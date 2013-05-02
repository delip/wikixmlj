
/**
 * 
 * @author Delip Rao
 *
 */
public class TestCase {
  public void check(String testCase, boolean result) {
    try {
      if(result)
        System.err.println("Test '" + testCase + "' passed");
      else throw new Exception("Test '" + testCase + "' failed");
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
