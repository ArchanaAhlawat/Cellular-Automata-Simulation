package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fish extends Animal{

	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "fish");
		ret.put("spawncount", "0");
		return ret;
	}
	


}
