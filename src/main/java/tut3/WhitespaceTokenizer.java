package tut3;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.teaching.general.type.WSToken;

public class WhitespaceTokenizer
    extends JCasAnnotator_ImplBase
{

    // Adapt the whitespace tokenizer into a UIMA annotator
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        String document = jcas.getDocumentText();
        int len = document.length();
        int start = 0;
        int end = 0;

        while (start < len) {
            while (start < len && Character.isSpaceChar(document.charAt(start))) {
                start++;
            }

            for (end = start; end < len && !Character.isSpaceChar(document.charAt(end)); end++);
            
            if (start < len) {
                // The following code creates an annotation and adds it to
                // the index. You'll need execute it for each token you find.
                WSToken tokenAnnotation = new WSToken(jcas);
                tokenAnnotation.setBegin(start);
                tokenAnnotation.setEnd(end);
                tokenAnnotation.addToIndexes();
            }
            start = end + 1;
        }
    }
}
