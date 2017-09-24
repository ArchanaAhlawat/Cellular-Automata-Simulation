package src;

import java.util.HashMap;

public class PredatorPreyCell extends Cell{
	int spawnCount = 0;
	int deathCount = 0;
	int spawnThreshold;

	public PredatorPreyCell(HashMap<String, String> instanceVariables) {
		super(instanceVariables);
	}
}
