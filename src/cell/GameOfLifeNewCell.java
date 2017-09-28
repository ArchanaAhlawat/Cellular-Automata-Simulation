package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOfLifeNewCell extends GeneralCell{

	public GameOfLifeNewCell(HashMap<String, String> cellParameters, MoveHelper mh, DefaultValuesHelper dvh) {
		super(cellParameters, mh, dvh);
		super.cellSpecificBehavior.put("alive", new AliveGOL());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	protected int getUnderpopulationThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("underpopulationthreshold"));
	}
	
	protected int getOverpopulationThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("overpopulationthreshold"));
	}
	
	protected int getReproductionNumber() {
		return Integer.parseInt(getCurrentParametersValues().get("reproductionnumber"));
	}

}
