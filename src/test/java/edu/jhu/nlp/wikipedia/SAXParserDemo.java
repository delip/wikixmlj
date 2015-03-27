package edu.jhu.nlp.wikipedia;

import java.net.MalformedURLException;

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

        WikiXMLParser wxsp = null;
        try {
            wxsp = WikiXMLParserFactory.getSAXParser(args[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return;
        }

        try {
			wxsp.setPageCallback(handler);
			wxsp.parse();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
