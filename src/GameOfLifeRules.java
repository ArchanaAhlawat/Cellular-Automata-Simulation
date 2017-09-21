package src;
import java.util.HashMap;

public class GameOfLifeRules extends Rules{
	
	int myUnderpopulationThreshold;
	int myOverpopulationThreshold;
	int myReproductionCriticalNumber;

	public GameOfLifeRules(HashMap<String, String> map) {
		super(map);
	}
	
	protected void setInstanceVariables() {
		this.myUnderpopulationThreshold = Integer.parseInt(this.getParameterMap().get("underpopulationThreshold"));
		this.myOverpopulationThreshold = Integer.parseInt(this.getParameterMap().get("overpopulationThreshold"));
		this.myReproductionCriticalNumber = Integer.parseInt(this.getParameterMap().get("reproductionNumber"));
	}

	@Override
	public Object applyRules(Cell cell, Collection<Cell> cellNeighbors) {
		String currentCellState = cell.getState();
		int neighborsAliveCount;
		int neighborsDeadCount;
		
		for (Cell neighbor: cellNeighbors) {
			if (neighbor.getState().equals("alive")) {
				neighborsAliveCount+=1;
			} else {
				neighborsDeadCount+=1;
			}
		}
		
		if (neighborsAliveCount<this.myUnderpopulationThreshold)  {
			return "dead";
		} else if (neighborsAliveCount>this.myOverpopulationThreshold) {
			return "dead";
		} else if (neighborsAliveCount==this.myReproductionCriticalNumber && currentCellState.equals("dead")) {
			return "alive";
		} else {
			return "alive";
		}
	}
	
	

}
