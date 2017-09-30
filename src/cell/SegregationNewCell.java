package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class SegregationNewCell extends GeneralCell{

	public SegregationNewCell(CurrentParameters cp,
			MoveHelper mh, DefaultValues dvh, UserOverrideValues uov, String myState) {
		super(cp, mh, dvh, uov);
		super.cellSpecificBehavior.put("agent1", new Agent1());
		super.cellSpecificBehavior.put("agent2", new Agent2());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	
	protected double getSatisfactionThreshold() {
		return Double.parseDouble(getCurrentGameParameters().get("satisfactionthreshold"));
	}

	
}
