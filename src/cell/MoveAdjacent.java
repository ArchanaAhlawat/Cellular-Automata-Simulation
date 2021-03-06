package cell;

import java.util.ArrayList;
import java.util.Map;

import middleware.CellManager;

public class MoveAdjacent extends cellMovement {

	MoveAdjacent(CellManager cm) {
		super(cm);
	}

	@Override
	public boolean computeAndPerformMovement(GeneralCell cell) {
		ArrayList<GeneralCell> stateNeighbors = super.checkCellsNextThenDefaultCurrentParameters(cell, cell.getNeighbors(), "empty");
		GeneralCell targetCell = super.chooseRandomCellFromList(stateNeighbors);
		if (targetCell != null) {
			targetCell.setNextCellParameters(cell.getCurrentCellParameters());
			return true;
		}
		return false;
		
	}

}
