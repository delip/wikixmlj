import edu.jhu.nlp.language.Language;
import edu.jhu.nlp.util.FileUtil;
import edu.jhu.nlp.wikipedia.InfoBox;
import edu.jhu.nlp.wikipedia.WikiTextParser;


public class WikiTextParserTest extends TestCase {

  public static boolean testDisambiguationPage() {
    String lexingtonWikiText = FileUtil.readFile("data/Lexington.wiki");
    WikiTextParser wtp = new WikiTextParser(lexingtonWikiText);
    return wtp.isDisambiguationPage();
  }

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

  /**
   * @param args
   */
  public static void main(String[] args) {
    WikiTextParserTest test = new WikiTextParserTest();
    //test.check("Disambiguation Page", testDisambiguationPage());
    //demoGetTranslatedTitle("data/Obama.wiki");
    //demoGetText("data/newton.xml");
    demoInfoBox("data/Obama.wiki");
  }

}
