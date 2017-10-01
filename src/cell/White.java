package cell;

import java.util.HashMap;
import java.util.Map;

public class White extends RPSColor {

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		super.cellSpecificEveryTime(cell);
		changeWhite(cell);
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getDefaultState() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", "white");
		ret.put("hitcount", "0");
		return ret;
	}
	
	public void changeWhite(GeneralCell cell) {
		if (cellToAttackHitCount()<9){
			cell.setNextCellParameters(cellToAttack.getCurrentCellParameters());
			cell.getNextCellParameters().put("hitcount", Integer.toString(cellToAttackHitCount()+1));
		}
	}

}
