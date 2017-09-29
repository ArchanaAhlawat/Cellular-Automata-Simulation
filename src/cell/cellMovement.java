package cell;

import java.util.ArrayList;
import java.util.Random;

public abstract class cellMovement {

	cellMovement(CellManager cm) {

	}

	protected abstract boolean computeAndPerformMovement(GeneralCell cell);
	
	protected ArrayList<GeneralCell> checkCellStatesCurrent(ArrayList<GeneralCell> allCells, String state) {
		return allCells;
	}

	protected ArrayList<GeneralCell> checkNeighborsCurrentParameters(GeneralCell cell, String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell dummy_cell : cell.getNeighbors()) {
			if (dummy_cell.getCurrentGameParameters().get("state").equals(state)) {
				stateNeighbors.add(dummy_cell);
			}
		}
		return stateNeighbors;
	}

	protected ArrayList<GeneralCell> checkNeighborsNextParameters(GeneralCell cell, String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell dummy_cell : cell.getNeighbors()) {
			if (dummy_cell.getNextCellParameters().get("state").equals(state)) {
				stateNeighbors.add(dummy_cell);
			}
		}
		return stateNeighbors;
	}

	protected ArrayList<GeneralCell> checkNeighborsNoNextParameters(GeneralCell cell, String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell dummy_cell : cell.getNeighbors()) {
			if (dummy_cell.getNextCellParameters().size()==0 && dummy_cell.getCurrentGameParameters().get("state").equals(state)) {
				stateNeighbors.add(dummy_cell);
			}
		}
		return stateNeighbors;
	}

	protected ArrayList<GeneralCell> checkCellsNextThenDefaultCurrentParameters(GeneralCell cell, ArrayList<GeneralCell> allCells, String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
//		for (GeneralCell dummy_cell : cell.getNeighbors()) {
		for (GeneralCell dummy_cell : allCells) {
			if (dummy_cell.getNextCellParameters().containsKey("state")) {
				if (dummy_cell.getNextCellParameters().get("state").equals(state)) {
					stateNeighbors.add(dummy_cell);
				}
			} else {
				if (dummy_cell.getCurrentGameParameters().get("state").equals(state)) {
					stateNeighbors.add(dummy_cell);
				}
			}
		}
		return stateNeighbors;
	}
	
	
	protected GeneralCell chooseRandomCellFromList(ArrayList<GeneralCell> cellList) {
		int num_cells = cellList.size();
		if (num_cells > 0) {
			Random rand = new Random();
			int random_index = rand.nextInt(num_cells);
			GeneralCell targetRandomCell = cellList.get(random_index);
			return targetRandomCell;
		}
		return null;
	}

}
