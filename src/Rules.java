package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.control.Cell;

public abstract class Rules {

	HashMap<String, String> myParameterMap;
	Collection<? extends CellSocietyCell> myNeighbors;
	CellSocietyCell myCell; 

	public Rules(HashMap<String, String> map) {
		this.myParameterMap = map;
	}

	/**
	 * @author Samuel Slack (sls97)
	 * @param
	 * @return HashMap<String, String> - map of game rules parameter names to
	 *         values
	 * 
	 */
	protected HashMap<String, String> getParameterMap() {
		return this.myParameterMap;
	}

	/**
	 * 
	 * @author Samuel Slack (sls97)
	 * @param
	 * @return void
	 * 
	 *         Uses a map of the game rule parameters to their values to set all
	 *         the 'special' values that slightly modify game rules.
	 */
	protected abstract void setInstanceVariables();

	/**
	 * 
	 * 
	 * @author SamuelSlack (sls97)
	 * @param Cell
	 *            - primary cell to consider state of Collection<Cell> -
	 *            collection of primary Cell's neighbor Cells
	 * @return Object newCellState
	 */
	public <T extends CellSocietyCell> void applyRules(T cell, Collection<T> cellNeighbors) {
		setNeighbors(cellNeighbors);
		setCurrentCell(cell);
		performRules();
	}
	
	protected abstract void performRules();

	protected Collection<? extends CellSocietyCell> getNeighbors() {
		return this.myNeighbors;
	}
	
	protected <T extends CellSocietyCell> void setNeighbors(Collection<T> neighbors) {
		this.myNeighbors = neighbors;
	}
	
	protected CellSocietyCell getCurrentCell() {
		return this.myCell;
	}
	
	public <T extends CellSocietyCell> void setCurrentCell(T cell) {
		this.myCell = cell;
	}
	
	protected String getCellState() {
		return getCurrentCell().getState();
	}

	public <T extends CellSocietyCell> ArrayList<T> calculateNeighborsOfState(String state) {
		Collection<T> cellNeighbors = (Collection<T>) this.getNeighbors();
		ArrayList<T> stateNeighbors = new ArrayList<>();
		for (T cell : cellNeighbors) {
			if (cell.getState().equals(state)) {
				stateNeighbors.add(cell);
			}
		}
		return stateNeighbors;

	}
	
	protected void setNextMove(String s) {
		getCurrentCell().setNextMove(s);
	}
	
	protected void setMoveAdjacent() {
		setNextMove("adjacent");
	}
	
	protected void setMoveRandom() {
		setNextMove("random");
	}
	
	protected void setMoveStay() {
		setNextMove("stay");
	}



	protected <T extends CellSocietyCell> T chooseRandomCellFromList(ArrayList<T> cellList) {
		int num_cells = cellList.size();
		if (num_cells > 0) {
			Random rand = new Random();
			int random_index = rand.nextInt(num_cells);
			T targetRandomCell = cellList.get(random_index);
			return targetRandomCell;
		}
		return null;
	}

}
