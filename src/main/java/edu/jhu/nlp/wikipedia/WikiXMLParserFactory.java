package edu.jhu.nlp.wikipedia;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Delip Rao
 *
 */
public class WikiXMLParserFactory {
    public static WikiXMLParser getSAXParser(String fileName) throws MalformedURLException {

        return new WikiXMLSAXParser(new File(fileName).toURI().toURL());
    }

    public static WikiXMLParser getSAXParser(String fileName, String languageCode) throws MalformedURLException {

        return new WikiXMLSAXParser(new File(fileName).toURI().toURL(), languageCode);
    }


    public static WikiXMLParser getSAXParser(URL fileName) {
        return new WikiXMLSAXParser(fileName);
    }
}
