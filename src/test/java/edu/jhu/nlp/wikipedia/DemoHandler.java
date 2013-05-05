package edu.jhu.nlp.wikipedia;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A very simple callback for demo. 																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						qa
 * 
 * @author Delip Rao
 * @see PageCallbackHandler
 *
 */

public class DemoHandler implements PageCallbackHandler {

	public void process(WikiPage page) {
		
		boolean personPage = false;
		HashSet<String> cats = page.getCategories();
		for (String category : cats) {
			if(category.matches("\\d\\d\\d\\d (births|deaths)"))
				personPage = true;
			else if(category.matches("Living people"))
				personPage = true;
		}
		
		if(personPage) {
			Pattern hiPattern = Pattern.compile("\\[\\[hi:(.*?)\\]\\]", Pattern.MULTILINE);
			Matcher matcher = hiPattern.matcher(page.getWikiText());
			while(matcher.find()) {
				String trans = matcher.group(1);
				System.out.println(page.getTitle() + "\t" + trans);
			}
			
		}
						
	}

}
