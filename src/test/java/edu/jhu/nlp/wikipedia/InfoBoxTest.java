package edu.jhu.nlp.wikipedia;

import edu.jhu.nlp.util.FileUtil;

/**
 * @author Delip Rao
 */
public class InfoBoxTest extends TestCase {
  public boolean doTest() {
    String wikiText = FileUtil.readFile("data/newton.xml");
    WikiTextParser parser = new WikiTextParser(wikiText);
    InfoBox infoBox = parser.getInfoBox();
    if(infoBox == null) System.err.println("null");
    else System.err.println(infoBox.dumpRaw());
    return false;
  }

  public static void main(String [] args) {
    InfoBoxTest test = new InfoBoxTest();
    test.doTest();
  }
}
