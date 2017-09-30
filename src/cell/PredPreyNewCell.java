package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import middleware.CurrentParameters;

public class PredPreyNewCell extends GeneralCell {

	PredPreyNewCell(CurrentParameters cp, MoveHelper mh, DefaultValues dvh, String myState) {
		super(cp, mh, dvh, myState);
		super.cellSpecificBehavior.put("fish", new Fish());
		super.cellSpecificBehavior.put("shark", new Shark());
		super.cellSpecificBehavior.put("empty", new Empty());
		randomlySetSpawnCount();
	}
	
	/*
	 * @author Sam Slack (sls97)
	 * 
	 * Randomly assigns the starting spawn count so that new fish and sharks are created
	 * at roughly the same rate every turn and not all at once.
	 * 
	 */
	private void randomlySetSpawnCount() {
		int spawnthresh = getSpawnThreshold();
		Random rand = new Random();
		int startspawncount = rand.nextInt(spawnthresh);
		setSpawnCount(startspawncount);
	}

	public int getSpawnCount() {
		return Integer.parseInt(getCurrentCellParameters().get("spawncount"));
	}

	protected void updateSpawnCount() {
		setSpawnCount(getSpawnCount() + 1);
	}

	protected void setSpawnCount(int num) {
		getCurrentCellParameters().put("spawncount", Integer.toString(num));
	}

	protected int getSpawnThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("spawnthreshold"));
	}

	protected boolean readyToSpawn() {
		return getSpawnCount() >= getSpawnThreshold();
	}

}
