package middleware;
import java.util.ArrayList;
import java.util.HashMap;

public class DefaultValues {
	private HashMap<String, HashMap<String, String>> defaults = new HashMap<String, HashMap<String, String>>();
	
	public DefaultValues(ArrayList<HashMap<String, String>> defaultMaps) {
		for (HashMap<String, String> df_map : defaultMaps) {
			defaults.put(df_map.get("state"), df_map);
		}
	}
	
	public HashMap<String, String> getDefaultMap(String state) {
		return defaults.get(state);
	}
}
