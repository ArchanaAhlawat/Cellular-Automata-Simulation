package src;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.control.Cell;

public class PredatorPreyRules extends Rules {

	public PredatorPreyRules(HashMap<String, String> map) {
		super(map);
	}

	@Override
	protected void setInstanceVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object applyRules(Cell cell, Collection<Cell> cellNeighbors) {
		String currentCellState = cell.getState();
		switch (currentCellState) {
			case "fish":
				fishRules(cell, cellNeighbors);
				break;
			case "shark":
				sharkRules(cell, cellNeighbors);
				break;
			case "empty":
				break;
			default: 
				break;
		}
		
		
		return null;
	}
	
	public void fishRules(Cell fish, Collection<Cell> cellNeighbors) {
		ArrayList<Cell> emptyNeighbors = calculateEmptyNeighbors(cellNeighbors);
		if (emptyNeighbors.size()>0) {
			spawnNewFish(emptyNeighbors);
		}
		
	}
	
	public void spawnNewFish(ArrayList<Cell> cellNeighbors){
		int numNeighbors = cellNeighbors.size();
		Random rand = new Random();
		int random_index = rand.nextInt(numNeighbors);
		Cell targetSpawnCell = cellNeighbors.get(random_index);
		targetSpawnCell.setNextState("fish");
	}

	
	public ArrayList<Cell> calculateEmptyNeighbors(Collection<Cell> cellNeighbors){
		ArrayList<Cell> emptyNeighbors = new ArrayList<>
		for (Cell cell: cellNeighbors) {
			if (cell.getState().equals("empty")){
				emptyNeighbors.add(cell);
			}
		}
		return emptyNeighbors;
		
	}
	
	public void sharkRules(Cell shark, Collection<Cell> cellNeighbors) {
		setNextState("randomlymove");
		
	}
}
