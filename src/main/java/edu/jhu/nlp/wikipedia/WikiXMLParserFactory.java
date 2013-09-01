package edu.jhu.nlp.wikipedia;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Delip Rao
 *
 */
public class WikiXMLParserFactory {
    public static WikiXMLParser getSAXParser(String fileName) throws MalformedURLException {
        return new WikiXMLSAXParser(new URL(fileName));
    }

    public static WikiXMLParser getSAXParser(URL fileName) {
        return new WikiXMLSAXParser(fileName);
    }
}
