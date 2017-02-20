package edu.jhu.nlp.wikipedia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WikiXMLParserTest {
    @Test
    public void testSaxParser() throws Exception
    {
        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(getClass().getResource("/newton.xml"));

        wxsp.setPageCallback(new PageCallbackHandler() {
            @Override
            public void process(WikiPage page) {

                assertEquals("Isaac Newton", page.getTitle());
                assertEquals("14627", page.getID());
                assertEquals("2010-01-04T10:29:28Z", page.getTimestamp());

            }
        });
        wxsp.parse();
    }
}
