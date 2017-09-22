package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
	private static ArrayList<Cell> myCells = new ArrayList<Cell>();
	private static String simName;
	
	private static void parseXMLfile() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse("testconfig.xml");
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
	
	private static  void parseDocument() {
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
				// get Cell element
				Element el = (Element)nl.item(i);
				// get Cell object
				Cell c = getCell(el);
				myCells.add(c); // null pointer exception haha!!!
			}
		}
		
	}
	
	private static void setSimName(Element empEl) {
		simName = empEl.getAttribute(("simName"));
		System.out.println(simName);
	}
	
	private static Cell getCell(Element empEl) {
		// for each <Cell> element get text or int values of attrib
		String attrib = getTextValue(empEl, "attrib");
		String type = empEl.getAttribute(("type"));
		//Create new Cell w the value read in from the xml node
		Cell c = new Cell(attrib, type); // fake cell
		return c;
	}
	
	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
	
	private static void printData() { // debugging purposes
		System.out.println("No of Cells " + myCells.size());
		Iterator<Cell> it = myCells.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		System.out.println("SimName: " + simName);
	}
	
	public String getSimName() {
		return simName;
	}
	
    public static void main(String[] args) {
        parseXMLfile();
        parseDocument();
        printData();   
    }
}
