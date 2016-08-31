package edu.jhu.nlp.wikipedia;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * A Wrapper class for the PageCallbackHandler
 *
 * @author Jason Smith
 *
 */
public class SAXPageCallbackHandler extends DefaultHandler {

	private boolean insideRevision = false;
	private PageCallbackHandler pageHandler;
	private WikiPage currentPage;
	private String currentTag;

	private StringBuilder currentWikitext;
	private StringBuilder currentTitle;
	private StringBuilder currentID;
	private String language = null;


	public SAXPageCallbackHandler(PageCallbackHandler pageHandler, String language){
		this.pageHandler = pageHandler;
		this.language = language;
	}

	@Override
	public void startElement(String uri, String name, String qName, Attributes attr){
		currentTag = qName;
		if (qName.equals("page")){
			currentPage = new WikiPage();
			currentWikitext = new StringBuilder("");
			currentTitle = new StringBuilder("");
			currentID = new StringBuilder("");
		}

		if (qName.equals("revision")){
			insideRevision = true;
		}

	}

	@Override
	public void endElement(String uri, String name, String qName){
		if (qName.equals("revision")){
			insideRevision = false;
		}
		if (qName.equals("page")){
			currentPage.setTitle(currentTitle.toString());
			currentPage.setID(currentID.toString());
			currentPage.setWikiText(currentWikitext.toString(), language);
			pageHandler.process(currentPage);
		}
		if (qName.equals("mediawiki"))
		{
			// TODO hasMoreElements() should now return false
		}
	}

	@Override
	public void characters(char ch[], int start, int length){
		if (currentTag.equals("title")){
			currentTitle = currentTitle.append(ch, start, length);
		}
		// Avoids looking at the revision ID, only the first ID should be taken.
		else if ((currentTag.equals("id")) && !insideRevision){
			currentID.append(ch, start, length);
		}
		else if (currentTag.equals("text")){
			currentWikitext = currentWikitext.append(ch, start, length);
		}
	}
}