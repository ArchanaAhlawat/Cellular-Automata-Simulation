package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fire implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		cell.changeToDefault("empty");
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		
	}
	
	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "fire");
		return ret;
	}


}
