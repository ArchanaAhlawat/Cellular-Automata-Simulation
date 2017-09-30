package middleware;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import cell.MoveHelper;
import cell.GeneralCell;
public class CellManager {
	/*
	 * 1st, matrix is initialized to default --> received from initializer
	 * holds matrix of cell objects --> COMPUTE and SETSTATE.
	 */
	private ArrayList<String> initializerList = new ArrayList<String>();
	private GeneralCell[][] currentGrid;
	private Grid gridManager;
	private DefaultValues dfv;
	private CurrentParameters currentParameters;
	private HashMap<String, String> moveMap;
	private MoveHelper mh;
	private ArrayList<GeneralCell> allCellsPossible;
	
	/*
	 * use XML parsed data to create cells
	 */
	
	public void addInitialCells(String state) {
		initializerList.add(state); // before making cells make sure init is done.
		//setMatrix();
	}
	
	private void setMatrix() {
		for (int i = 0; i < initializerList.size(); i++) {
				allCellsPossible.add(createCell(initializerList.get(0)));
				initializerList.remove(0);
		}
		gridManager = new Grid(allCellsPossible);
		setCurrentGrid();
	}
	
	private void setCurrentGrid() {
		currentGrid = gridManager.getGrid();
	}
	
	public ArrayList<GeneralCell> getAllPossibleCells() {
		return allCellsPossible;
	}
	
	public void setDefaultsAndParametersAndMove(DefaultValues df, CurrentParameters cp, MoveHelper move) {
		dfv = df;
		currentParameters = cp;
		mh = move;
		setMatrix();
	}
	
	private GeneralCell createCell(String state) { // TODO make dependent on simulation type
		return new GeneralCell(currentParameters, mh, dfv, state);
	}
	
	public void performUpdates() {
		computeAndSetState();
	}
	
	/*
	 * compute state by checking all neighbors
	 */
	private void computeAndSetState() {
		for (int i = 0; i < currentGrid[0].length; i++) {
			for (int j = 0; j < currentGrid[0].length; j++) {
				ArrayList<GeneralCell> neighbors = computeNeighbors(i, j);
				currentGrid[i][j].computeState(neighbors);
			}
		}
		setState();
	}
	
	private ArrayList<GeneralCell> computeNeighbors(int i, int j) {
		ArrayList<GeneralCell> neighborList = new ArrayList<GeneralCell>();
		for (int[] coor : gridManager.computeNeighbors(i, j)) {
			if (! currentGrid[coor[0]][coor[1]].equals(null)) { // check
				neighborList.add(currentGrid[coor[0]][coor[1]]);
			}
		}
		return neighborList;
	}
	
	private void setState() {
		for (int i = 0; i < currentGrid.length; i++) {
			for (int j = 0; j < currentGrid.length; j++) {
				currentGrid[i][j].becomeNextState();
			}
		}
	}
	
	public GeneralCell[][] getGrid() {
		return currentGrid;
	}
}
