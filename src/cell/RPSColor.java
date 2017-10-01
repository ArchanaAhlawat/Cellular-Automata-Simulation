 package cell;

import java.util.Map;

public abstract class RPSColor implements CellSpecificBehavior{
	
	protected GeneralCell cellToAttack;

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		cellToAttack = cell.chooseRandomCellFromList(cell.getNeighbors());	
	}
	
	public int cellToAttackHitCount() {
		return Integer.parseInt(cellToAttack.getCurrentCellParameters().get("hitcount"));
	}
	

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {

		
	}

	@Override
	public abstract Map<String, String> getDefaultState();
	
	
	

}
