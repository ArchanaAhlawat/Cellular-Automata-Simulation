package cell;

import java.util.HashMap;
import java.util.Map;

public class Blue extends RPSColor {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "blue");
		ret.put("hitcount", "0");
		return ret;
	}

}
