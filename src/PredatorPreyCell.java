package src;

import java.util.HashMap;

public class PredatorPreyCell extends CellSocietyCell{

	int spawnCount = 0;
	int deathCount = 0;
	int spawnThreshold;

	
	public PredatorPreyCell(String state, HashMap<String, HashMap<String, String>> instanceVariables) {
		super(state, instanceVariables);
		setInstanceVariables();
	}
	
	private void setInstanceVariables() {
		spawnCount = Integer.parseInt(this.getParameterMap().get("spawncount"));
		deathCount = Integer.parseInt(this.getParameterMap().get("deathcount"));	
		spawnThreshold = Integer.parseInt(this.getParameterMap().get("spawnthreshold"));
	}
	
	private int getSpawnCount() {
		return this.spawnCount;
	}
	
	private int getSpawnThreshold() {
		return this.spawnThreshold;
	}
	
	private void setSpawnThreshold(int num) {
		this.spawnThreshold = num;
	}
	
	public boolean spawnNow() {
		return getSpawnCount()>getSpawnThreshold();
	}
	
	

}
