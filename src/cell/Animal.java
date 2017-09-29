package cell;

import java.util.Map;

public abstract class Animal implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		((PredPreyNewCell) cell).updateSpawnCount();
		if (((PredPreyNewCell) cell).readyToSpawn()) {
			cell.calcAndReplace("empty", cell.cellSpecificBehavior.get(cell.getState()).getDefaultState());
		}
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		if (cell.getNextCellParameters().size() <= 0) {
			cell.move();
		}
		
	}

	@Override
	public abstract Map<String, String> getDefaultState();

}
