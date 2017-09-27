package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class SegregationNewCell extends GeneralCell{

	public SegregationNewCell(HashMap<String, String> cellParameters,
			HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
		super.cellSpecificBehavior.put("agent1", new Agent());
		super.cellSpecificBehavior.put("agent2", new Agent());
		super.cellSpecificBehavior.put("empty", new Empty());
	}

	@Override
	public void move() {
		
	}
	
	protected double getSatisfactionThreshold() {
		return Double.parseDouble(getCurrentParametersValues().get("satisfactionthreshold"));
	}

	
}
