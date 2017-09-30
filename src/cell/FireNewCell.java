package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class FireNewCell extends GeneralCell{

	public FireNewCell(CurrentParameters cp, MoveHelper mh, DefaultValues dvh, UserOverrideValues uov, String myState) {
		super(cp,mh, dvh, uov);
		super.cellSpecificBehavior.put("fire", new Fire());
		super.cellSpecificBehavior.put("tree", new Tree());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	
	protected double getProbCatch() {
		return Double.parseDouble(getCurrentGameParameters().get("probcatch"));
	}

}
