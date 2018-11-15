package tut3;

import de.tudarmstadt.ukp.teaching.general.type.BIToken;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import java.text.BreakIterator;

/**
 * This class is a UIMA annotator, that tokenizes a given document with a BreakIterator based on word instances.
 */
public class BreakIteratorTokenizer extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jcas)
    {
        String document = jcas.getDocumentText();

        BreakIterator tok = BreakIterator.getWordInstance();
        tok.setText(document);
        int firstBoundary = 0;
        int nextBoundary = tok.next();
        while(nextBoundary != BreakIterator.DONE) {
            BIToken tokenAnnotation = new BIToken(jcas);
            tokenAnnotation.setBegin(firstBoundary);
            tokenAnnotation.setEnd(nextBoundary);
            tokenAnnotation.addToIndexes();
            firstBoundary = nextBoundary; //Last boundary is the beginning of the next token
            nextBoundary = tok.next();
        }
    }
}
