package cell;

import java.util.Map;

public abstract class RPSColor implements CellSpecificBehavior{
	
	protected GeneralCell cellToAttack;

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		cellToAttack = cell.chooseRandomCellFromList(cell.getNeighbors());	
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public abstract Map<String, String> getDefaultState();
	
	
	

}
