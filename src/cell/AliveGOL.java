package cell;

public class AliveGOL implements CellSpecificBehavior {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		int neighborsAliveCount = cell.calcCurrNeighborsOfState("alive").size();
		if (neighborsAliveCount<((GameOfLifeNewCell) cell).getUnderpopulationThreshold())  {
			cell.setNextParameterValues(cell.getDefaults().get("dead"));
		} else if (neighborsAliveCount>((GameOfLifeNewCell) cell).getOverpopulationThreshold()) {
			cell.setNextParameterValues(cell.getDefaults().get("dead"));
		} else if (neighborsAliveCount==((GameOfLifeNewCell) cell).getReproductionNumber() && cell.getState().equals("dead")) {
			cell.setNextParameterValues(cell.getDefaults().get("alive"));
		} 

	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// Nothing
	}

}
