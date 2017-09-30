package cell;

import java.util.ArrayList;

public class MoveRandom extends cellMovement {

	CellManager myCellManager;
	
	MoveRandom(CellManager cm) {
		super(cm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean computeAndPerformMovement(GeneralCell cell) {
		ArrayList<GeneralCell> stateCells = checkCellsNextThenDefaultCurrentParameters(cell, getAllCellsAsArrayList(), "empty");
		GeneralCell targetCell = chooseRandomCellFromList(stateCells);
		if (targetCell != null) {
			targetCell.setNextCellParameters(cell.getCurrentCellParameters());
			return true;
		}
		return false;
		
	}
	
	private ArrayList<GeneralCell> getAllCellsAsArrayList() {
		GeneralCell[][] matrix = myCellManager.getMatrix();
		ArrayList<GeneralCell> ret = new ArrayList<GeneralCell>();
		for (GeneralCell [] cell_row: matrix) {
			for (GeneralCell cell : cell_row){
				ret.add(cell);
			}
		}
		return ret;
	}

}
