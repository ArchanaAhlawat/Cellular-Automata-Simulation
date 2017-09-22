package src;

import java.util.HashMap;

public abstract class CellSocietyCell {
	
	
	String currentState;
	String nextState;
	HashMap<String, String> myParameterMap = new HashMap<String, String>();
	
	public CellSocietyCell(String state, HashMap<String, HashMap<String, String>> instanceVariables) {
		this.myParameterMap = instanceVariables.get(state);
		this.currentState = state;
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
}
