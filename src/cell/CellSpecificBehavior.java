package cell;

public interface CellSpecificBehavior {

	void cellSpecificEveryTime(GeneralCell cell);
	void cellSpecificBasedOnNextState(GeneralCell cell);
}
