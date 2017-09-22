package src;
import java.util.Collection;
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
	public Object applyRules(CellSocietyCell cell, Collection<CellSocietyCell> cellNeighbors) {
		String currentCellSocietyCellState = cell.getState();
		int neighborsAliveCount = 0;
		int neighborsDeadCount = 0;
		
		for (CellSocietyCell neighbor: cellNeighbors) {
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
		} else if (neighborsAliveCount==this.myReproductionCriticalNumber && currentCellSocietyCellState.equals("dead")) {
			return "alive";
		} else {
			return "alive";
		}
	}
	
	

}
