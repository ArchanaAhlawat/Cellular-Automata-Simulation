package middleware;

import java.util.ArrayList;
import java.util.Random;
import cell.GeneralCell;

public class Grid {
	private static final int GRID_SIZE = 50;
	private ArrayList<GeneralCell> allCellsPossible = new ArrayList<>();
	private GeneralCell[][] grid = new GeneralCell[GRID_SIZE][GRID_SIZE];

	public Grid(ArrayList<GeneralCell> all) {
		allCellsPossible = all;
		setGrid();
	}

	public void setGrid() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Random rand = new Random();
				int num = rand.nextInt(allCellsPossible.size());
				grid[i][j] = (allCellsPossible.get(num)).clone();
			}
		}
	}

	public ArrayList<int[]> computeNeighbors(int i, int j) {
		ArrayList<int[]> neighborIndices = new ArrayList<int[]>();
		int[] indices = new int[] { -1, 0, 1 };
		for (int k : indices) {
			for (int l : indices) {
				int[] temp = new int[2];
				temp[0] = k + i;
				temp[1] = l + j;
				if (!neighborIndices.contains(temp) && !(temp[0] == i && temp[1] == j) && temp[0]>=0 && temp[1]>=0) {
					neighborIndices.add(temp);
				}
			}
		}
		return neighborIndices;
	}

	public GeneralCell[][] getGrid() { // will only ever return once (as it is implemented). updates happen in
										// CellManager
		return grid;
	}
}
