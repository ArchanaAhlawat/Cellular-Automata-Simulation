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
	

	
	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Randomly assigns the starting spawn count so that new fish and sharks are created
	 * at roughly the same rate every turn and not all at once.
	 * 
	 ***/
	private void randomlySetSpawnCount() {
		int spawnthresh = getSpawnThreshold();
		Random rand = new Random();
		int startspawncount = rand.nextInt(spawnthresh);
		setSpawnCount(startspawncount);
	}

	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Returns the current cell's spawncount - i.e., how long since birth or last spawn
	 * 
	 ***/
	public int getSpawnCount() {
		return Integer.parseInt(getCurrentCellParameters().get("spawncount"));
	}

	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Increments the spawncount by 1
	 * 
	 ***/
	protected void updateSpawnCount() {
		setSpawnCount(getSpawnCount() + 1);
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * 
	 * Returns the current cell's spawncount to 0
	 * 
	 ***/
	protected void revertSpawnCount() {
		setSpawnCount(0);
	}

	
	/***
	 * @author Sam Slack (sls97)
	 * @param int num - new spawncount value
	 * 
	 * Sets the current cell's spawncount to some number
	 * 
	 ***/
	protected void setSpawnCount(int num) {
		getCurrentCellParameters().put("spawncount", Integer.toString(num));
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * @return int - current cell's deathcount value
	 * Gets the current cell's deathcount value -- this value's use depends on if cell is Shark or Fish.
	 * 
	 ***/
	public int getDeathCount() {
		return Integer.parseInt(getCurrentCellParameters().get("deathcount"));
	}
	
	
	/***
	 * @author Sam Slack (sls97)
	 * Increments the current cell's deathcount value by 1
	 * 
	 ***/
	protected void updateDeathCount() {
		setDeathCount(getDeathCount() + 1);
	}

	/***
	 * @author Sam Slack (sls97)
	 * @param int num - New deathcount value
	 * Sets the current cell's deathcount to some number
	 * 
	 ***/
	protected void setDeathCount(int num) {
		getCurrentCellParameters().put("deathcount", Integer.toString(num));
	}
	/***
	 * @author Sam Slack (sls97)
	 * @return int - number of iterations between current cell's reproductions
	 * 
	 ***/
	protected int getSpawnThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("reproductionTime"));
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * @return int - deaththreshold - for Shark, this is measured as iterations since last meal; for Fish, this is measured as number of spawns 
	 * 
	 ***/
	protected int getDeathThreshold() {
		return Integer.parseInt(getCurrentGameParameters().get("deathThresh"));
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * @return boolean - determines if cell's spawncount equals or exceeds its spawnthreshold
	 * 
	 ***/

	protected boolean readyToSpawn() {
		return getSpawnCount() >= getSpawnThreshold();
	}
	
	/***
	 * @author Sam Slack (sls97)
	 * @return boolean - determines if cell's deathcount equals or exceeds its deaththreshold
	 * 
	 ***/
	
	protected boolean readyToDie() {
		return getDeathCount() >= getDeathThreshold();
	}

	@Override
	public GeneralCell clone() {
		return new PredPreyNewCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
