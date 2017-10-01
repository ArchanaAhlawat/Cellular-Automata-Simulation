package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import middleware.CurrentParameters;

public abstract class GeneralCell {

	// Use getters for instance variables in subclass constructors

	Map<String, HashMap<String, String>> defaults = new HashMap<String, HashMap<String, String>>();
	Map<String, String> currentCellParameters = new HashMap<String, String>();
	Map<String, String> nextCellParameters = new HashMap<String, String>();
	Map<String, String> overrideCellParameters = new HashMap<String, String>();
	ArrayList<GeneralCell> myNeighbors;
	HashMap<String, CellSpecificBehavior> cellSpecificBehavior = new HashMap<String, CellSpecificBehavior>();
	HashMap<String, cellMovement> cellSpecificMovement = new HashMap<String, cellMovement>();
	MoveHelper moveHelper;
	CurrentParameters currentGameParameters;
	protected ArrayList<String> possibleStates = new ArrayList<String>();


	// public GeneralCell(HashMap<String, String> cellParameters,
	// HashMap<String, HashMap<String, String>> allDefaultParameters, MoveHelper
	// mh, DefaultValuesHelper dvh) {
	
	public GeneralCell(CurrentParameters cp, MoveHelper mh, String myState) {
//		this.setCurrentParametersValues(cellParameters);
		// this.setDefaults(allDefaultParameters);
		currentCellParameters.put("state", myState);
		currentGameParameters = cp;
		this.moveHelper = mh;
//		this.defaultValuesHelper = dvh;
//		this.userOverrideValue = uov;
	}

	public String getState() {
//		return getCurrentParametersValues().get("state");
		return getCurrentCellParameters().get("state");
	}
	
	protected Map<String, String> getCurrentCellParameters() {
		return currentCellParameters;
	}
	
	protected void setCurrentCellParameters(Map<String, String> newParameterValues) {
		this.currentCellParameters = newParameterValues;
		
	}

	protected Map<String, String> getCurrentGameParameters() {
//		return currentParameterValues;
		return currentGameParameters.getCurrentParameterMap(getState());
	}

//	private void setCurrentParametersValues(Map<String, String> currentParametersValues) {
//		this.currentParameterValues = currentParametersValues;
//	}

	public Map<String, String> getNextCellParameters() {
		return nextCellParameters;
	}

	protected void setNextCellParameters(Map<String, String> nextParameterValues) {
		this.nextCellParameters = nextParameterValues;
	}

	protected ArrayList<GeneralCell> getNeighbors() {
		return myNeighbors;
	}

	protected void setNeighbors(ArrayList<GeneralCell> neighbors) {
		this.myNeighbors = neighbors;
	}

	/***
	 * @author Sam Slack (sls97)
	 * @param neighbors - ArrayList<GeneralCell> containing all neighbors of current cell 
	 * 
	 * Cell uses internal states and rules to determine its next state.
	 */
	public void computeState(ArrayList<GeneralCell> neighbors) {
		setNeighbors(neighbors);
		updateEverytime();
		updateBasedOnNextState();
	}

	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Current cell will use this method and a helper class to detemrine where it should move to on any given iteration
	 */
	public void move() {
		this.moveHelper.moveCell(this);
		changeToDefault("empty");
	}

	public boolean calcAndReplace(String state, Map<String, String> newParamValues) {
		ArrayList<GeneralCell> stateNeighbors = calcUnmodifiedNeighborsOfState(state);
		GeneralCell targetCell = chooseRandomCellFromList(stateNeighbors);
		if (targetCell != null) {
			targetCell.setNextCellParameters(newParamValues);
			return true;
		}
		return false;
	}

	public ArrayList<GeneralCell> calcUnmodifiedNeighborsOfState(String state) {
		ArrayList<GeneralCell> currNeighbors = calcCurrNeighborsOfState(state);
		ArrayList<GeneralCell> ret = new ArrayList<GeneralCell>();
		for (GeneralCell cell : currNeighbors) {
			if (cell.getNextCellParameters().size() == 0) {
				ret.add(cell);
			}
		}
		return ret;
	}

	public ArrayList<GeneralCell> calcCurrNeighborsOfState(String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell cell : getNeighbors()) {
			if (cell.getCurrentCellParameters().get("state").equals(state)) {
				stateNeighbors.add(cell);
			}
		}

		return stateNeighbors;
	}

	public ArrayList<GeneralCell> calcNextNeighborsOfState(String state) {
		ArrayList<GeneralCell> stateNeighbors = new ArrayList<>();
		for (GeneralCell cell : getNeighbors()) {
			if (cell.getNextCellParameters().get("state").equals(state)) {
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
//		this.setNextCellParameters(this.defaultValuesHelper.getDefaultMap(state));
		this.setNextCellParameters(cellSpecificBehavior.get(state).getDefaultState());
	}
	
	protected void setOverriddenCellParameters(Map<String, String> nextOverriddenValues) {
		this.overrideCellParameters = nextOverriddenValues;
	}
	
	public void changeOverriddenState(String state) {
//		this.setNextCellParameters(this.defaultValuesHelper.getDefaultMap(state));
		this.setOverriddenCellParameters(cellSpecificBehavior.get(state).getDefaultState());
		if (this.getNextCellParameters().size()==0){
			this.setNextCellParameters(this.getOverrideCellParameters());
			this.setOverriddenCellParameters(new HashMap<String, String>());
		}
	}

	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Current cell will apply these rules every iteration of the simulation - they are independent of the cell's future state
	 */
	public void updateEverytime() {
//		updateParamsBasedOnUserInput();
		cellSpecificBehavior.get(getState()).cellSpecificEveryTime(this);
	}
/***
 * @author Sam Slack (sls97)
 * 
 * Current cell will only apply these rules if some condition about their next state is true
 */
	public void updateBasedOnNextState() {
		cellSpecificBehavior.get(getState()).cellSpecificBasedOnNextState(this);
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * @return List<String> - contains all possible states that the current cell can become - i.e., all of the simulation's states
	 * 							except the cell's current state
	 * 
	 * Used primarily to allow the FrontEnd to give user control over the states of individual cells as the game is progressing in real time
	 */
	public List<String> getPossibleNewStates() {
		List<String> ret = new ArrayList<String>();
		for (String s: possibleStates){
			if (!s.equals(this.getState())){
				ret.add(s);
			}
		}
		return ret;
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Allows for changes to cell state from user to be documented without interrupting current iteration
	 */
	public void setOverridenState(String newstate) {
		this.changeOverriddenState(newstate);
	}
	
	/*** 
	 * @author Sam Slack (sls97)
	 * @return Map<String,String> - returns map of current parameters that the user has overridden
	 */
	public Map<String,String >getOverrideCellParameters() {
		return this.overrideCellParameters;
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Allows CellManager to quickly create copies of cell objects by cloning new objects. 
	 */
	public abstract GeneralCell clone();

	/***
	 * @author Sam Slack (sls97)
	 * CellManager calls this method to change a cell from its current state into its next state. This method also controls the logic to ensure that user overridden parameters will be applied in the next iteration if they conflicted with the current iteration.
	 */
	public void becomeNextState() {
		if (getNextCellParameters().size() != 0) {
			this.setCurrentCellParameters(this.getNextCellParameters());
		}
		this.setNextCellParameters(this.getOverrideCellParameters());
		this.setOverriddenCellParameters(new HashMap<String, String>());

		
	}
}
