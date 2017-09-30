package middleware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
	 * XML file : cells and initial states. 
	*/
	private static Document dom;
	private static String simName;
	private static CellManager cmanager;
	private static DefaultValues dfv;
	private static CurrentParameters currentParameters;
	private static ArrayList<HashMap<String, String>> defaults = new ArrayList<HashMap<String, String>>();
	private static HashMap<String, String> moveMap = new HashMap<String, String>();
	
	// TODO many static methods
	
	private static void parseXMLFile(String configFileName) { // handle exceptions 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(configFileName);
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
	}// THROW ERRORS!! 
	
	private static void parseDocument() {
		//get root element
		Element docEle = dom.getDocumentElement();
		// get a nodelist of elements
		//NodeList nl_info = docEle.getElementsByTagName("CellType");
		HashMap<String, String> genInfo = new HashMap<String, String>();
		NodeList nl_info_root = docEle.getElementsByTagName("CellInfo");
		if (nl_info_root != null && nl_info_root.getLength() > 0) {
			Element el = (Element)nl_info_root.item(0);
			NodeList nl_info = el.getElementsByTagName("*"); // special value * matches all tags
			setSimName((Element) nl_info.item(0));
			for (int i = 1; i < nl_info.getLength(); i++) { // set at 1 bc we are skipping simName
				Element ele = (Element) nl_info.item(i);
				genInfo.put(ele.getFirstChild().getParentNode().getNodeName(), ele.getFirstChild().getNodeValue());
			}
		}
		NodeList nl = docEle.getElementsByTagName("Cell");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element)nl.item(i);
				NodeList attributes = el.getElementsByTagName("*");
				HashMap<String, String> attributeMap = new HashMap<String, String>();
				for (int j = 0; j < attributes.getLength(); j++) {
					Element ele = (Element) attributes.item(j);
					attributeMap.put(ele.getFirstChild().getParentNode().getNodeName(), ele.getFirstChild().getNodeValue());
				}
				for (String key : genInfo.keySet()) {
					attributeMap.put(key, genInfo.get(key));
				}
				cmanager.addInitialCells(attributeMap.get("state"));
				addDefaultMapAndMoveMap(attributeMap);
			}
		}
		setDefaultsAndCurrentParameters();
		cmanager.setDefaultsAndParametersAndMove(dfv, currentParameters, createMoveHelper());
	}
	
	private static MoveHelper createMoveHelper() {
		return new MoveHelper(cmanager, moveMap);
	}
	
	private static void addDefaultMapAndMoveMap(HashMap<String, String>attributeMap) {
		if (! defaults.contains(attributeMap)) { // unique states
			moveMap.put(attributeMap.get("state"), attributeMap.get("move"));
			defaults.add(attributeMap);
		}
	}
	
	private static void setDefaultsAndCurrentParameters() {
		dfv = new DefaultValues(defaults);
		currentParameters = new CurrentParameters(defaults);
	}
	
	private static void setSimName(Element empEl) {
		simName = empEl.getFirstChild().getNodeValue();
	}
	
	public String getSimName() {
		return simName;
	}
	
	public static Cell[][] loadConfig(String configFileName) {
		parseXMLFile(configFileName);
		parseDocument();
		return cmanager.getMatrix();
	}
	
/*	DEBUGGING PURPOSES
 * public static void main(String[] args) {
		loadConfig("Segregation.xml");
	}*/
}
