package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class Shark extends PredPreyNewCell{
	
	Shark(HashMap<String, String> cellParameters, HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
	}

	@Override
	protected void cellSpecificEveryTime(ArrayList<GeneralCell> neighbors) {
		eatFish(neighbors);
	}
	
	private void eatFish(ArrayList<GeneralCell> neighbors){
		calcAndReplace("fish", getDefaults().get("empty"), neighbors);
	}

	@Override
	protected void cellSpecificBasedOnNextState(ArrayList<GeneralCell> neighbors) {
		
	}


	
}
