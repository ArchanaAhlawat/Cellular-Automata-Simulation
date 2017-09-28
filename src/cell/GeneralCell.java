package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class GeneralCell {

	// Use getters for instance variables in subclass constructors

	Map<String, HashMap<String, String>> defaults = new HashMap<String, HashMap<String, String>>();
	Map<String, String> currentParameterValues = new HashMap<String, String>();
	Map<String, String> nextParameterValues = new HashMap<String, String>();
	ArrayList<GeneralCell> myNeighbors;
	HashMap<String, CellSpecificBehavior> cellSpecificBehavior = new HashMap<String, CellSpecificBehavior>();
	HashMap<String, cellMovement> cellSpecificMovement = new HashMap<String, cellMovement>();
	MoveHelper moveHelper;
	DefaultValuesHelper defaultValuesHelper;
	UserOverrideValues userOverrideValue;

	// public GeneralCell(HashMap<String, String> cellParameters,
	// HashMap<String, HashMap<String, String>> allDefaultParameters, MoveHelper
	// mh, DefaultValuesHelper dvh) {
	public GeneralCell(HashMap<String, String> cellParameters, MoveHelper mh, DefaultValuesHelper dvh, UserOverrideValues uov) {
		this.setCurrentParametersValues(cellParameters);
		// this.setDefaults(allDefaultParameters);
		this.moveHelper = mh;
		this.defaultValuesHelper = dvh;
		this.userOverrideValue = uov;
	}

	protected String getState() {
		return getCurrentParametersValues().get("state");
	}

	protected Map<String, String> getCurrentParametersValues() {
		return currentParameterValues;
	}

	private void setCurrentParametersValues(Map<String, String> currentParametersValues) {
		this.currentParameterValues = currentParametersValues;
	}

	public Map<String, String> getNextParameterValues() {
		return nextParameterValues;
	}

	protected void setNextParameterValues(Map<String, String> nextParameterValues) {
		this.nextParameterValues = nextParameterValues;
	}

	protected Map<String, Map<String, String>> getDefaults() {
		return this.defaultValuesHelper.returnAllDefaults();
		// return this.defaults;
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

	public void move() {
		this.moveHelper.moveCell(this);
		changeToDefault("empty");
	}

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
		for (GeneralCell cell : currNeighbors) {
			if (cell.getNextParameterValues().size() == 0) {
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
		this.setNextParameterValues(this.defaultValuesHelper.returnDefaultForState(state));
	}

	public void updateEverytime() {
		updateParamsBasedOnUserInput();
		cellSpecificBehavior.get(getState()).cellSpecificEveryTime(this);
	}

	public void updateBasedOnNextState() {
		cellSpecificBehavior.get(getState()).cellSpecificBasedOnNextState(this);
	}

	private void updateParamsBasedOnUserInput() {
		if (userOverrideValue.hasUserUpdate()) {
			Map<String, Map<String, String>> UOV_Map = userOverrideValue.getOverridenValues();
			if (!UOV_Map.get(getState()).isEmpty()) {
				for (String state_variable : UOV_Map.get(getState()).keySet()) {
					String newValue = UOV_Map.get(getState()).get(state_variable);
					this.getCurrentParametersValues().put(state_variable, newValue);
				}

			}
		}

	}

	public void becomeNextState() {
		if (getNextParameterValues().size() != 0) {
			this.setCurrentParametersValues(this.getNextParameterValues());
			this.setNextParameterValues(new HashMap<String, String>());
		}
	}
}
