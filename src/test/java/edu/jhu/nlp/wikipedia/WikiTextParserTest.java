package edu.jhu.nlp.wikipedia;

import edu.jhu.nlp.language.Language;
import edu.jhu.nlp.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static junit.framework.Assert.assertTrue;


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

    // TODO: create more test cases since there really isn't any in the
    // original code.

    public static void demoGetText(String wikiFile) {
        String wikiText = FileUtil.readFile(wikiFile);
        WikiTextParser wtp = new WikiTextParser(wikiText);
        System.err.println(wtp.getPlainText());
    }

    public static void demoInfoBox(String wikiFile) {
        String wikiText = FileUtil.readFile(wikiFile);
        WikiTextParser wtp = new WikiTextParser(wikiText);
        InfoBox infoBox = wtp.getInfoBox();
        if(infoBox != null)
            System.err.println(infoBox.dumpRaw());
        else System.err.println("(null)");
    }

    public static void demoGetTranslatedTitle(String wikiFile) {
        String wikiText = FileUtil.readFile(wikiFile);
        WikiTextParser wtp = new WikiTextParser(wikiText);
        System.err.println(wtp.getTranslatedTitle(Language.ARABIC));
    }
}
