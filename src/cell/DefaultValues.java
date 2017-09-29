package cell;

import java.util.HashMap;
import java.util.Map;

public class DefaultValues {

	Map<String, Map<String, String>> cellDefaultValues;
	
	DefaultValues(Map<String, Map<String, String>> allDefaultParameters) {
		cellDefaultValues = allDefaultParameters;
		}
	
	public Map<String, Map<String, String>> returnAllDefaults() {
		return cellDefaultValues;
	}
	
	
	public Map<String, String> getDefaultForState(String state) {
		return cellDefaultValues.get(state);
	}
	
	
	public Map<String, String> returnDefaults(GeneralCell cell) {
		return cellDefaultValues.get(cell.getState());
	}
	
}
