package edu.jhu.nlp.wikipedia;

/**
 * 
 * @author Jason Smith
 *
 */
public class SAXParserDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("Usage: Parser <XML-FILE>");
			System.exit(-1);
		}
		
		PageCallbackHandler handler = new DemoSAXHandler();
		
		WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(args[0]);
		
		try {
			wxsp.setPageCallback(handler);
			wxsp.parse();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
