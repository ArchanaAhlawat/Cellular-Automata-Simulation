package cell;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PredPreyNewCell extends GeneralCell {

	PredPreyNewCell(HashMap<String, String> cellParameters,
			HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
	}

	@Override
	public void move(ArrayList<GeneralCell> neighbors) {
		calcAndReplace("empty", getCurrentParametersValues(), neighbors);
		changeToDefault("empty");
	}

	@Override
	public void updateEverytime(ArrayList<GeneralCell> neighbors) {
		updateSpawnCount();
		if (readyToSpawn()) {
			calcAndReplace("empty", getDefaults().get(getState()), neighbors);
		}
		cellSpecificEveryTime(neighbors);
	}

	protected abstract void cellSpecificEveryTime(ArrayList<GeneralCell> neighbors);

	@Override
	public void updateBasedOnNextState(ArrayList<GeneralCell> neighbors) {
		if (getNextParameterValues().size() > 0) {
			cellSpecificBasedOnNextState(neighbors);
			move(neighbors);
		}

	}

	protected abstract void cellSpecificBasedOnNextState(ArrayList<GeneralCell> neighbors);

	public int getSpawnCount() {
		return Integer.parseInt(getCurrentParametersValues().get("spawncount"));
	}

	protected void updateSpawnCount() {
		setSpawnCount(getSpawnCount() + 1);
	}

	protected void setSpawnCount(int num) {
		getCurrentParametersValues().put("spawncount", Integer.toString(num));
	}

	protected int getSpawnThreshold() {
		return Integer.parseInt(getCurrentParametersValues().get("spawnthreshold"));
	}

	protected boolean readyToSpawn() {
		return getSpawnCount() >= getSpawnThreshold();
	}

}
