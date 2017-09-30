package cell;

import java.util.HashMap;
import java.util.Map;

public class MoveHelper {

	CellManager cm; 
	HashMap<String, cellMovement> cellSpecificMovement;
	MoveHelper(CellManager cellman, Map<String, String> defaultMotion) {
		cm = cellman;
		for (String key: defaultMotion.keySet()) {
			// Can add in new types of motion
			switch (defaultMotion.get(key)) {
			case "adjacent":
				cellSpecificMovement.put(key, new MoveAdjacent(cm));
				break;
			case "random":
				cellSpecificMovement.put(key, new MoveRandom(cm));
				break;
			case "stay":
				cellSpecificMovement.put(key, new MoveStay(cm));
				break;
			default:
				cellSpecificMovement.put(key, new MoveStay(cm));
				break;
			}
		}
	}
	
	
	public void moveCell(GeneralCell cell) {
		if (cellSpecificMovement.get(cell.getState()).computeAndPerformMovement(cell)) {
			cell.changeToDefault("empty");
		}
	}
	
}
