package tut3.consumer;

import de.tudarmstadt.ukp.teaching.general.type.BIToken;
import de.tudarmstadt.ukp.teaching.general.type.Sentence;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;

/**
 * This class is a UIMA writer that selects all Sentence annotations and counts the BIToken in the units, that are
 * annotated with "Sentence".
 */
public class TokensPerSentenceWriter extends JCasConsumer_ImplBase {

    @Override
    public void process(JCas jCas) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CAS ===\n");
        sb.append("-- Document Text --\n");
        sb.append(jCas.getDocumentText()).append('\n');
        sb.append("-- Annotations --\n");

        //Select all sentence tokens
        for(Sentence s: JCasUtil.select(jCas, Sentence.class)) {
            sb.append('[').append(s.getType().getShortName()).append(']');
            sb.append('(').append(s.getBegin()).append(", ").append(s.getEnd()).append(')');
            sb.append(" Number of BIToken: ").append(JCasUtil.selectCovered(jCas, BIToken.class, s).size()).append(": ");
            sb.append(s.getCoveredText()).append('\n');
        }

        sb.append('\n');

        getContext().getLogger().log(Level.INFO, sb.toString());
    }
}
