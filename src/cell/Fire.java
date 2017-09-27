package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class Fire implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		cell.changeToDefault("empty");
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		
	}


}
