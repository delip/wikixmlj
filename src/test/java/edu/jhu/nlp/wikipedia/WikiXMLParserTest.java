package edu.jhu.nlp.wikipedia;

import org.junit.Test;

public class WikiXMLParserTest {
    @Test
    public void testSaxParser() throws Exception
    {
        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(getClass().getResource("/newton.xml").getFile());

        wxsp.setPageCallback(new PageCallbackHandler() {
            @Override
            public void process(WikiPage page) {
                System.out.println(page.getTitle());
                for (String link : page.getLinks())
                {
                    System.out.println(link);
                }
            }
        });
        wxsp.parse();
    }
}
