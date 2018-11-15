package tut1.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

public class DummyReader extends JCasCollectionReader_ImplBase {
	List<String> texts;
	int idx = 0;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			texts = Files.readAllLines(Paths.get(classLoader.getResource("news.txt").toURI()));
		} catch(IOException|URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getNext(JCas jcas) throws IOException, CollectionException {
		jcas.setDocumentText(texts.get(idx));
		idx++;
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(idx + 1, texts.size(), Progress.ENTITIES) };
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return idx < texts.size();
	}

}
