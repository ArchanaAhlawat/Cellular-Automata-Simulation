package src;

public class Cell {
	private String attribute;
	private String type;
	
	public Cell(String attrib, String typee) {
		attribute = attrib;
		type = typee;
		
	}
	
	@Override
	public String toString() {
		return "attribute: " + attribute + " type: " + type;
	}
}
