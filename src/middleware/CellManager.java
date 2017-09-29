package middleware;

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
	// TODO:
	private ArrayList<String> initializerList = new ArrayList<String>();
	private GeneralCell[][] currentMatrix = new GeneralCell[GRID_SIZE][GRID_SIZE]; // SIZE ? 
	private Neighbors neighbors = new Neighbors();
	private DefaultValues dvf;
	private CurrentParameters currentParameters;
	
	/*
	 * use XML parsed data to create cells
	 */
	
	public void addInitialCells(String state) {
		setInitialCurrentMatrix(state); // before making cells make sure init is done.
		setMatrix();
	}
	
	public Cell[][] getMatrix() { 
		return currentMatrix;
	}
	
	private void setMatrix() {
		for (int i = 0; i < GRID_SIZE; i++){
			for (int j = 0; j < GRID_SIZE; j++) {
				currentMatrix[i][j] = createCell(initializerList.get(0));
				initializerList.remove(0); // unsure if best method -- try iterator 
			}
		}
	}
	
	private GeneralCell createCell(String state) { // TODO make dependent on simulation type
		
		return Cell();
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
				// for each cell cell.computeState(). will automatically update next state automatically.
				// then go thru all cells, cell.becomeNextState()
				HashMap<String, String> updatedVals = rules.applyRules(currentMatrix[i][j], neighbors);
				setState(updatedVals, i, j); // change this. bc need to update 
			}
		}
	}
	
	private ArrayList<Cell> computeNeighbors(int i, int j) {
		ArrayList<Cell> neighborList = new ArrayList<Cell>();
		for (int[][] coor : neighbors.computeNeighbors(i, j)) {
			if (! currentMatrix[coor[0][0]][coor[1][0]].equals(null)) { // check
				neighborList.add(currentMatrix[coor[0][0]][coor[1][0]]);
			}
		}
		return neighborList;
	}
	
	private void setState(HashMap<String, String> updatedVals, int i, int j) {
		// update cell values based on map taken from Rules
		for (String key : updatedVals.keySet()) {
			// NEED TO MAKE NEW MATRIX (NEXT MATRIX) . shark example.
			currentMatrix[i][j].myParameterMap.put(key, updatedVals.get(key));
		}
	}
	
	private void setInitialCurrentMatrix(String state) {
		initializerList.add(state);
	}
}
