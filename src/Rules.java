package src;
import java.util.Collection;
import java.util.HashMap;

import javafx.scene.control.Cell;

public abstract class Rules {
	
	HashMap<String, String> myParameterMap;
	
	public Rules(HashMap<String, String> map) {
		this.myParameterMap = map;
	}
	
	/**
	 * @author Samuel Slack (sls97)
	 * @param
	 * @return HashMap<String, String> - map of game rules parameter names to values
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
	 * Uses a map of the game rule paraemters to their values to set all the 'special' values that slightly modify game rules.
	 */
	protected abstract void setInstanceVariables();
	

	/**
	 * 
	 * 
	 * @author SamuelSlack (sls97)
	 * @param Cell - primary cell to consider state of
	 * 		  Collection<Cell> - collection of primary Cell's neighbor Cells
	 * @return Object newCellState
	 */
	public abstract Object applyRules(Cell cell, Collection<Cell> cellNeighbors);
	
}
