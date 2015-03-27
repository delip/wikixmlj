package edu.jhu.nlp.wikipedia;

import edu.jhu.nlp.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.*;


public class WikiTextParserTest extends TestCase {

    private BufferedReader in = null;
    private String wtext = null;

    @Before
    public void setup() throws IOException {
        wtext = FileUtil.readFile(getClass().getResource("/Lexington.wiki").getFile());

    }

    @Test
    public void testDisambiguationPage() throws IOException {
        WikiTextParser wtp = new WikiTextParser(wtext);
        assertTrue(wtp.isDisambiguationPage());
    }

    @Test
    public void testDisambiguationPage2() throws IOException {
        WikiPage page = new WikiPage();
        new WikiTextParser(wtext);
        page.setTitle("Lexington");
        page.setWikiText(wtext);
        assertTrue(page.isDisambiguationPage());
    }

    @Test
    public void testInfoBox() throws IOException, WikiTextParserException {
        String wtext = FileUtil.readFile(getClass().getResource("/Obama.wiki").getFile());

        WikiPage page = new WikiPage();
        new WikiTextParser(wtext);
        page.setTitle("Obama");
        page.setWikiText(wtext);
        assertNotNull(page.getInfoBox());
        String text = page.getText();
        assertTrue(text.indexOf("{{Infobox") == -1);
    }

    @Test
    public void testMatcherGroupReferenceInWiki() {
        try {
            String text = new WikiTextParser("[[$9]]").getPlainText();
            assertEquals("$9", text);
        } catch (IllegalArgumentException e) {
            fail("matcher group reference should be pasrsed");
        }
    }

    @Test(expected = WikiTextParserException.class)
    public void testInfoBoxIndexOutOfBounds() throws WikiTextParserException {
        String wtext = FileUtil.readFile(getClass().getResource("/Fitzroy_Island_National_Park.wiki").getFile());
        WikiTextParser wtp = new WikiTextParser(wtext);
        wtp.getInfoBox().dumpRaw();
    }

    // TODO: create more test cases since there really isn't any in the
    // original code.

    public static void demoGetText(String wikiFile) {
        String wikiText = FileUtil.readFile(wikiFile);
        WikiTextParser wtp = new WikiTextParser(wikiText);
        System.err.println(wtp.getPlainText());
    }

}
