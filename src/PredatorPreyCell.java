package src;

import java.util.HashMap;

public class PredatorPreyCell extends CellSocietyCell{

	int spawnCount = 0;
	int deathCount = 0;
	int reproduceThreshold;

	
	public PredatorPreyCell(String state, HashMap<String, HashMap<String, String>> instanceVariables) {
		super(state, instanceVariables);
		setInstanceVariables();
	}
	
	private void setInstanceVariables() {
		spawnCount = Integer.parseInt(this.getParameterMap().get("spawncount"));
		deathCount = Integer.parseInt(this.getParameterMap().get("deathcount"));	
	}
	
	public int getSpawnCount() {
		return this.spawnCount;
	}
	
	public boolean spawnNow() {
		return this.spawnCount>this.reproduceThreshold;
	}
	
	

}
