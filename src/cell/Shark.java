package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shark extends Animal{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		super.cellSpecificEveryTime(cell);
		if (((PredPreyNewCell) cell).readyToSpawn()) {
			((PredPreyNewCell) cell).revertSpawnCount();
			cell.calcAndReplace("empty", cell.cellSpecificBehavior.get(cell.getState()).getDefaultState());
		}
		((PredPreyNewCell) cell).updateDeathCount();
		if (((PredPreyNewCell) cell).readyToDie()) {
			cell.changeToDefault("empty");
		}
		eatFish(cell);
		
	}

//	@Override
//	public void cellSpecificBasedOnNextState(GeneralCell cell) {
//		// TODO Auto-generated method stub
//		
//	}
//	
	private void eatFish(GeneralCell cell){
		if (cell.calcAndReplace("fish", cell.getCurrentCellParameters())){
		cell.changeToDefault("empty");
		((PredPreyNewCell) cell).setDeathCount(0);
		}
	}
	
	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "shark");
		ret.put("spawncount", "0");
		ret.put("deathcount", "0");
		return ret;
	}
	
//	private boolean checkDeath(GeneralCell cell){
//		if(((PredPreyNewCell) cell).getDeathCount()>getSharkDeathThreshold(cell)) {
//			cell.changeToDefault("empty");
//			return true;
//		}
//		return false;
//	}
	
//	private int getSharkDeathThreshold(GeneralCell cell) {
//		return Integer.parseInt(cell.getCurrentGameParameters().get("sharkdeaththreshhold"));
//	}
	


	
}
