package cell;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class FireNewCell extends GeneralCell{

	public FireNewCell(HashMap<String, String> cellParameters,HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
		super.cellSpecificBehavior.put("fire", new Fire());
		super.cellSpecificBehavior.put("tree", new Tree());
		super.cellSpecificBehavior.put("empty", new Empty());
	}

	@Override
	public void move() {

	}

	@Override
	public void updateEverytime() {
		cellSpecificBehavior.get(getState()).cellSpecificEveryTime(this);
	}

	abstract void cellSpecificEveryTime();

	@Override
	public void updateBasedOnNextState() {
		cellSpecificBehavior.get(getState()).cellSpecificBasedOnNextState(this);	
	}
	
	protected double getProbCatch() {
		return Double.parseDouble(getCurrentParametersValues().get("probcatch"));
	}

}
