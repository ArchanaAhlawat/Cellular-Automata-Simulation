package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOfLifeNewCell extends GeneralCell{

	public GameOfLifeNewCell(HashMap<String, String> cellParameters,HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
		super.cellSpecificBehavior.put("alive", new AliveGOL());
		super.cellSpecificBehavior.put("dead", new Empty());
	}

	@Override
	public void move() {
		//Nothing
	}

//	@Override
//	public void updateEverytime() {
//		
//	}

	protected int getUnderpopulationThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("underpopulationthreshold"));
	}
	
	protected int getOverpopulationThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("overpopulationthreshold"));
	}
	
	protected int getReproductionNumber() {
		return Integer.parseInt(getCurrentParametersValues().get("reproductionnumber"));
	}
	
//	@Override
//	public void updateBasedOnNextState() {
//		
//	}

}
