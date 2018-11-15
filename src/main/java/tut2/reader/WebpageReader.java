package tut2.reader;

import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * Implements an Uima-Webpagereader, that uses the URL specified by the UIMA configuration parameter to load
 * the webpage via JSoup.
 * It loads all &lt;p&gt;-HTML-Tags to define the documents of the webpage.
 * The documents, i.e. the &lt;p&gt;-blocks, can be iterated by the getNext()-method.
 *
 * @author Stefan Thaut
 * @version 2
 */
public class WebpageReader extends JCasCollectionReader_ImplBase {

    /**
     * The index of the current document
     */
    private int current;

    /**
     * The number of documents found on the webpage
     */
    private int max;

    /**
     * The iterator to iterate over the documents of the webpage
     */
    private Iterator<Element> iterator;

    /**
     * UIMA configuration parameter to set the language of the document.
     * The language is specified by the language-code.
     * The default value is "en-us".
     */
    public static final String PARAM_DOCUMENT_LANGUAGE = "DocumentLanguage";
    @ConfigurationParameter(
            name = PARAM_DOCUMENT_LANGUAGE,
            description = "Sets the document's language",
            defaultValue = "en-us"
    )
    private String documentLanguage;

    /**
     * UIMA configuration parameter to set the URL of the webpage, that should be crawled.
     * The default value refers to the english wikipedia page of natural language processing.
     */
    public static final String PARAM_WEBPAGE_URL = "WebpageURL";
    @ConfigurationParameter(
            name = PARAM_WEBPAGE_URL,
            description = "Sets the URL of the webpage, that should be crawled",
            defaultValue = "https://en.wikipedia.org/wiki/Natural_language_processing"
    )
    private String webpageURL;

    /**
     * UIMA configuration parameter to set the selector element for JSoup. The default value is the body element of the
     * webpage.
     */
    public static final String PARAM_SELECTOR = "Selector";
    @ConfigurationParameter(
            name = PARAM_SELECTOR,
            description = "Sets the selector for JSoup, which elements should be crawled",
            mandatory = false,
            defaultValue = "body"
    )
    private String selector;

    /**
     * Initializes the reader with the given UimaContext-object.
     * Sets the index of the current document to zero.
     * Loads the document via JSoup and filter all &lt;p&gt;-blocks.
     * Sets the number of documents to the number of filtered &lt;p&gt;-blocks.
     * Finally it creates the iterator over the documents.
     * @param context - the UimaContext
     * @throws ResourceInitializationException - is thrown from the super-class
     */
    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);

        current = 0;

        try {
            Document document = Jsoup.connect(webpageURL).get();
            Elements elements = document.select(selector);
            max = elements.size();
            iterator = elements.iterator();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a Progress-object with the current state of the reader.
     * That is the id of the current documents and the total number of documents.
     * @return - Prograss-object
     */
    @Override
    public Progress[] getProgress() {
        return new Progress[]{new ProgressImpl(current, max, Progress.ENTITIES)};
    }

    /**
     * Returns true, if there is another document left.
     * @return - true, if there is another document left, false otherwise
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Sets the language of the document to the language specified by the configuration parameter.
     * Forward the next document object to the JCas-consumer.
     * Increments then the index of the current object.
     * @param jCas - the consumer object
     */
    @Override
    public void getNext(JCas jCas) {
        jCas.setDocumentLanguage(documentLanguage);
        jCas.setDocumentText(iterator.next().text());
        current++;
    }
}
