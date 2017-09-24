package src;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CellManager {
	private static final int GRID_SIZE = 50; // MUST BE 2500 CELLS in XML file
	/*
	 * 1st, matrix is initialized to default --> received from initializer
	 * holds matrix of cell objects --> COMPUTE and SETSTATE.
	 */
	static ArrayList<Cell> initializerList = new ArrayList<Cell>();
	private Cell[][] currentMatrix = new Cell[GRID_SIZE][GRID_SIZE]; // SIZE ? 
	private Rules rules = new Rules();

	/*
	 * use XML parsed data to create cells
	 */
	public void addInitialCells(String simName, Element el) {
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
		setMatrix();
	}
	
	public Cell[][] getMatrix() {
		return currentMatrix;
	}
	
	private void setMatrix() {
		for (int i = 0; i < GRID_SIZE; i++){
			for (int j = 0; j < GRID_SIZE; j++) {
				currentMatrix[i][j] = initializerList.get(0);
				initializerList.remove(0); // unsure if best method
			}
		}
	}
	
	public void performUpdates() {
		computeState();
		//setState();
		// TO DO 
	}
	
	/*
	 * compute state by checking all neighbors
	 */
	private void computeState() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				ArrayList<Cell> neighbors = computeNeighbors(i, j);
				// compute new state for each cell
				// call Rules or specific Rules?
				rules.applyRules(currentMatrix[i][j], neighbors);
			}
		}
	}
	
	private ArrayList<Cell> computeNeighbors(int i, int j) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		// 1st 4 if statements deal with 4 corner cases. only 3 neighbors.
		if (i == 0 && j == 0) { 
			neighbors.add(currentMatrix[i][j+1]);
			neighbors.add(currentMatrix[i+1][j]);
			neighbors.add(currentMatrix[i+1][j+1]);
		}
		else if (i == 0 && j == GRID_SIZE - 1) {
			neighbors.add(currentMatrix[i][j-1]);
			neighbors.add(currentMatrix[i+1][j-1]);
			neighbors.add(currentMatrix[i+1][j]);
		}
		else if (i == GRID_SIZE - 1 && j == 0) {
			neighbors.add(currentMatrix[i-1][j]);
			neighbors.add(currentMatrix[i-1][j+1]);
			neighbors.add(currentMatrix[i][j+1]);
		}
		else if (i == GRID_SIZE - 1 && j == GRID_SIZE - 1) {
			neighbors.add(currentMatrix[i-1][j]);
			neighbors.add(currentMatrix[i-1][j-1]);
			neighbors.add(currentMatrix[i][j-1]);
		}
		// next 4 if statements deal with edge cases (not including corners)
		else if (i == 0) {
			neighbors.add(currentMatrix[i][j-1]);
			neighbors.add(currentMatrix[i+1][j-1]);
			neighbors.add(currentMatrix[i+1][j]);
			neighbors.add(currentMatrix[i+1][j+1]);
			neighbors.add(currentMatrix[i][j+1]);
		}
		else if (i == GRID_SIZE - 1) {
			neighbors.add(currentMatrix[i][j-1]);
			neighbors.add(currentMatrix[i-1][j-1]);
			neighbors.add(currentMatrix[i-1][j]);
			neighbors.add(currentMatrix[i-1][j+1]);
			neighbors.add(currentMatrix[i][j+1]);
		}
		else if (j == 0) {
			neighbors.add(currentMatrix[i-1][j]);
			neighbors.add(currentMatrix[i-1][j+1]);
			neighbors.add(currentMatrix[i][j+1]);
			neighbors.add(currentMatrix[i+1][j+1]);
			neighbors.add(currentMatrix[i+1][j]);
		}
		else if (j == GRID_SIZE - 1) {
			neighbors.add(currentMatrix[i-1][j]);
			neighbors.add(currentMatrix[i-1][j-1]);
			neighbors.add(currentMatrix[i][j-1]);
			neighbors.add(currentMatrix[i+1][j-1]);
			neighbors.add(currentMatrix[i+1][j]);
		}
		else { // middle cells
			neighbors.add(currentMatrix[i-1][j-1]);
			neighbors.add(currentMatrix[i-1][j]);
			neighbors.add(currentMatrix[i-1][j+1]);
			neighbors.add(currentMatrix[i][j+1]);
			neighbors.add(currentMatrix[i+1][j+1]);
			neighbors.add(currentMatrix[i+1][j]);
			neighbors.add(currentMatrix[i+1][j-1]);
			neighbors.add(currentMatrix[i][j-1]);
		}
		return neighbors;
	}
	
	private void setState() {
		// TO DO
	}
	
	public static void setInitialCurrentMatrix(Cell c) {
		initializerList.add(c);
	}
}
