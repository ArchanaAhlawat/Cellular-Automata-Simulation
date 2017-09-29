package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shark extends Animal{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		super.cellSpecificEveryTime(cell);
		eatFish(cell);
		
	}

//	@Override
//	public void cellSpecificBasedOnNextState(GeneralCell cell) {
//		// TODO Auto-generated method stub
//		
//	}
//	
	private void eatFish(GeneralCell cell){
		cell.calcAndReplace("fish", cell.getCurrentCellParameters());
		cell.changeToDefault("empty");
	}
	
	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "shark");
		ret.put("spawncount", "0");
		return ret;
	}
	


	
}
