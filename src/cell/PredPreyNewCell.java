package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class PredPreyNewCell extends GeneralCell {

	PredPreyNewCell(HashMap<String, String> cellParameters, MoveHelper mh, DefaultValuesHelper dvh, UserOverrideValues uov) {
		super(cellParameters, mh, dvh, uov);
		super.cellSpecificBehavior.put("fish", new Fish());
		super.cellSpecificBehavior.put("shark", new Shark());
		super.cellSpecificBehavior.put("empty", new Empty());
	}

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
