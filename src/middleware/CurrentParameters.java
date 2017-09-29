package middleware;

import java.util.ArrayList;
import java.util.HashMap;

/* so that the cell does not have to keep track of all these game-wide values
 * initialized in Initializer
 * string to hashmap. similar to dfv but dfv never changes
 * currentparameters change based on user input/choice
*/
public class CurrentParameters {
	private HashMap<String, HashMap<String, String>> currentParams = new HashMap<String, HashMap<String, String>>();
	
	public CurrentParameters(ArrayList<HashMap<String, String>> currentMaps) {
		for (HashMap<String, String> df_map : currentMaps) {
			currentParams.put(df_map.get("state"), df_map);
		}
	}
	
	public HashMap<String, String> getCurrentParameterMap(String state) {
		return currentParams.get(state);
	} // front end uses or cell manager?
	
	/*
	 * @param state: cell state that should be affected
	 * @param parameterToChange: specific parameter that should change
	 * @param newValue: String value that parameter should change to
	 */
	public void changeOrAddParameterValues(String state, String parameterToChangeOrAdd, String newValue) {
		HashMap<String, String> mapChange = currentParams.get(state);
		mapChange.put(parameterToChangeOrAdd, newValue); // will either replace existing k,v pair or add a new one
		currentParams.put(state, mapChange); 
	}
}
