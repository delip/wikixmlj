package edu.jhu.nlp.wikipedia;

import edu.jhu.nlp.language.Language;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Data structures for a wikipedia page.
 *
 * @author Delip Rao
 */
public class WikiPage {

    private String title = null;
    private WikiTextParser wikiTextParser = null;
    private String id = null;
    private Language language = null;
    private Pattern disambCatPattern = null; //Pattern.compile("\\("+language.getDisambiguationLabel()+"\\)", Pattern.CASE_INSENSITIVE);
    private Pattern categoryPattern = null; //Pattern.compile( language.getLocalizedCategoryLabel()+ "\\W\\w+", Pattern.CASE_INSENSITIVE);


    /**
     * Set the page title. This is not intended for direct use.
     *
     * @param title
     */
    public void setTitle(final String title) {
        this.title = title.trim();
    }

    /**
     * Set the wiki text associated with this page.
     * This setter also introduces side effects. This is not intended for direct use.
     *
     * @param wtext wiki-formatted text
     */
    public void setWikiText(final String wtext, String languageCode) {
        wikiTextParser = new WikiTextParser(wtext, languageCode);
        this.language = new Language(languageCode);
        disambCatPattern = Pattern.compile("\\("+language.getDisambiguationLabel()+"\\)", Pattern.CASE_INSENSITIVE);
        categoryPattern = Pattern.compile( language.getLocalizedCategoryLabel()+ ":\\S+(\\s*\\S+)*", Pattern.CASE_INSENSITIVE);
    }

    /**
     * Set the wiki text, defaults to English.
     * @param wtext
     */
    public void setWikiText(final String wtext) {
        setWikiText(wtext, "en");
    }


    /**
     * @return a string containing the page title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return a string containing the page title.
     */
    public String getTranslatedTitle(String languageCode) {
        return wikiTextParser.getTranslatedTitle(languageCode);
    }


    /**
     * @return true if this a disambiguation page.
     */
    public boolean isDisambiguationPage() {
        return disambCatPattern.matcher(title).matches() || wikiTextParser.isDisambiguationPage();
    }

    /**
     * @return true for "localizedSpecialLabel pages" -- like Category:, Wikipedia:, etc
     */
    public boolean isSpecialPage() {
        return isCategoryPage() && isRedirect() && isDisambiguationPage() && isNotContentPage();
//        return title.indexOf(':') > 0;
    }

    /**
     * @return true for category pages.
     */
    public boolean isCategoryPage(){
        return categoryPattern.matcher(title).matches();
    }

    /**
     * @return true for non content pages not.
     */
    private boolean isNotContentPage(){
        return title.startsWith("Wikipedia:") && title.startsWith("File:");
    }

    /**
     * Use this method to get the wiki text associated with this page.
     * Useful for custom processing the wiki text.
     *
     * @return a string containing the wiki text.
     */
    public String getWikiText() {
        return wikiTextParser.getText();
    }

    /**
     * @return true if this is a redirection page
     */
    public boolean isRedirect() {
        return wikiTextParser.isRedirect();
    }

    /**
     * @return true if this is a localizedStubLabel page
     */
    public boolean isStub() {
        return wikiTextParser.isStub();
    }

    /**
     * @return the title of the page being redirected to.
     */
    public String getRedirectPage() {
        return wikiTextParser.getRedirectText();
    }

    /**
     * @return plain text stripped of all wiki formatting.
     */
    public String getText() {
        return wikiTextParser.getPlainText();
    }

    /**
     * @return a list of categories the page belongs to, null if this a redirection/disambiguation page
     */
    public HashSet<String> getCategories() {
        return wikiTextParser.getCategories();
    }

    public InfoBox getInfoBox() throws WikiTextParserException {
        return wikiTextParser.getInfoBox();
    }

    /**
     * @return a list of links contained in the page
     */
    public HashSet<String> getLinks() {
        return wikiTextParser.getLinks();
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }
}