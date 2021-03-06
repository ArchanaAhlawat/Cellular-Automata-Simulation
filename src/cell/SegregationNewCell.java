package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import middleware.CurrentParameters;

public class SegregationNewCell extends GeneralCell {

	public SegregationNewCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp, mh, myState);
		super.cellSpecificBehavior.put("agent1", new Agent1());
		super.cellSpecificBehavior.put("agent2", new Agent2());
		super.cellSpecificBehavior.put("empty", new Empty());
		possibleStates = new ArrayList<String>(Arrays.asList("agent1", "agent2", "empty"));
	}

	protected double getSatisfactionThreshold() {
		String val = getCurrentGameParameters().get("satisfaction_threshhold");
		return ((double) Integer.parseInt(val)) / 100;
	}

	@Override
	public GeneralCell clone() {
		return new SegregationNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
