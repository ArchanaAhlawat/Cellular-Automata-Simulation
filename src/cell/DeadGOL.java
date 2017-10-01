package cell;

import java.util.HashMap;
import java.util.Map;

public class DeadGOL implements CellSpecificBehavior {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		int neighborsAliveCount = cell.calcCurrNeighborsOfState("alive").size();
		if (neighborsAliveCount==((GameOfLifeNewCell) cell).getReproductionNumber()) {
			cell.changeToDefault("alive");
		} 

	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// Nothing
	}
	
	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "empty");
		return ret;
	}

}
