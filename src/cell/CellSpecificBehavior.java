package cell;

import java.util.Map;

public interface CellSpecificBehavior {

	void cellSpecificEveryTime(GeneralCell cell);
	void cellSpecificBasedOnNextState(GeneralCell cell);
	Map<String, String> getDefaultState();
}
