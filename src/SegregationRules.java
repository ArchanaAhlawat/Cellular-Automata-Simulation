package src;
import java.util.Collection;
import java.util.HashMap;

public class SegregationRules extends Rules{
	
	double mySatisfiedThreshold;
	
	public SegregationRules(HashMap<String, String> map) {
		super(map);
	}

	
	protected void setInstanceVariables() {
		mySatisfiedThreshold = Double.parseDouble(this.myParameterMap.get("threshold"));				
	}
	
	private double getSatisfiedThreshold() {
		return this.mySatisfiedThreshold;
	}
	
	public void setSatisfiedThreshold(double newThreshold) {
		this.mySatisfiedThreshold = newThreshold;
	}
	
	
	@Override
	public Object applyRules(Cell cell, Collection<Cell> cellNeighbors) {
		double similarNeighbors;
		double totalNeighbors = cellNeighbors.size();
		
		String current_cell_state = cell.getState();
		
		for (Cell neighbor: cellNeighbors) {
			String neighbor_state = neighbor.getState();
			
			similarNeighbors = neighbor_state.equals(current_cell_state) ? similarNeighbors+1 : similarNeighbors;
		}
		
		double percentSimilarNeighbors = similarNeighbors/totalNeighbors;
		if (percentSimilarNeighbors<this.getSatisfiedThreshold()) {
			return "move";
		} else {
			return "stay";
		}
		
	}


}
