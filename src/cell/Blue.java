package cell;

import java.util.HashMap;
import java.util.Map;

public class Blue extends Color {

//	@Override
//	public void cellSpecificEveryTime(GeneralCell cell) {
//		super.cellSpecificEveryTime(cell);
//
//	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "blue");
		ret.put("hitcount", "10");
		return ret;
	}

}
