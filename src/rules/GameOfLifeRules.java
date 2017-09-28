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
		this.myUnderpopulationThreshold = Integer.parseInt(this.getParameterMap().get("underpopulationthreshold"));
		this.myOverpopulationThreshold = Integer.parseInt(this.getParameterMap().get("overpopulationthreshold"));
		this.myReproductionCriticalNumber = Integer.parseInt(this.getParameterMap().get("reproductionnumber"));
	}

	@Override
	public void performRules() {
		int neighborsAliveCount = calculateNeighborsOfState("alive").size();
		
		if (neighborsAliveCount<this.myUnderpopulationThreshold)  {
			setCellNextState("dead");
		} else if (neighborsAliveCount>this.myOverpopulationThreshold) {
			setCellNextState("dead");
		} else if (neighborsAliveCount==this.myReproductionCriticalNumber && getCellState().equals("dead")) {
			setCellNextState("alive");
		} else {
			setCellNextState("alive");
		}
	}

	

}
