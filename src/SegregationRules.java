package src;

import java.util.Collection;
import java.util.HashMap;

public class SegregationRules extends Rules {

	double mySatisfactionThreshold;

	public SegregationRules(HashMap<String, String> map) {
		super(map);
	}

	protected void setInstanceVariables() {
		mySatisfactionThreshold = Double.parseDouble(this.myParameterMap.get("satisfactionthreshold"));
	}

	private double getSatisfactionThreshold() {
		return this.mySatisfactionThreshold;
	}

	public void setSatisfactionThreshold(double newThreshold) {
		this.mySatisfactionThreshold = newThreshold;
	}

	@Override
	public void performRules() {
		double similarNeighbors = calculateNeighborsOfState(getCellState()).size();
		double totalNeighbors = getNeighbors().size();

		double percentSimilarNeighbors = similarNeighbors / totalNeighbors;

		if (percentSimilarNeighbors < this.getSatisfactionThreshold()) {
			setMoveRandom();
		} else {
			setMoveStay();
		}

	}

}
