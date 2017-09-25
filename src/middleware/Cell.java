package middleware;

public abstract class Cell {

	private String state;
	
	public Cell(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	public abstract void setState(String state);
}
