package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import middleware.CurrentParameters;

public class GameOfLifeNewCell extends GeneralCell{

	public GameOfLifeNewCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp, mh, myState);
		super.cellSpecificBehavior.put("alive", new AliveGOL());
		super.cellSpecificBehavior.put("empty", new DeadGOL());
		possibleStates = new ArrayList<String>(Arrays.asList("alive","empty"));
	}
	protected int getUnderpopulationThreshold() {
		int ret = Integer.parseInt(getCurrentGameParameters().get("underpopulation_threshhold"));
		System.out.println("Underpop thresh: " + ret);
		return ret;

	}
	
	protected int getOverpopulationThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("overpopulation_threshhold"));
	}
	
	protected int getReproductionNumber() {
		return Integer.parseInt(getCurrentGameParameters().get("reproduction_number"));
	}
	
	@Override
	public GeneralCell clone() {
		return new GameOfLifeNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
