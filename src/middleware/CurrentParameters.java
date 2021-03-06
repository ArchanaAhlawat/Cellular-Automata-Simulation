package middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* so that the cell does not have to keep track of all these game-wide values
 * initialized in Initializer
 * string to hashmap. similar to dfv but dfv never changes
 * currentparameters change based on user input/choice
*/
public class CurrentParameters {
	private HashMap<String, HashMap<String, String>> currentParams = new HashMap<String, HashMap<String, String>>();
	private DefaultValues dfv;
	private Map<String, String> gameLevelParams;

	public CurrentParameters(ArrayList<HashMap<String, String>> currentMaps, DefaultValues defaults, Map<String, String> gameLevelParams) {
		for (HashMap<String, String> df_map : currentMaps) {
			currentParams.put(df_map.get("state"), df_map);
		}
		System.out.println("keyset of currentParams: ");
		for (String key : currentParams.keySet()) {
			System.out.println("Key: " + key + "; " + currentParams.get(key));
		}
		dfv = defaults;
		this.gameLevelParams = gameLevelParams;
	}

	public Map<String, String> getGameLevelParameterMap() {
		return gameLevelParams;
	}
	
	public Map<String, String> getCurrentParameterMap(String state) {
		HashMap<String, String> val = currentParams.get(state);
		return val;
	}
	
	public void setGameLevelParameter(String param, String value) throws IllegalArgumentException {
		if (!gameLevelParams.containsKey(param)) {
			throw new IllegalArgumentException();
		}
		gameLevelParams.put(param, value);
	}

	/*
	 * @param state: cell state that should be affected
	 * 
	 * @param parameterToChange: specific parameter that should change
	 * 
	 * @param newValue: String value that parameter should change to
	 */
	public void changeOrAddParameterValues(String state, String parameterToChangeOrAdd, String newValue) {
		HashMap<String, String> mapChange = currentParams.get(state);
		mapChange.put(parameterToChangeOrAdd, newValue); // will either replace existing k,v pair or add a new one
		currentParams.put(state, mapChange);
	}

	public void revertToDefaultValues() {
		for (String state : currentParams.keySet()) {
			currentParams.put(state, dfv.getDefaultMap(state));
		}
	}
}
