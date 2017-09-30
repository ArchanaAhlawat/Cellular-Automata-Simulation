package middleware;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
	private static final int GRID_SIZE = 50;
	private ArrayList<GeneralCell> allCellsPossible;
	private GeneralCell[][] grid;
	
	public Grid(ArrayList<GeneralCells> all) {
		allCellsPossible = all;
		setGrid();
	}
	
	public void setGrid() {
		for (int i = 0; i < GRID_SIZE; i++){
			for (int j = 0; j < GRID_SIZE; j++) {
				Random rand = new Random();
				int num = rand.nextInt(allCellsPossible.size()) + 1;
				grid[i][j] = new Cell(allCellsPossible.get(num));
			}
		}
	}
	
	public ArrayList<int[]> computeNeighbors(int i, int j) {
		ArrayList<int[]> neighborIndices = new ArrayList<int[]>();
		int[] indices = new int[]{-1,0,1};
		for (int k: indices) {
			for (int l: indices) {
				int[] temp = new int[2];
				temp[0] = k+i;
				temp[1] = l+j;
				if (! neighborIndices.contains(temp) && !(temp[0] != i && temp[1] != j)) { 
					neighborIndices.add(temp);
				}
			}
		}
		return neighborIndices;
	}
	
	public GeneralCell[][] getGrid() { // will only ever return once (as it is implemented). updates happen in CellManager
		return grid;
	}
}
