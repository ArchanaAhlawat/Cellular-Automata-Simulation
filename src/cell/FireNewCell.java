package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class FireNewCell extends GeneralCell{

	public FireNewCell(HashMap<String, String> cellParameters, MoveHelper mh, DefaultValuesHelper dvh, UserOverrideValues uov) {
		super(cellParameters,mh, dvh, uov);
		super.cellSpecificBehavior.put("fire", new Fire());
		super.cellSpecificBehavior.put("tree", new Tree());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	
	protected double getProbCatch() {
		return Double.parseDouble(getCurrentParametersValues().get("probcatch"));
	}

}
