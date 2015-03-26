package edu.jhu.nlp.wikipedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Delip Rao
 *
 */
public class TestCase {
  private static final Logger logger = LoggerFactory.getLogger(TestCase.class);

  public void check(String testCase, boolean result) {
    try {
      if(result)
        logger.info("Test '" + testCase + "' passed");
      else throw new Exception("Test '" + testCase + "' failed");
    } catch(Exception e) {
      logger.error(e.toString());
      System.exit(-1);
    }
  }
}
