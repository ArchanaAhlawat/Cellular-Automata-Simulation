package cell;

import middleware.CurrentParameters;

public class FireNewCell extends GeneralCell{

	public FireNewCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp,mh, myState);
		super.cellSpecificBehavior.put("fire", new Fire());
		super.cellSpecificBehavior.put("tree", new Tree());
		super.cellSpecificBehavior.put("empty", new Empty());
		possibleStates = new ArrayList<String>(Arrays.asList("fire", "tree", "empty"));
	}
	
	protected double getProbCatch() {
		return (double)Integer.parseInt(getCurrentGameParameters().get("probCatch")) / 100;
	}

	
	@Override
	public GeneralCell clone() {
		return new FireNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}
}
