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

	public void fishRules() {
		checkAndImplementSpawn();
		setMoveAdjacent();
	}

	public void sharkRules() {
		checkAndImplementSpawn();
		if (!eatFish()) {
			setMoveAdjacent();
		}
	}

	public void checkAndImplementSpawn() {
		if (getCurrentCell().spawnNow()) {
			spawnNew();
		}
	}

	public void spawnNewFish() {
		spawnNew("fish");
	}

	public void spawnNewShark() {
		spawnNew("shark");
	}

	public void spawnNew(String s) {
		ArrayList<PredatorPreyCell> emptyNeighbors = calculateEmptyNeighbors();
		PredatorPreyCell targetSpawnCell = (PredatorPreyCell) chooseRandomCellFromList(emptyNeighbors);
		if (targetSpawnCell != null) {
			targetSpawnCell.setNextState("new" + s);
			targetSpawnCell.setNextMove("stay");
		}
	}

	public void spawnNew() {
		String s = getCellState();
		ArrayList<PredatorPreyCell> emptyNeighbors = calculateEmptyNeighbors();
		PredatorPreyCell targetSpawnCell = (PredatorPreyCell) chooseRandomCellFromList(emptyNeighbors);
		if (targetSpawnCell != null) {
			targetSpawnCell.setNextState("new" + s);
			targetSpawnCell.setNextMove("stay");
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
