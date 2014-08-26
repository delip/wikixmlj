package edu.jhu.nlp.wikipedia;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;

import java.net.MalformedURLException;


public class WikiXMLParserFactoryTest {
    @Test
    public void testGetSAXParserFromRelativeFileName() {
        try {
            assertNotNull(WikiXMLParserFactory.getSAXParser("../articles.xml"));
        } catch (MalformedURLException e) {
            fail("relative filename should be get parser; exception:" + e);
        }
    }

    @Test
    public void testGetSAXParserFromAbsoluteFileName() {
        try {
            assertNotNull(WikiXMLParserFactory.getSAXParser("/articles.xml"));
        } catch (MalformedURLException e) {
            fail("relative filename should be get parser; exception:" + e);
        }
    }
}
