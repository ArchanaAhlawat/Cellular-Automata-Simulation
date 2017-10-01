package cell;

import java.util.Map;

public abstract class Animal implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		((PredPreyNewCell) cell).updateSpawnCount();
		
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
