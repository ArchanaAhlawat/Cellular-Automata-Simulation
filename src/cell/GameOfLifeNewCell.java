package cell;

import java.util.ArrayList;
import java.util.HashMap;

import middleware.CurrentParameters;

public class GameOfLifeNewCell extends GeneralCell{

	public GameOfLifeNewCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp, mh, myState);
		super.cellSpecificBehavior.put("alive", new AliveGOL());
		super.cellSpecificBehavior.put("empty", new Empty());
	}
	protected int getUnderpopulationThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("underpopulation_threshold"));
	}
	
	protected int getOverpopulationThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("overpopulation_threshold"));
	}
	
	protected int getReproductionNumber() {
		return Integer.parseInt(getCurrentGameParameters().get("reproduction_number"));
	}
	
	@Override
	public GeneralCell clone() {
		return new GameOfLifeNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
