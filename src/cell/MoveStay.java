package cell;

import java.util.ArrayList;
import java.util.Map;

public class MoveStay extends cellMovement {

	MoveStay(CellManager cm) {
		super(cm);
	}

	@Override
	public boolean computeAndPerformMovement(GeneralCell cell) {
		return false;
	}

}
