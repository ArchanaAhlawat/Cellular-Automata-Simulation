package cell;

import java.util.HashMap;
import java.util.Map;

public class Agent2 extends Agent{

	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "agent2");
		return ret;
	}

}
