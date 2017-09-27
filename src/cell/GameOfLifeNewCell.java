package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOfLifeNewCell extends GeneralCell{

	public GameOfLifeNewCell(HashMap<String, String> cellParameters,HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
	}

	@Override
	public void move(ArrayList<GeneralCell> neighbors) {
	}

	@Override
	public void updateEverytime(ArrayList<GeneralCell> neighbors) {
		int neighborsAliveCount = calcNeighborsOfState("alive",neighbors,"curr").size();
		if (neighborsAliveCount<this.getUnderpopulationThreshold())  {
			setNextParameterValues(getDefaults().get("dead"));
		} else if (neighborsAliveCount>this.getOverpopulationThreshold()) {
			setNextParameterValues(getDefaults().get("dead"));
		} else if (neighborsAliveCount==this.getReproductionNumber() && getState().equals("dead")) {
			setNextParameterValues(getDefaults().get("alive"));
		} 
	}

	private int getUnderpopulationThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("underpopulationthreshold"));
	}
	
	private int getOverpopulationThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("overpopulationthreshold"));
	}
	
	private int getReproductionNumber() {
		return Integer.parseInt(getCurrentParametersValues().get("reproductionnumber"));
	}
	
	@Override
	public void updateBasedOnNextState(ArrayList<GeneralCell> neighbors) {
		
	}

}
