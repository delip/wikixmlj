package edu.jhu.nlp.wikipedia;

import edu.jhu.nlp.language.Language;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For internal use only -- Used by the {@link WikiPage} class.
 * Can also be used as a stand alone class to parse wiki formatted text.
 *
 * @author Delip Rao
 */
public class WikiTextParser {
    private String wikiText = null;
    private HashSet<String> pageCats = null;
    private HashSet<String> pageLinks = null;
    private boolean redirect = false;
    private String redirectString = null;
    private boolean stub = false;
    private boolean disambiguation = false;
    private static Pattern redirectPattern = null;
    private static Pattern stubPattern = null;
    private static Pattern disambiguationPattern = null;
    private InfoBox infoBox = null;
    private Language language = null;

    private static Pattern stylesPattern = Pattern.compile("\\{\\|.*?\\|\\}$", Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern infoboxCleanupPattern = Pattern.compile("\\{\\{infobox.*?\\}\\}$", Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static Pattern curlyCleanupPattern0 = Pattern.compile("^\\{\\{.*?\\}\\}$", Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern curlyCleanupPattern1 = Pattern.compile("\\{\\{.*?\\}\\}", Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern cleanupPattern0 = Pattern.compile("^\\[\\[.*?:.*?\\]\\]$", Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern cleanupPattern1 = Pattern.compile("\\[\\[(.*?)\\]\\]", Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern refCleanupPattern = Pattern.compile("<ref>.*?</ref>", Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern commentsCleanupPattern = Pattern.compile("<!--.*?-->", Pattern.MULTILINE | Pattern.DOTALL);

    /**
     * Default constructor
     * @param wikiText  The wiki text
     * @param languageCode  the language of the currently parsed wikipedia
     */
    public WikiTextParser(String wikiText, String languageCode) {
        this.wikiText = wikiText;
        this.language = new Language(languageCode);
        createPatterns();
        findRedirect(wikiText);
        Matcher matcher;
        matcher = stubPattern.matcher(wikiText);
        stub = matcher.find();
        matcher = disambiguationPattern.matcher(wikiText);
        disambiguation = matcher.find();
    }


    /**
     * Default constructor. When no language is given, defaults to English.
     * @param wikiText the wiki text
     */
    public WikiTextParser(String wikiText){
        this(wikiText, "en");
    }

    /**
     * Check for redirects
     * @param wikiText  the currently parsed page
     */
    private void findRedirect(String wikiText) {
        Matcher matcher = redirectPattern.matcher(wikiText);
        if (matcher.find()) {
            redirect = true;
            if (matcher.groupCount() == 1) {
                redirectString = matcher.group(1);
            }
        }
    }
    /**
     * Create localized patterns (given the language in the constructor) for redirects, stubs, etc.
     */
    private void createPatterns(){
        redirectPattern = Pattern.compile("#"+language.getLocalizedRedirectLabel()+"\\s*\\[\\[(.*?)\\]\\]", Pattern.CASE_INSENSITIVE);
        stubPattern = Pattern.compile("\\-"+language.getLocalizedStubLabel()+"\\}\\}", Pattern.CASE_INSENSITIVE);
        disambiguationPattern = Pattern.compile("\\{\\{"+language.getDisambiguationLabel()+"\\}\\}", Pattern.CASE_INSENSITIVE);
    }

    public boolean isRedirect() {
        return redirect;
    }

    public boolean isStub() {
        return stub;
    }

    public String getRedirectText() {
        return redirectString;
    }

    public String getText() {
        return wikiText;
    }

    public HashSet<String> getCategories() {
        if (pageCats == null) {
            parseCategories();
        }
        return pageCats;
    }

    public HashSet<String> getLinks() {
        if (pageLinks == null) {
            parseLinks();
        }
        return pageLinks;
    }

    private void parseCategories() {
        pageCats = new HashSet<String>();
        Pattern catPattern = Pattern.compile("\\[\\["+language.getLocalizedCategoryLabel()+":(.*?)\\]\\]", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = catPattern.matcher(wikiText);
        while (matcher.find()) {
            String[] temp = matcher.group(1).split("\\|");
            pageCats.add(temp[0]);
        }
    }

    private void parseLinks() {
        pageLinks = new HashSet<String>();
        Pattern catPattern = Pattern.compile("\\[\\[(.*?)\\]\\]", Pattern.MULTILINE);
        Matcher matcher = catPattern.matcher(wikiText);
        while (matcher.find()) {
            String[] temp = matcher.group(1).split("\\|");
            if (temp.length == 0) {
                continue;
            }
            String link = temp[0];
            if (!link.contains(":")) {
                pageLinks.add(link);
            }
        }
    }

    /**
     * Return only the unformatted text body. Heading markers are omitted.
     * //ToDo: remove "References", "Notes", "Further Reading" and "See Also" sections (in all languages).
     * @return the unformatted text body
     */
    public String getTextBody() {
        String text = getPlainText();
        text = stripBottomInfo(text, language.seealsoLabel);
        text = stripBottomInfo(text, language.furtherLabel);
        text = stripBottomInfo(text, language.referenceLabel);
        text = stripBottomInfo(text, language.notesLabel);
        text = cleanHeadings(text);
        return text;
    }

    private String stripBottomInfo(String text, String label) {
        Pattern bottomPattern = Pattern.compile("^=*\\s?" + label + "\\s?=*$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = bottomPattern.matcher(text);
        if(matcher.find())
            text = text.substring(0, matcher.start());
        return text;
    }

    private String cleanHeadings(String text) {
        Pattern startHeadingPattern = Pattern.compile("^=*", Pattern.MULTILINE);
        Pattern endHeadingPattern = Pattern.compile("=*$", Pattern.MULTILINE);
        text = startHeadingPattern.matcher(text).replaceAll("");
        text = endHeadingPattern.matcher(text).replaceAll("");
        return text;
    }

    public String getPlainText() {
        String text = wikiText.replaceAll("&gt;", ">");
        text = text.replaceAll("&lt;", "<");
        text = infoboxCleanupPattern.matcher(text).replaceAll(" ");
        text = commentsCleanupPattern.matcher(text).replaceAll(" ");
        text = stylesPattern.matcher(text).replaceAll(" ");
        text = refCleanupPattern.matcher(text).replaceAll(" ");
        text = text.replaceAll("</?.*?>", " ");
        text = curlyCleanupPattern0.matcher(text).replaceAll(" ");
        text = curlyCleanupPattern1.matcher(text).replaceAll(" ");
        text = cleanupPattern0.matcher(text).replaceAll(" ");

        Matcher m = cleanupPattern1.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            // For example: transform match to upper case
            int i = m.group().lastIndexOf('|');
            String replacement;
            if (i > 0) {
                replacement = m.group(1).substring(i - 1);
            } else {
                replacement = m.group(1);
            }
            m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        m.appendTail(sb);
        text = sb.toString();

        text = text.replaceAll("'{2,}", "");
        return text.trim();
    }



    public InfoBox getInfoBox() throws WikiTextParserException {
        //parseInfoBox is expensive. Doing it only once like other parse* methods
        if (infoBox == null)
            infoBox = parseInfoBox();
        return infoBox;
    }

    //TODO: ignore brackets in html/xml comments (or better still implement a formal grammar for wiki markup)
    private InfoBox parseInfoBox() throws WikiTextParserException {
        final String INFOBOX_CONST_STR = "{{Infobox";
        int startPos = wikiText.indexOf(INFOBOX_CONST_STR);
        if (startPos < 0) return null;
        int bracketCount = 2;
        int endPos = startPos + INFOBOX_CONST_STR.length();
        for (; endPos < wikiText.length(); endPos++) {
            switch (wikiText.charAt(endPos)) {
                case '}':
                    bracketCount--;
                    break;
                case '{':
                    bracketCount++;
                    break;
                default:
            }
            if (bracketCount == 0) break;
        }

        if (bracketCount != 0) {
            throw new WikiTextParserException("Malformed Infobox, couldn't match the brackets.");
        }

        String infoBoxText = wikiText.substring(startPos, endPos + 1);
        infoBoxText = stripCite(infoBoxText); // strip clumsy {{cite}} tags
        // strip any html formatting
        infoBoxText = infoBoxText.replaceAll("&gt;", ">");
        infoBoxText = infoBoxText.replaceAll("&lt;", "<");
        infoBoxText = infoBoxText.replaceAll("<ref.*?>.*?</ref>", " ");
        infoBoxText = infoBoxText.replaceAll("</?.*?>", " ");
        return new InfoBox(infoBoxText);
    }

    private String stripCite(String text) {
        String CITE_CONST_STR = "{{cite";
        int startPos = text.indexOf(CITE_CONST_STR);
        if (startPos < 0) return text;
        int bracketCount = 2;
        int endPos = startPos + CITE_CONST_STR.length();
        for (; endPos < text.length(); endPos++) {
            switch (text.charAt(endPos)) {
                case '}':
                    bracketCount--;
                    break;
                case '{':
                    bracketCount++;
                    break;
                default:
            }
            if (bracketCount == 0) break;
        }
        text = text.substring(0, startPos - 1) + text.substring(endPos);
        return stripCite(text);
    }

    public boolean isDisambiguationPage() {
        return disambiguation;
    }

    public String getTranslatedTitle(String languageCode) {
        Pattern pattern = Pattern.compile("^\\[\\[" + languageCode + ":(.*?)\\]\\]$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(wikiText);
        if (matcher.find()) {

            return matcher.group(1);
        }
        return null;
    }
}
