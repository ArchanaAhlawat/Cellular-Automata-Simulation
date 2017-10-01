package cell;

import java.util.ArrayList;
import java.util.Arrays;

import middleware.CurrentParameters;

public class RPSCell extends GeneralCell{

	public RPSCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp, mh, myState);
		super.cellSpecificBehavior.put("red", new Red());
		super.cellSpecificBehavior.put("blue", new Blue());
		super.cellSpecificBehavior.put("green", new Green());
		super.cellSpecificBehavior.put("white", new White());
		this.setNextCellParameters(this.getCurrentCellParameters());
		possibleStates = new ArrayList<String>(Arrays.asList("red", "blue", "green", "white"));
	}

	
	public int getCurrentHitCount() {
		return Integer.parseInt(getCurrentCellParameters().get("hitcount"));
	}
	
	public int getNextHitCount() {
		return Integer.parseInt(getNextCellParameters().get("hitcount"));
	}

	protected void incrementNextHitCount() {
		setNextHitCount(getNextHitCount() -1);
	}
	
	protected void decrementNextHitCount() {
		setNextHitCount(getNextHitCount() +1);
	}

	protected void setNextHitCount(int num) {
		getNextCellParameters().put("hitcount", Integer.toString(num));
	}
	
	
	@Override
	public GeneralCell clone() {
		return new RPSCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
