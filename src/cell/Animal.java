package cell;

public class Animal implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		((PredPreyNewCell) cell).updateSpawnCount();
		if (((PredPreyNewCell) cell).readyToSpawn()) {
			cell.calcAndReplace("empty", cell.getDefaults().get(cell.getState()));
		}
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		if (cell.getNextParameterValues().size() <= 0) {
			cell.move();
		}
		
	}

}
