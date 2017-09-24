package src;

import java.util.HashMap;

public class Cell {
	String currentState;
	String nextState;
	String nextMove;
	HashMap<String, String> myParameterMap = new HashMap<String, String>();
	
	public Cell(HashMap<String, String> instanceVariables) {
		this.myParameterMap = instanceVariables;
	}
	
	protected HashMap<String, String> getParameterMap() {
		return this.myParameterMap;
	}
}
