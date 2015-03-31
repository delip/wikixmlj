package edu.jhu.nlp.language;

import edu.jhu.nlp.util.FileUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Enumeration;

/**
 * A utility class for language specific features
 * @author Alan Said
 *
 */
public class Language {

  public static String localizedCategoryLabel;
  public static String localizedSpecialLabel;
  public static String localizedPortalLabel;
  public static String localizedRedirectLabel;
  public static String localizedStubLabel;
  public static String disambiguationLabel;

  private static final String CATEGORY = "category";
  private static final String SPECIAL = "special";
  private static final String PORTAL = "portal";
  private static final String REDIRECT = "redirect";
  private static final String STUB = "stub";
  public static final String DISAMBIGUATION = "disambiguation";


  /**
   * Set the language of the wikipedia to be parsed.
   * @param languageCode the language code of the wikipedia, e.g. "en" for English, "de" for German, "zh" for Chinese.
   */
  public Language(String languageCode){
    JSONObject jobj = getJsonObject(languageCode);
    this.localizedCategoryLabel = (String) jobj.get(CATEGORY);
    this.localizedSpecialLabel = (String) jobj.get(SPECIAL);
    this.localizedPortalLabel = (String) jobj.get(PORTAL);
    this.localizedRedirectLabel = (String) jobj.get(REDIRECT);
    this.localizedStubLabel = (String) jobj.get(STUB);
    this.disambiguationLabel = (String) jobj.get(DISAMBIGUATION);
  }

  /**
   * Get the JSON object containing localized labels.
   * @param languageCode  the language code.
   * @return  the localized JSON object.
   */
  private JSONObject getJsonObject(String languageCode) {
    JSONObject jobj = null;
    try {
      InputStream is = Language.class.getResourceAsStream("/languages.json");
      Reader isr = new InputStreamReader(is);
      jobj = (JSONObject) ((JSONObject) new JSONParser().parse(isr)).get(languageCode);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return jobj;
  }
  public static String getLocalizedCategoryLabel() {
    return localizedCategoryLabel;
  }

  public static String getLocalizedSpecialLabel() {
    return localizedSpecialLabel;
  }

  public static String getLocalizedPortalLabel() {
    return localizedPortalLabel;
  }

  public static String getLocalizedRedirectLabel() {
    return localizedRedirectLabel;
  }

  public static String getLocalizedStubLabel() {
    return localizedStubLabel;
  }

  public static String getDisambiguationLabel() {
    return disambiguationLabel;
  }


}
