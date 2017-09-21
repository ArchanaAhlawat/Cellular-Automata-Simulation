package src;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.control.Cell;

public class FireRules extends Rules {

	double probCatch;
	
	public FireRules(HashMap<String, String> map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setInstanceVariables() {
		this.probCatch = Double.parseDouble(this.getParameterMap().get("probCatch"));
		
	}
	
	private double getProbCatch() {
		return this.probCatch;
	}

	@Override
	public Object applyRules(Cell cell, Collection<Cell> cellNeighbors) {
		String currentCellState = cell.getState();
		
		if (currentCellState.equals("empty")) {
			return "empty";
		}
		
		if (currentCellState.equals("fire")) {
			return "empty";
		}
		
		ArrayList<String> neighborCellStates = new ArrayList<String>();
		for (Cell neighborCell : cellNeighbors) {
			neighborCellStates.add(neighborCell.getState());
		}
		
		if (neighborCellStates.contains("fire")) {
			Random rand = new Random();
			if (((double) rand.nextInt(100)/100)<=this.getProbCatch()) {
				return "fire";
			} else {
				return currentCellState;
			}
		}
		
	}

	

}
