package cell;

import java.util.ArrayList;
import java.util.HashMap;

import middleware.CurrentParameters;

public class FireNewCell extends GeneralCell{

	public FireNewCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp,mh, myState);
		super.cellSpecificBehavior.put("fire", new Fire());
		super.cellSpecificBehavior.put("tree", new Tree());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	
	protected double getProbCatch() {
		return Double.parseDouble(getCurrentGameParameters().get("probcatch"));
	}

	
	@Override
	public GeneralCell clone() {
		return new FireNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}
}
