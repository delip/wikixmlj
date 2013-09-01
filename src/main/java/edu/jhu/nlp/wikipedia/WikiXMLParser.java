package edu.jhu.nlp.wikipedia;

import org.apache.tools.bzip2.CBZip2InputStream;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
    private URL wikiXMLFile = null;
    protected WikiPage currentPage = null;
    private InputStream is = null;

    public WikiXMLParser(URL fileName) {
        wikiXMLFile = fileName;
    }

    public WikiXMLParser(InputStream is) {
        this.is = is;
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
     * @return An InputSource created from wikiXMLFile
     * @throws Exception
     */
    protected InputSource getInputSource() throws Exception {
        if (is != null) return new InputSource(is);

        final BufferedReader br;
        if (wikiXMLFile.toExternalForm().endsWith(".gz")) {
            br = new BufferedReader(new InputStreamReader(new GZIPInputStream(wikiXMLFile.openStream()), "UTF-8"));
        } else if (wikiXMLFile.toExternalForm().endsWith(".bz2")) {
            InputStream fis = wikiXMLFile.openStream();
            byte[] ignoreBytes = new byte[2];
            fis.read(ignoreBytes); //"B", "Z" bytes from commandline tools
            br = new BufferedReader(new InputStreamReader(new CBZip2InputStream(fis), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(wikiXMLFile.openStream(), "UTF-8"));
        }

        return new InputSource(br);
    }

    protected void notifyPage(WikiPage page)
    {
        currentPage = page;
    }
}