package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOfLifeNewCell extends GeneralCell{

	public GameOfLifeNewCell(CurrentParameters cp, MoveHelper mh, DefaultValues dvh, UserOverrideValues uov, String myState) {
		super(cp, mh, dvh, uov);
		super.cellSpecificBehavior.put("alive", new AliveGOL());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	protected int getUnderpopulationThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("underpopulationthreshold"));
	}
	
	protected int getOverpopulationThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("overpopulationthreshold"));
	}
	
	protected int getReproductionNumber() {
		return Integer.parseInt(getCurrentGameParameters().get("reproductionnumber"));
	}

}
