package cell;

import java.util.List;

public abstract class DetermineNeighborsHelper {

	protected CellManager cm;
	
	DetermineNeighborsHelper(CellManager cellMan) {
		this.cm = cellMan;
	}
	
	protected int[][] getMatrixSize() {
		return cm.getDimensions();
	}
	
	protected abstract List<GeneralCell> getNeighbors(int[][] cellLocation);
	
}
