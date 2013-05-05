package edu.jhu.nlp.wikipedia;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 *
 *
 * @author Delip Rao
 * @author Jason Smith
 *
 */
public abstract class WikiXMLParser
{
    private String wikiXMLFile = null;
    private BufferedReader wikiXMLBufferedReader = null;
    protected WikiPage currentPage = null;

    public WikiXMLParser(String fileName)
    {
        wikiXMLFile = fileName;
    }

    public WikiXMLParser(InputStream is)
    {
        wikiXMLBufferedReader = new BufferedReader(new InputStreamReader(is));
    }

    /**
     * Set a callback handler. The callback is executed every time a
     * page instance is detected in the stream. Custom handlers are
     * implementations of {@link PageCallbackHandler}
     * @param handler
     * @throws Exception
     */
    public abstract void setPageCallback(PageCallbackHandler handler) throws Exception;

    /**
     * The main parse method.
     * @throws Exception
     */
    public abstract void parse() throws Exception;

    /**
     *
     * @return an iterator to the list of pages
     * @throws Exception
     */
    public abstract WikiPageIterator getIterator() throws Exception;

    /**
     *
     * @return An InputSource created from wikiXMLFile
     * @throws Exception
     */
    protected InputSource getInputSource() throws Exception
    {
        BufferedReader br = null;

        if (this.wikiXMLBufferedReader != null)
        {
            br = this.wikiXMLBufferedReader;
        }
        else if (wikiXMLFile.endsWith(".gz"))
        {
            br = new BufferedReader(new InputStreamReader(
                    new GZIPInputStream(new FileInputStream(wikiXMLFile)), "UTF-8"));
        }
        else
        {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(wikiXMLFile), "UTF-8"));
        }

        return new InputSource(br);
    }

    protected void notifyPage(WikiPage page)
    {
        currentPage = page;
    }
}