package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class PredatorPreyRules extends Rules {

	public PredatorPreyRules(HashMap<String, String> map) {
		super(map);
	}
	
	
	chooseRandomCell

	@Override
	protected void setInstanceVariables() {
		// TODO Auto-generated method stub

	}	

	public void applyRules(PredatorPreyCell cell, Collection<PredatorPreyCell> cellNeighbors) {
		this.myNeighbors = cellNeighbors;
		String currentCellState = cell.getState();
		switch (currentCellState) {
		case "fish":
			fishRules(cell);
			break;
		case "shark":
			sharkRules(cell);
			break;
		case "empty":
			break;
		default:
			break;
		}
	}

	public int getCellSpawnCount() {
		if (getCurrentCell().
			return Integer.parseInt(getCurrentCell().getParameterMap().get("spawncount"));
		} else {
			return -1;
		}

	}

	public void fishRules(PredatorPreyCell fish) {
		
		spawnNewFish();
	}

	public void sharkRules(PredatorPreyCell shark) {
		eatFish();
	}

	public void spawnNewFish() {
		ArrayList<PredatorPreyCell> emptyNeighbors = calculateEmptyNeighbors();
		PredatorPreyCell targetSpawnCell = (PredatorPreyCell) chooseRandomCellFromList(emptyNeighbors);
		if (targetSpawnCell != null) {
			targetSpawnCell.setNextState("newfish");
		}
	}

	public ArrayList<PredatorPreyCell> calculateEmptyNeighbors() {
		return calculateNeighborsOfState("empty");
	}

	public ArrayList<PredatorPreyCell> calculateFishNeighbors() {
		return calculateNeighborsOfState("fish");
	}

	public void eatFish() {
		ArrayList<PredatorPreyCell> fishNeighbors = calculateFishNeighbors();
		PredatorPreyCell targetFishCell = chooseRandomCellFromList(fishNeighbors);
		if (targetFishCell != null) {
			targetFishCell.setNextState("empty");
		}
	}


}
