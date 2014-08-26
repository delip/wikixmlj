package edu.jhu.nlp.wikipedia;

import edu.jhu.nlp.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class WikiTextParserTest extends TestCase {

    private BufferedReader in = null;
    private String wtext = null;

    @Before
    public void setup() throws IOException
    {
        in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Lexington.wiki")));

        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = in.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        wtext = sb.toString();
    }

    @After
    public void teardown() throws IOException
    {
        if (in != null) {
            in.close();
        }
        in = null;
    }

    @Test
    public void testDisambiguationPage() throws IOException
    {
        WikiTextParser wtp = new WikiTextParser(wtext);
        assertTrue(wtp.isDisambiguationPage());
    }

    @Test
    public void testDisambiguationPage2() throws IOException
    {
        WikiPage page = new WikiPage();new WikiTextParser(wtext);
        page.setTitle("Lexington");
        page.setWikiText(wtext);
        assertTrue(page.isDisambiguationPage());
    }

    @Test
    public void testInfoBox() throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Obama.wiki")));

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = in.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        String wtext = sb.toString();

        WikiPage page = new WikiPage();new WikiTextParser(wtext);
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

    // TODO: create more test cases since there really isn't any in the
    // original code.

    public static void demoGetText(String wikiFile) {
        String wikiText = FileUtil.readFile(wikiFile);
        WikiTextParser wtp = new WikiTextParser(wikiText);
        System.err.println(wtp.getPlainText());
    }
}
