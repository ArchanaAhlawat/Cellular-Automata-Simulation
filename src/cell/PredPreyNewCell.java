package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import middleware.CurrentParameters;

public class PredPreyNewCell extends GeneralCell {
	


	public PredPreyNewCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp, mh, myState);
		super.cellSpecificBehavior.put("fish", new Fish());
		super.cellSpecificBehavior.put("shark", new Shark());
		super.cellSpecificBehavior.put("empty", new Empty());
		possibleStates = new ArrayList<String>(Arrays.asList("fish", "shark", "empty"));
		randomlySetSpawnCount();
		getCurrentCellParameters().put("deathcount", "0");
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
	
	public int getDeathCount() {
		return Integer.parseInt(getCurrentCellParameters().get("deathcount"));
	}

	protected void updateDeathCount() {
		setDeathCount(getDeathCount() + 1);
	}

	protected void setDeathCount(int num) {
		getCurrentCellParameters().put("deathcount", Integer.toString(num));
	}

	protected int getSpawnThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("reproductionTime"));
	}
	
	protected int getDeathThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("deathThresh"));
	}

	protected boolean readyToSpawn() {
		return getSpawnCount() >= getSpawnThreshold();
	}
	
	protected boolean readyToDie() {
		return getDeathCount() >= getDeathThreshold();
	}

	@Override
	public GeneralCell clone() {
		return new PredPreyNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
