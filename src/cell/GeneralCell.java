package cell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import middleware.Cell;

public abstract class GeneralCell {

	Map<String, HashMap<String, String>> defaults = new HashMap<String, HashMap<String, String>>();
	Map<String, String> currentParametersValues = new HashMap<String, String>();
	Map<String, String> nextParameterValues = new HashMap<String, String>();

	public GeneralCell(HashMap<String, String> cellParameters,
			HashMap<String, HashMap<String, String>> allDefaultParameters) {
		this.setCurrentParametersValues(cellParameters);
		this.setDefaults(allDefaultParameters);
	}

	protected String getState() {
		return getCurrentParametersValues().get("state");
	}
	
//	protected void setNextState(String s) {
//		getNextParameterValues().put("state", s);
//	}

	protected Map<String, String> getCurrentParametersValues() {
		return currentParametersValues;
	}

	private void setCurrentParametersValues(Map<String, String> currentParametersValues) {
		this.currentParametersValues = currentParametersValues;
	}

	public Map<String, String> getNextParameterValues() {
		return nextParameterValues;
	}

	protected void setNextParameterValues(Map<String, String> nextParameterValues) {
		this.nextParameterValues = nextParameterValues;
	}

	protected Map<String, HashMap<String, String>> getDefaults() {
		return this.defaults;
	}

	private void setDefaults(HashMap<String, HashMap<String, String>> defaultParams) {
		this.defaults = defaultParams;
	}

	public void computeState(ArrayList<GeneralCell> neighbors) {
		updateEverytime(neighbors);
		updateBasedOnNextState(neighbors);
	}

	public abstract void move(ArrayList<GeneralCell> neighbors);

	public void calcAndReplace(String state, Map<String, String> newParamValues, ArrayList<GeneralCell> neighbors) {
		ArrayList<GeneralCell> stateNeighbors  = calcNeighborsOfState(state, neighbors, "next");
		GeneralCell targetCell = chooseRandomCellFromList(stateNeighbors);
		if (targetCell != null) {
			targetCell.setNextParameterValues(newParamValues);
		}
	}

	public ArrayList<GeneralCell> calcNeighborsOfState(String state, ArrayList<GeneralCell> neighbors,
			String currOrNext) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell cell : neighbors) {
			if (currOrNext.equals("next")) {
				if (cell.getNextParameterValues().size() == 0) {
					if (cell.getCurrentParametersValues().get("state").equals(state)) {
						stateNeighbors.add(cell);
					}
				}
			} else {
				if (cell.getCurrentParametersValues().get("state").equals(state)) {
					stateNeighbors.add(cell);
				}
			}
		}

		return stateNeighbors;
	}

	protected GeneralCell chooseRandomCellFromList(ArrayList<GeneralCell> cellList) {
		int num_cells = cellList.size();
		if (num_cells > 0) {
			Random rand = new Random();
			int random_index = rand.nextInt(num_cells);
			GeneralCell targetRandomCell = cellList.get(random_index);
			return targetRandomCell;
		}
		return null;
	}

	public void changeToDefault(String state) {
		this.setNextParameterValues(this.getDefaults().get(state));
	}

	public abstract void updateEverytime(ArrayList<GeneralCell> neighbors);

	public abstract void updateBasedOnNextState(ArrayList<GeneralCell> neighbors);

	public void becomeNextState() {
		if (getNextParameterValues().size() != 0) {
			this.setCurrentParametersValues(this.getNextParameterValues());
			this.setNextParameterValues(new HashMap<String, String>());
		}
	}
}
