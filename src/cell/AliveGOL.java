package cell;

import java.util.HashMap;
import java.util.Map;

public class AliveGOL implements CellSpecificBehavior {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		int neighborsAliveCount = cell.calcCurrNeighborsOfState("alive").size();
		if (neighborsAliveCount<((GameOfLifeNewCell) cell).getUnderpopulationThreshold())  {
			cell.changeToDefault("empty");
		} else if (neighborsAliveCount>=((GameOfLifeNewCell) cell).getOverpopulationThreshold()) {
			cell.changeToDefault("empty");
		} else if (neighborsAliveCount==((GameOfLifeNewCell) cell).getReproductionNumber()) {
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
		ret.put("state", "alive");
		return ret;
	}

}
