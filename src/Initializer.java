package src;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Initializer {
	/* 
	 * reads XML file based on choice from Main
	 * initialize CellManager & Rules 
	 *interacts w frontend to get what?
	 * XML file : coordinates of cells. initial states. 
	 * each cell has a tag. 
	*/
	private static Document dom;
	private static String simName;
	
	private static void parseXMLfile() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse("Segregation.xml");
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (SAXException se) {
			se.printStackTrace();
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}
	
	private static void parseDocument() {
		//get root element
		Element docEle = dom.getDocumentElement();
		// get a nodelist of elements
		NodeList nl_info = docEle.getElementsByTagName("CellType");
		if (nl_info != null && nl_info.getLength() > 0) {
			Element el = (Element)nl_info.item(0);
			setSimName(el);
		}
		NodeList nl = docEle.getElementsByTagName("Cell");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element)nl.item(i);
				CellManager.addInitialCells(simName, el);
			}
		}
	}
	
	private static void setSimName(Element empEl) {
		simName = empEl.getAttribute(("simName"));
		System.out.println(simName);
	}
	
	public String getSimName() {
		return simName;
	}
	
    public static void main(String[] args) {
        parseXMLfile();
        parseDocument();  
    }
}
