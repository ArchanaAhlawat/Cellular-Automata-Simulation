###**Inheritance Review with Pau and Ben.**
=========
_________

#### **Part One**
1. We are encapsulating XML file parsing/information extraction from other parts of the program.  Through this, we get information about cells and initial states from the file and store that information for other parts of the program to use and display on the GUI.
2. For my part, I am building inheritance into Cell creation.  Cell will be an abstract class, and there will be cell subclasses that will exhibit behavior based on what simulation is in play.
3. Being able to create different cells is important for flexibility, so the abstract cell will be open.  XML parsing should definitely be closed; all data should be in the XML files, but how we parse through them should not change.
4. If the user does not input a well formatted XML file or if the file chosen is not of XML type, then the Initializer (my XML parser) would have to throw an exception and print to a dialogue that the user would see.  The dialogue would tell the user to try again and input a correctly formatted .XML file.
5. The design is good because it encapsulates XML parsing really well so that only one class deals with it and only passed on information to another class.  Also, Cell objects only know about their states and not anything about neighbors.  So, the CellManager can figure out what Cells are a Cell's neighbors.

#### **Part Two**
1. The XML parser, Initializer, passes on information to CellManager, which creates and maintains Cell objects and the grid matrix of Cells.  I am writing Initializer, CellManager, and the Cells.  CellManager interacts with the Rules class by passing in cells and their neighbors and then updating state based on what the Rules returned.  Also, Initializer is initialized via the Front End class once the user picks a simulation to run (and a file).  Then, the Front End class also interacts with CellManager by using a public method, getMatrix to get the current Cell matrix.  It uses this matrix to display a grid on the screen.
2. The dependency between the Front End class and the Initializer and the CellManager is based on the class's behavior.  Front End must use choices from the end user to pass to figure out what raw data to parse.  Also, Front End must get a current updated grid of Cells after every step in the simulation.  The dependency between CellManager and Rules is based on implementation.  CellManager could actually implement the methods within Rules by itself, but my group decided to separate those out into different classes so that each class is performing a more specific action.
3. The dependency between Front End and the other classes is unavoidable because we need to communicate with what the user is choosing and doing.  Only the bare minimum necessary information should be passed between these classes though.  CellManager and Rules must have very clear implementations and boundaries for what they do and do not do.
4. Went over Cell superclass and GameOfLife subclass.  Could simplify this class to only have a map of variables, and actually only have a concrete Cell class. Taking away inheritance seems like a good idea.
5. If simulation-specific rules are implemented in different Rules subclasses, then different Cell subclasses are actually unnecessary because each cell has the same set of methods.  A map of variables is a good idea, and each map can be of a different map (determined by XML).

#### **Part Three**
1. Five use cases. XML file parser can handle any future simulations and any number of variables can be added.  User can pick what XML file they want to use for the simulation.  Rules can implement different simulation rules. Cells can move simultaneously via CellManager (called in Front End).
2. I am most excited to work on the CellManager. It will be interesting to figure out how to best interact with Rules and make sure the roles are distinct.
3. I am most worried to work on the XML parsing portion of the project.  I am not sure how to start and hope that storing the data is not inconvenient.