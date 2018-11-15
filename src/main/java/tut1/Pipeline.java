package tut1;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import tut1.consumer.DummyWriter;
import tut1.reader.DummyReader;

public class Pipeline {

	public static void main(String[] args) throws UIMAException, IOException {
		CollectionReader reader = createReader(DummyReader.class);

		AnalysisEngine seg = createEngine(BreakIteratorSegmenter.class);

		AnalysisEngine writer = createEngine(DummyWriter.class);

		SimplePipeline.runPipeline(reader, seg, writer);
	}
}