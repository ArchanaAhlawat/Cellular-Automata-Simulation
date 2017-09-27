package cell;

public class Agent implements CellSpecificBehavior {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		double totalSimilarNeighbors = cell.calcCurrNeighborsOfState(cell.getState()).size();
		double percentSimilar = totalSimilarNeighbors/cell.getNeighbors().size();
		if (percentSimilar<((SegregationNewCell) cell).getSatisfactionThreshold()){
			cell.move();
		}

	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// Nothing

	}

}
