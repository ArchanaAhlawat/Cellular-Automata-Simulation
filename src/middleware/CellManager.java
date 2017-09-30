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
	private ArrayList<String> initializerList = new ArrayList<String>();
	private GeneralCell[][] currentMatrix = new GeneralCell[GRID_SIZE][GRID_SIZE]; // SIZE ? 
	private Neighbors neighbors = new Neighbors();
	private DefaultValues dfv;
	private CurrentParameters currentParameters;
	private HashMap<String, String> moveMap;
	private MoveHelper mh;
	
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
	
	public void setDefaultsAndParametersAndMove(DefaultValues df, CurrentParameters cp, MoveHelper move) {
		dfv = df;
		currentParameters = cp;
		mh = move;
	}
	
	private GeneralCell createCell(String state) { // TODO make dependent on simulation type
		return Cell(currentParameters, dfv, mh, state);
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
				ArrayList<GeneralCell> neighbors = computeNeighbors(i, j);
				currentMatrix[i][j].computeState(neighbors);
			}
		}
		setState();
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
	
	private void setState() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				currentMatrix[i][j].becomeNextState();
			}
		}
	}
	
	private void setInitialCurrentMatrix(String state) {
		initializerList.add(state);
	}
}
