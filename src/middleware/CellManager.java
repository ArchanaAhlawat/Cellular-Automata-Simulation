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
	private ArrayList<Cell> initializerList = new ArrayList<Cell>();
	private Cell[][] currentMatrix = new Cell[GRID_SIZE][GRID_SIZE]; // SIZE ? 
	private Rules rules = new Rules(); // DEFAULT STATES!!!!! 
	

	/*
	 * use XML parsed data to create cells
	 */
	public void addInitialCells(HashMap<String, String> attributeMap) {
		setInitialCurrentMatrix(new Cell(attributeMap));
		setMatrix();
	}
	
	public Cell[][] getMatrix() {
		return currentMatrix;
	}
	
	private void setMatrix() {
		for (int i = 0; i < GRID_SIZE; i++){
			for (int j = 0; j < GRID_SIZE; j++) {
				currentMatrix[i][j] = initializerList.get(0);
				initializerList.remove(0); // unsure if best method -- try iterator 
			}
		}
	}
	
	public void performUpdates() {
		computeAndSetState();
	}
	
	/*
	 * compute state by checking all neighbors
	 */
	private HashMap<String, String> computeAndSetState() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				ArrayList<Cell> neighbors = computeNeighbors(i, j);
				// compute new state for each cell
				// call Rules or specific Rules?
				HashMap<String, String> updatedVals = rules.applyRules(currentMatrix[i][j], neighbors);
				setState(updatedVals, i, j); // change this. bc need to update 
			}
		}
	}
	
	private ArrayList<Cell> computeNeighbors(int i, int j) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		int[] indices = new int[]{-1,0,1};
		for (int k: indices) {
			for (int l: indices) {
				if (! currentMatrix[i+k][j+l].equals(null)) {
					if (! neighbors.contains(currentMatrix[i+k][j+l])) {
						neighbors.add(currentMatrix[k][l]);
					}
				}
			}
		}
		return neighbors;
	}
	
	private void setState(HashMap<String, String> updatedVals, int i, int j) {
		// update cell values based on map taken from Rules
		for (String key : updatedVals.keySet()) {
			// NEED TO MAKE NEW MATRIX (NEXT MATRIX) . shark example.
			currentMatrix[i][j].myParameterMap.put(key, updatedVals.get(key));
		}
	}
	
	private void setInitialCurrentMatrix(Cell c) {
		initializerList.add(c);
	}
}
