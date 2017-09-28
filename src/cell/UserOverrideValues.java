package cell;

import java.util.Map;

public class UserOverrideValues {

Map<String, Map<String, String>> userOverrideValues;
boolean newUpdate = false;
private static DefaultValuesHelper defaultValuesHelper;
	
	UserOverrideValues(DefaultValuesHelper dvh) {
		defaultValuesHelper = dvh;
		}
	
	public Map<String, Map<String, String>> getOverridenValues() {
		return userOverrideValues;
	}
	
	public void resetUpdatedValues() {
		newUpdate = false;
		userOverrideValues.clear();
	}
	

	/*
	 * @param cell_state type of cell (i.e., shark, fish, tree, fire, etc.)
	 * @param varname specific 'special value' - reproductioncount, underpopulationthreshhold, etc.
	 */
	
	public void resetToDefault(String cell_state, String varname) {
		addNewOverrideValue(cell_state,varname, defaultValuesHelper.returnDefaultForState(cell_state).get(varname));
	}
	
	public boolean hasUserUpdate() {
		return newUpdate;
	}
	
	/*
	 * @param cell_state type of cell (i.e., shark, fish, tree, fire, etc.)
	 * @param varname specific 'special value' - reproductioncount, underpopulationthreshhold, etc. 
	 * @parm val new value 
	 * 
	 */
	
	public void addNewOverrideValue(String cell_state, String varname, String val) {
		userOverrideValues.get(cell_state).put(varname, val);
		newUpdate = true;	
	}
	
}
