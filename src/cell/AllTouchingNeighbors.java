package cell;

import java.util.ArrayList;
import java.util.List;

public class AllTouchingNeighbors extends DetermineNeighborsHelper {

	AllTouchingNeighbors(CellManager cellMan) {
		super(cellMan);
	}

	@Override
	protected List<GeneralCell> getNeighbors(int[][] cellLocation) {
		List<GeneralCell> ret = new ArrayList<GeneralCell>();
		// Add all neighbors cells, check for edge cases
		return null;
	}

}
