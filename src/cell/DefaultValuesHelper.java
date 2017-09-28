package cell;

import java.util.HashMap;
import java.util.Map;

public class DefaultValuesHelper {

	Map<String, Map<String, String>> cellDefaultValues;
	
	DefaultValuesHelper(Map<String, Map<String, String>> allDefaultParameters) {
		cellDefaultValues = allDefaultParameters;
		}
	
	public Map<String, Map<String, String>> returnAllDefaults() {
		return cellDefaultValues;
	}
	
	
	public Map<String, String> returnDefaultForState(String state) {
		return cellDefaultValues.get(state);
	}
	
	
	public Map<String, String> returnDefaults(GeneralCell cell) {
		return cellDefaultValues.get(cell.getState());
	}
	
}
