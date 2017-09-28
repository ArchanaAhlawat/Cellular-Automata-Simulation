package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class SegregationNewCell extends GeneralCell{

	public SegregationNewCell(HashMap<String, String> cellParameters,
			MoveHelper mh, DefaultValuesHelper dvh) {
		super(cellParameters, mh, dvh);
		super.cellSpecificBehavior.put("agent1", new Agent());
		super.cellSpecificBehavior.put("agent2", new Agent());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	
	protected double getSatisfactionThreshold() {
		return Double.parseDouble(getCurrentParametersValues().get("satisfactionthreshold"));
	}

	
}
