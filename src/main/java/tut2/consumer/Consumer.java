package tut2.consumer;

import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.Level;

/**
 * This class provides a simple UIMA-output class, that prints the results of the analysed documents of a webpage
 * in the console.
 */
public class Consumer extends JCasConsumer_ImplBase {

    /**
     * Prints the informations about the documents on a webpage. Prints at first the whole document text and then
     * the individual annotations created by different analysis engines.
     * @param jCas
     */
    @Override
    public void process(JCas jCas) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CAS ===\n");
        sb.append("-- Document Text --\n");
        sb.append(jCas.getDocumentText()).append('\n');
        sb.append("-- Annotations --\n");

        for(Annotation a : JCasUtil.select(jCas, Annotation.class)) {
            String shortName = a.getType().getShortName();
            String text = a.getCoveredText();
            sb.append('[').append(shortName).append(']');
            sb.append('(').append(a.getBegin()).append(", ").append(a.getEnd()).append(')');
            sb.append(text).append('\n');
        }

        sb.append('\n');

        getContext().getLogger().log(Level.INFO, sb.toString());
    }
}
