package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import middleware.CurrentParameters;

public class RPSCell extends GeneralCell{
	
	HashMap<ArrayList<String>, Boolean> RPSMap = new HashMap<>();

	public RPSCell(CurrentParameters cp, MoveHelper mh, String myState) {
		super(cp, mh, myState);
		super.cellSpecificBehavior.put("red", new Red());
		super.cellSpecificBehavior.put("blue", new Blue());
		super.cellSpecificBehavior.put("green", new Green());
		super.cellSpecificBehavior.put("white", new White());
		this.setNextCellParameters(this.getCurrentCellParameters());
		possibleStates = new ArrayList<String>(Arrays.asList("red", "blue", "green", "white"));
		setRPSMap();
	}

	private void setRPSMap() {
		addKeyValToRPSMap("green", "blue", false);
		addKeyValToRPSMap("green", "red", true);
		addKeyValToRPSMap("blue", "red", false);
	}
	
	private void addKeyValToRPSMap(String current, String opponent, boolean result) {
		ArrayList<String> newKey = new ArrayList<String>(Arrays.asList(current, opponent));
		ArrayList<String> newKeyReverse = new ArrayList<String>(Arrays.asList(opponent, current));
		RPSMap.put(newKey, result);
		RPSMap.put(newKeyReverse, !result);
	}
	
	public int getCurrentHitCount() {
		return Integer.parseInt(getCurrentCellParameters().get("hitcount"));
	}
	
	public int getNextHitCount() {
		return Integer.parseInt(getNextCellParameters().get("hitcount"));
	}

	protected void incrementNextHitCount() {
		setNextHitCount(getNextHitCount() -1);
	}
	
	protected void decrementNextHitCount() {
		setNextHitCount(getNextHitCount() +1);
	}

	protected void setNextHitCount(int num) {
		if (num<0){
			num = 0;
		}
		
		getNextCellParameters().put("hitcount", Integer.toString(num));
	}
	
	protected boolean checkDead() {
		return getNextHitCount()>=10;
	}
	
	
	@Override
	public GeneralCell clone() {
		return new RPSCell(this.currentGameParameters, this.moveHelper, this.getState());
	}

}
