package src;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class CellManager {
	/*
	 * 1st, matrix is initialized to default --> received from initializer
	 * holds matrix of cell objects --> COMPUTE and SETSTATE.
	 * 
	 * NOTE: Concrete cell classes not yet made in this branch
	 * 
	 */
	static ArrayList<Cell> currentMatrix = new ArrayList<Cell>();

	public static void addInitialCells(String simName, Element el) {
		NodeList attributes = el.getElementsByTagName("*"); // special value * matches all tags
		ArrayList<String> attributeList = new ArrayList<String>();
		for (int i = 0; i < attributes.getLength(); i++) {
			Element ele = (Element)attributes.item(0);
			attributeList.add(ele.getFirstChild().getNodeValue());
			System.out.println(ele.getFirstChild().getNodeValue());
		}
		if (simName.equals("Game Of Life")) {  // this section prob not the smartest/best designed way...
			setInitialCurrentMatrix(new GameOfLifeCell(attributeList));
		}
		if (simName.equals("Segregation")) {
			setInitialCurrentMatrix(new SegregationCell(attributeList));
		}
		if (simName.equals("Predator Prey")) {
			setInitialCurrentMatrix(new PredatorPreyCell(attributeList));
		}
		if (simName.equals("Fire")) {
			setInitialCurrentMatrix(new FireCell(attributeList));
		}
	}
	
	public static void setInitialCurrentMatrix(Cell c) {
		currentMatrix.add(c);
	}
}
