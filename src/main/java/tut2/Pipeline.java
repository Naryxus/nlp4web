package tut2;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import tut2.consumer.Consumer;
import tut2.reader.WebpageReader;

import java.io.IOException;

/**
 * This class provides the UIMA-pipeline.
 * At first it reads a webpage with the WebpageReader.
 * Then it tokenize the documents with a BreakIteratorSegmenter.
 * After that it checks the spelling with the JazzyChecker.
 * And at last it prints the output with the Consumer-class.
 */
public class Pipeline {

    public static void main(String[] args) throws UIMAException, IOException {
        CollectionReader reader = createReader(WebpageReader.class);

        AnalysisEngine seg = createEngine(BreakIteratorSegmenter.class);

        //The JazzyChecker uses the default english-dictionary
        AnalysisEngine jazzy = createEngine(JazzyChecker.class, JazzyChecker.PARAM_MODEL_LOCATION, "src/main/resources/eng_com.dic");

        AnalysisEngine writer = createEngine(Consumer.class);

        SimplePipeline.runPipeline(reader, seg, jazzy, writer);
    }
}
