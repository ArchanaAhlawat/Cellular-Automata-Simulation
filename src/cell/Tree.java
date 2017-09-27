package cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tree implements CellSpecificBehavior{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		if (cell.calcCurrNeighborsOfState("fire").size() > 0) {
			Random rand = new Random();
			// Better way than casting???
			if (rand.nextFloat() <= ((FireNewCell) cell).getProbCatch()) {
				cell.changeToDefault("fire");
			}
		}	
	}

	@Override
	public void cellSpecificBasedOnNextState(GeneralCell cell) {
		// Nothing	
	}



}
