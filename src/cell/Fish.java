package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class Fish extends PredPreyNewCell{

	Fish(HashMap<String, String> cellParameters, HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
	}

	@Override
	protected void cellSpecificEveryTime(ArrayList<GeneralCell> neighbors) {
	}

	@Override
	protected void cellSpecificBasedOnNextState(ArrayList<GeneralCell> neighbors) {
	}

}
