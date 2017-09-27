package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.control.Cell;

public class FireRules extends Rules {

	double probCatch;

	public FireRules(HashMap<String, String> map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setInstanceVariables() {
		this.probCatch = Double.parseDouble(this.getParameterMap().get("probCatch"));

	}

	private double getProbCatch() {
		return this.probCatch;
	}

	@Override
	public void performRules() {
		String state = getCellState();
		switch (state){
		case "empty":
			setCellNextState("empty");
			break;
		case "fire":
			setCellNextState("empty");
			break;
		case "tree":
			treeRules();
			break;
		case default:
			break;
		}
	}

	public void treeRules() {
		if (calculateNeighborsOfState("fire").size() > 0) {
			Random rand = new Random();
			if (rand.nextFloat() <= getProbCatch()) {
				setCellNextState("fire");
			} else {
				setCellNextState("tree");
			}
		}
	}

}
