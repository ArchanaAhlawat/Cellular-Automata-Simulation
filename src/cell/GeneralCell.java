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
	ArrayList<GeneralCell> myNeighbors;
	HashMap<String, CellSpecificBehavior> cellSpecificBehavior = new HashMap<String, CellSpecificBehavior>();

	public GeneralCell(HashMap<String, String> cellParameters,
			HashMap<String, HashMap<String, String>> allDefaultParameters) {
		this.setCurrentParametersValues(cellParameters);
		this.setDefaults(allDefaultParameters);
	}

	protected String getState() {
		return getCurrentParametersValues().get("state");
	}

	// protected void setNextState(String s) {
	// getNextParameterValues().put("state", s);
	// }

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
	
	protected ArrayList<GeneralCell> getNeighbors() {
		return myNeighbors;
	}
	
	protected void setNeighbors(ArrayList<GeneralCell> neighbors) {
		this.myNeighbors = neighbors;
	}

	public void computeState(ArrayList<GeneralCell> neighbors) {
		setNeighbors(neighbors);
		updateEverytime();
		updateBasedOnNextState();
	}

	public abstract void move();

	public void calcAndReplace(String state, Map<String, String> newParamValues) {
		ArrayList<GeneralCell> stateNeighbors = calcUnmodifiedNeighborsOfState(state);
		GeneralCell targetCell = chooseRandomCellFromList(stateNeighbors);
		if (targetCell != null) {
			targetCell.setNextParameterValues(newParamValues);
		}
	}
	
	public ArrayList<GeneralCell> calcUnmodifiedNeighborsOfState(String state) {
		ArrayList<GeneralCell> currNeighbors = calcCurrNeighborsOfState(state);
		ArrayList<GeneralCell> ret = new ArrayList<GeneralCell>();
		for (GeneralCell cell: currNeighbors) {
			if (cell.getNextParameterValues().size()==0) {
				ret.add(cell);
			}
		}
		return ret;
	}

	public ArrayList<GeneralCell> calcCurrNeighborsOfState(String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell cell : getNeighbors()) {
			if (cell.getCurrentParametersValues().get("state").equals(state)) {
				stateNeighbors.add(cell);
			}
		}

		return stateNeighbors;
	}
	
	public ArrayList<GeneralCell> calcNextNeighborsOfState(String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell cell : getNeighbors()) {
			if (cell.getNextParameterValues().get("state").equals(state)) {
				stateNeighbors.add(cell);
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

	public void updateEverytime() {
		cellSpecificBehavior.get(getState()).cellSpecificEveryTime(this);	
	}

	public void updateBasedOnNextState() {
		cellSpecificBehavior.get(getState()).cellSpecificBasedOnNextState(this);
	}

	public void becomeNextState() {
		if (getNextParameterValues().size() != 0) {
			this.setCurrentParametersValues(this.getNextParameterValues());
			this.setNextParameterValues(new HashMap<String, String>());
		}
	}
}
