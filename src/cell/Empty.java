package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Empty implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		// Nothing
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
