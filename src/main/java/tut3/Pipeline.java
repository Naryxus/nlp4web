package tut3;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import tut2.consumer.Consumer;
import tut2.reader.WebpageReader;
import tut3.consumer.TokensPerSentenceWriter;

import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

public class Pipeline {

    public static void main(String[] args) throws UIMAException, IOException {
        //Use the WebpageReader from assignment 2
        CollectionReader reader = createReader(WebpageReader.class, WebpageReader.PARAM_SELECTOR, "p");

        AnalysisEngine wst = createEngine(WhitespaceTokenizer.class);

        //BreakIterator based on word instances
        AnalysisEngine bit = createEngine(BreakIteratorTokenizer.class);

        //BreakIterator based on sentence instances
        AnalysisEngine sentenceSplitter = createEngine(SentenceSplitter.class);

        AnalysisEngine writer = createEngine(Consumer.class);

        //Output, that prints the number of BIToken per Sentence annotation
        AnalysisEngine tokensPerSentenceWriter = createEngine(TokensPerSentenceWriter.class);

        SimplePipeline.runPipeline(reader, wst, bit, sentenceSplitter, tokensPerSentenceWriter);
    }
}
