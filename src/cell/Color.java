package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Color extends RPSColor {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		boolean attackStatus = ((RPSCell) cell).RPSMap.get(new ArrayList<String>(Arrays.asList(cell.getState(), cellToAttack.getState())));
		if (attackStatus) {
			((RPSCell) cellToAttack).decrementNextHitCount();
			if (((RPSCell) cellToAttack).checkDead()){
				cellToAttack.setNextCellParameters(cell.cellSpecificBehavior.get(cell.getState()).getDefaultState());
			}
		} else {
			((RPSCell) cell).decrementNextHitCount();
			if (((RPSCell) cell).checkDead()){
				cell.setNextCellParameters(cell.cellSpecificBehavior.get(cellToAttack.getState()).getDefaultState());
			}
		}
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {

	}

	@Override
	public abstract Map<String, String> getDefaultState();

}
