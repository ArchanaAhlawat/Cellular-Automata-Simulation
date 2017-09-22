package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class PredatorPreyRules extends Rules {

	public PredatorPreyRules(HashMap<String, String> map) {
		super(map);
	}

	@Override
	public PredatorPreyCell getCurrentCell() {
		return (PredatorPreyCell) myCell;
	}

	@Override
	protected void setInstanceVariables() {
		// TODO Auto-generated method stub
	}

	public void performRules() {
		String currentCellState = getCellState();
		switch (currentCellState) {
		case "fish":
			fishRules();
			break;
		case "shark":
			sharkRules();
			break;
		case "empty":
			break;
		default:
			break;
		}
	}

	public int getCellSpawnCount() {
		return getCurrentCell().getSpawnCount();
	}

	public void fishRules() {
		if (getCurrentCell().spawnNow()) {
			spawnNewFish();
		} 
		setMoveAdjacent();
	}

	public void sharkRules() {
		if (!eatFish()) {
			setMoveAdjacent();
		}
		
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

	public boolean eatFish() {
		ArrayList<PredatorPreyCell> fishNeighbors = calculateFishNeighbors();
		PredatorPreyCell targetFishCell = chooseRandomCellFromList(fishNeighbors);
		if (targetFishCell != null) {
			targetFishCell.setNextState("empty");
			return true;
		}
		return false;
	}

}
