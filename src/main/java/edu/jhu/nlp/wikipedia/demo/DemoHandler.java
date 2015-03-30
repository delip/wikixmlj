package edu.jhu.nlp.wikipedia.demo;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A very simple callback for demo.
 * 
 * @author Delip Rao
 * @see PageCallbackHandler
 *
 */

public class DemoHandler implements PageCallbackHandler {

	private static final Logger logger = LoggerFactory.getLogger(DemoHandler.class);


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
				logger.info(page.getTitle() + "\t" + trans);
			}
			
		}
						
	}

}
