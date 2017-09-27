package cell;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PredPreyNewCell extends GeneralCell {

	PredPreyNewCell(HashMap<String, String> cellParameters,HashMap<String, HashMap<String, String>> allDefaultParameters) {
		super(cellParameters, allDefaultParameters);
		super.cellSpecificBehavior.put("fish", new Fish());
		super.cellSpecificBehavior.put("shark", new Shark());
		super.cellSpecificBehavior.put("empty", new Empty());
	}

	@Override
	public void move() {
		calcAndReplace("empty", getCurrentParametersValues());
		changeToDefault("empty");
	}

//	@Override
//	public void updateEverytime() {
//		updateSpawnCount();
//		if (readyToSpawn()) {
//			calcAndReplace("empty", getDefaults().get(getState()));
//		}
//		super.updateEverytime();
//	}
	
//	@Override
//	public void updateBasedOnNextState() {
//		if (getNextParameterValues().size() <= 0) {
//			cellSpecificBehavior.get(getState()).cellSpecificBasedOnNextState(this);
////			cellSpecificBasedOnNextState(neighbors);
//			move();
//		}
//
//	}

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
