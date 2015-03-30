package edu.jhu.nlp.wikipedia.demo;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An even simpler callback demo.
 *
 * @author Jason Smith
 * @see PageCallbackHandler
 *
 */

public class DemoSAXHandler implements PageCallbackHandler {

	private static final Logger logger = LoggerFactory.getLogger(DemoSAXHandler.class);


	public void process(WikiPage page) {
		logger.info(page.getTitle() + "\t" + page.getCategories());
	}

}
