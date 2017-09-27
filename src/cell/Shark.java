package cell;

import java.util.ArrayList;
import java.util.HashMap;

public class Shark extends Animal{

	@Override
	public void cellSpecificEveryTime(GeneralCell cell) {
		super.cellSpecificEveryTime(cell);
		eatFish(cell);
		
	}

//	@Override
//	public void cellSpecificBasedOnNextState(GeneralCell cell) {
//		// TODO Auto-generated method stub
//		
//	}
//	
	private void eatFish(GeneralCell cell){
		cell.calcAndReplace("fish", cell.getDefaults().get("empty"));
	}
	


	
}
