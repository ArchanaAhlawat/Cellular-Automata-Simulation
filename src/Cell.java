package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Cell {
	private String attribute;
	private String type;
	
	String currentState;
	String nextState;
	String nextMove;
	HashMap<String, String> myParameterMap = new HashMap<String, String>();

	// method only to to debug
	public Cell(String attrib, String typee) {
		attribute = attrib;
		type = typee;
	}
	
	
	public Cell(HashMap<String, String> instanceVariables) {
		//this.myParameterMap = instanceVariables.get(state);
	}
	
	public String getNextMove(String s) {
		return this.nextMove;
	}
	
	public void setNextMove(String s) {
		this.nextMove = s;
	}

	public String getState() {
		return this.currentState;
	}
	
	public void setState(String s) {
		this.currentState = s;
	}
	
	public void setNextState(String s){
		this.nextState = s;
	}
	
	protected HashMap<String, String> getParameterMap() {
		return this.myParameterMap;
	}

	
	@Override
	public String toString() {
		return "attribute: " + attribute + " type: " + type;
	}
}
