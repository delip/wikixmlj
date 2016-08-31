package edu.jhu.nlp.wikipedia;

/**
 * A class abstracting Wiki infobox
 * @author Delip Rao
 * @author Victor Olivares
 */
public class InfoBox {
	String infoBoxWikiText = null;
	InfoBox(String infoBoxWikiText) {
		//to to be the following line
		//this.infoBoxWikiText = infoBoxWikiText;
		if (infoBoxWikiText != null){
			this.infoBoxWikiText = infoBoxWikiText;
		} else {
			//set infobox text to empty string
			this.infoBoxWikiText = new String();
		}
	}
	public String dumpRaw() {
		return infoBoxWikiText;
	}
	public boolean isEmpty() {
		return infoBoxWikiText.isEmpty();
	}
}
