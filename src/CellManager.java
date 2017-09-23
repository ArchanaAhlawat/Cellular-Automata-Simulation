package src;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CellManager {
	/*
	 * 1st, matrix is initialized to default --> received from initializer
	 * holds matrix of cell objects --> COMPUTE and SETSTATE.
	 */
	static ArrayList<Cell> currentMatrix = new ArrayList<Cell>();

	/*
	 * use XML parsed data to create cells
	 */
	public static void addInitialCells(String simName, Element el) {
		NodeList attributes = el.getElementsByTagName("*"); // special value * matches all tags
		HashMap<String, String> attributeMap = new HashMap<String, String>();
		for (int i = 0; i < attributes.getLength(); i++) {
			Element ele = (Element)attributes.item(i);
			attributeMap.put(ele.getFirstChild().getParentNode().getNodeName(), ele.getFirstChild().getNodeValue());
			System.out.println(ele.getFirstChild().getParentNode().getNodeName());
			System.out.println(ele.getFirstChild().getNodeValue());
		}
		if (simName.equals("Game Of Life")) {  // this section prob not the smartest/best designed way...
			setInitialCurrentMatrix(new GameOfLifeCell(attributeMap));
		}
		if (simName.equals("Segregation")) {
			setInitialCurrentMatrix(new SegregationCell(attributeMap));
		}
		if (simName.equals("Predator Prey")) {
			setInitialCurrentMatrix(new PredatorPreyCell(attributeMap));
		}
		//if (simName.equals("Fire")) {
		//	setInitialCurrentMatrix(new FireCell(attributeList));
		//}
	}
	
	public static void setInitialCurrentMatrix(Cell c) {
		currentMatrix.add(c);
	}
}
