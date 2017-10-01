package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fish extends Animal{
	
	
	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		super.cellSpecificEveryTime(cell);
		if (((PredPreyNewCell) cell).readyToSpawn()) {
			cell.calcAndReplace("empty", cell.cellSpecificBehavior.get(cell.getState()).getDefaultState());
			((PredPreyNewCell) cell).updateDeathCount();
		}
		if (((PredPreyNewCell) cell).readyToDie()) {
			cell.changeToDefault("empty");
		}
	}
	
	

	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "fish");
		ret.put("spawncount", "0");
		ret.put("deathcount", "0");
		return ret;
	}
	


}
