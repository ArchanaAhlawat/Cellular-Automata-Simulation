package rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import middleware.Cell;

public abstract class Rules {

	HashMap<String, String> myParameterMap;
	HashMap<String, String> cellParameterMap;
	HashMap<String, String> myUpdatedValuesMap = new HashMap<String, String>();
	Collection<? extends Cell> myNeighbors;
	Cell myCell; 

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
	protected HashMap<String, String> getMyParameterMap() {
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
	public <T extends Cell> void applyRules(T cell, Collection<T> cellNeighbors) {
		setNeighbors(cellNeighbors);
		setCurrentCell(cell);
		performRules();
	}
	
	protected HashMap<String, String> getCellParameterMap() {
		return this.cellParameterMap;
	}
	
	protected setCellParameterMap(Cell cell) {
		this.cellParameterMap = cell.getParameterMap();
	}
	
	protected abstract void performRules();

	protected Collection<? extends Cell> getNeighbors() {
		return this.myNeighbors;
	}
	
	protected <T extends Cell> void setNeighbors(Collection<T> neighbors) {
		this.myNeighbors = neighbors;
	}
	
	protected Cell getCurrentCell() {
		return this.myCell;
	}
	
	public <T extends Cell> void setCurrentCell(T cell) {
		this.myCell = cell;
	}
	
	protected String getCellState() {
		return getCellParameterMap().get("state");
	}

	protected void setCellNextState(String s) {
		getUpdatedValuesMap().put("state", s);
	}
	
	public <T extends Cell> ArrayList<T> calculateNeighborsOfState(String state) {
		Collection<T> cellNeighbors = (Collection<T>) this.getNeighbors();
		ArrayList<T> stateNeighbors = new ArrayList<>();
		for (T cell : cellNeighbors) {
			if (cell.getParameterMap().get("state").equals(state)) {
				stateNeighbors.add(cell);
			}
		}
		return stateNeighbors;

	}
	
	protected HashMap<String, String> getUpdatedValuesMap() {
		return this.myUpdatedValuesMap;
	}
	
	protected void setNextMove(String s) {
		getUpdatedValuesMap().put("move", s);
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



	protected <T extends Cell> T chooseRandomCellFromList(ArrayList<T> cellList) {
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
