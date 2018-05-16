Design Review
=======

### Overall Design

#### High Level Design

The front end, the back end, and the interface each have its own package.  In the front end package, in the main class, the simulation loop/timeline
is set up.  There are separate classes to set up the grid panel and the configuration options panel, deal with and allow user actions, and
initialize and update using data from CellManager during each time step.  The front end part will also throw errors by sending the user
a message about badly configured XML files or invalid grid sizes before running a new simulation.  The front end only directly interacts with
an instance of Initializer and an instance of CellManager, which Initializer creates.  These two parts are part of the interface, the "middleware"
package.  So, the front end gets the grid to display via the public loadConfig(String configFileName) method in Initializer.  This method
triggers parsing through a given XML file, storing the relevant data, creating a grid and specific cells, and then ultiamtely
returns a CellManager instance to the front end.  The front end then uses the public computeAndPerformUpdates() method in CellManager in order
to trigger each timestep change in cells and receive an updated grid to display.  The front end only directly interacts with the back-end --
Cell objects -- when it needs to override XML data with real-time user changes made by clicking the grid.

The key parts of the interface are Initializer, CellManager, Grid, and Style.  The Initializer class reads through an XML file, stores all
relevant information, and passes this information to CellManager in order to eventually create the specific Cells.  The XML files are
formatted so that they contain information that is simulation specific and cell specific.  Simulation specific and cell specific data are
stored in separate maps.  An example of a cell specific map might be a predator prey map such as this: {"state": "fish", "reproductionCount": "3"}.
An example of a simulation specific map might be {"satisfactionThreshold": "70"} for a segregation simulation.  For the sake of ease of
new simulations, XML files are designed so that not every single cell in the desired grid must be noted, but only all unique possible cells. 
Note that the Style XML file is designed to take care of desired distibutions or locations of specific cells.  So, Initializer creates a
new instance of DefaultValues and CurrentParameters.  DefaultValues is a class meant to facilitate user interaction with the grid by 
allowing the user to change the grid or specific cells back to their default states.  CurrentParameters is a class also dedicated to the
same purpose, but it holds all the maps and allows for users to change values on a simulation level.  Initializer uses the CellManager 
addInitialCells() method in order to pass in all the unique states for cells that are possible.  Then, Initializer also sends a instantiated
DefaultValues, CurrentParameters, and MoveHelper objects.  The MoveHelper object holds a map of states and default motions that determine how
Cells eventually move (whether that is random, in adjacent directions only, etc.).  Before ultimately returning CellManager,
the Style XML file is parsed and then CellManager creates the cells.

When the CellManager receives all this information, it sets its grid of cells by using the grid object that the Style class created after
parsing through the Style XML file.  This grid object (for now) can be a normal rectangular grid (Grid) or a triangular grid (TriangleGrid)
depending on the specifications laid our in the Style XML file.  CellManager calls grid to have it set the cells up.  Grid holds the logic
for computing neighbors and setting up a particular geometric arrangement of cells.  Also, the grid class can either randomly
distribute all the unique possible cells among all cells possible, or it can take in concentrations to create the grid.  In the future, this
could be extended to have a grid created with specific locations of cells given. CellManager ultimately returns this grid to allow the front
end to display it.  When the front end calls CellManager to update every timestep, the performUpdates method computes neighbors using logic
in the grid class, then for every cell, calls cell.computeState() and later, cell.becomeNextState().  The initial cell computeState() method
takes in the list of neighbors calculated.  The reason that computeState and becomeNextState are done separately
is to avoid conflicting actions by cells as they need to react to the current state only and at the same time, not potential 
next states of neighbor cells.  The creation of cell objects via constructors and a cell.clone() method, as well as cell update
methods are the only ways that the interface interacts with the back end and the simulation rules logic.

Finally, the back end is mainly made up of all possible cells for each simulation as well as move classes that help segment
actions.  It only interacts with the front end and the interface in the ways described above.  Each cell is a subclass
of GeneralCell and every updateCell is abstracted because the rules are implemented in cell specific methods and updateCell
returns the same type of object every time it is called.  Each cell uses its associated attribute map, which is taken from
its constructor which passes in default and current value maps.  Then it uses its neighbors (passed from CellManager) to 
determine what next state to take.  Ultimately, it becomes its next state internally, therefore not requiring a new cell to 
be created or a grid to be created again.  

#### Adding new features
Adding a new simulation is not too difficult at all.  First it would require a new simulation XML file that contains all simulation
level information as well as all the unique cells possible and their associated instance variables and values.  Because XML file configuration
only requires one copy each of each possible cell (not all cells that will be in the grid), this is very easy for someone to create manually
and rapidly.  A new Style XML file is not required, since these files are not specific to any simulation.  However, adding one is also very 
easy because it only required a couple of node values.  Next, in CellManager, in the createCell() method, a new if statement must be added to 
check for the new type of cell being created.  This is an unavoidable design currently.  Then, new Cell classes that represent each possible
cell should be added. So, for example if a new simulation that had 3 unique types of cells was going to be created, then 3 new subclasses 
would extend GeneralCell and implement their own rules.  This design decision to have every single possible cell (not just a broad simulation cell)
have a subclass means that there might be more work in terms of creating more classes, but simplifies the logic of rules implementation.  Then, 
the front end would need to add a line to handle this new simulation.

#### Dependencies
The dependencies between the parts are pretty clear and easy to find.  Mostly they are through public methods and parameters.  There are many 
protected methods that help each separate part (back end, front end, and interface) to interact with its own internal structures fluidly, but 
keep these separate from classes that should definitely not have full access.  Also, there is lots of internal logic implementation throughout 
the back end and interface that is not seen by front end classes that use the end result of grids.  Additionally, cells only have knowledge of
their own states and their neighbors.  The CellManager and Grid classes deal with GeneralCells, and do not know information about what 
specific cell subclasses are being used once the cells are created.

#### Two components by my teammates
In the CellDisplay class, implemented by Adithya, all the methods are extremely readable because they all do something direct and specific and
their names make it very clear what their purpose is.  This class has methods to create the grid to display, to get the pictures associated
with different types of cells (for future display), and 4 methods that relate to the display of the drop down menu and what nodes to add if it is
activated.  All the methods have very explicit names, such as initDropDownPanel(), which adds the nodes for the drop down menu if needed, and
getImageForState(), which gets the images for each cell to display later as imageviews.  It is a pretty encapsulated class that can be 
altered if needed without changing implementation of other classes.  One big reason for this is that the purpose of this class and each method
is very clear, and each method does not do something too big.  It also uses a lot of information from other classes using flexible
data structures like ArrayLists, so changing information from those other classes can easily be handled in the CellDisplay class.

In the PredPreyNewCell class, implemented by Sam, all the methods are very readable because they are very short and their names
are explicit.  This class is the general predator prey cell class that handles some simulation level information.  An example that 
shows the readability and clarity of this code is that some method names are randomlySetSpawnCount() and updateSpawnCount(), 
which do exactly what they say.  It is very encapsulated because the methods inside can change to implement some additional rules, 
and other classes, such as CellManager which uses updated cell states, does not have to be changed to handle new information. 
Additionally, rather than handle many different types of rules and cells, it only handles the general predator prey cell information on 
a simulation level, while Shark cell and Fish cell (subclasses of GeneralCell) can handle information and actions specific to the two.

One thing I learned about design from my teammates' code is to write better and more clear method names and work to make my methods
shorter, even if I think that that is useless.  It definitely makes code easier to understand, compared to trying to follow convoluted
logic through long methods that call vaguely named methods.  My teammates' methods often have names that are longer than mine, and
this is helpful in rapidly figuring out how a particular class works without actually having implemented it myself.

Overall, the code is generally consistent in its layout, though my method names could be more specific to match my teammates' styles.  The 
two examples above both show classes that are similar in terms of having short and descriptive methods.  Similarly, my CellManager class
has many methods like this, that tackle small actions and have specific names like computeAndSetState().

### My Design

#### High level explanation of my code
I reviewed a lot of what my code does and how it interacts in the previous section, so I will focus here on the flow of the 
program in regards to the interface, and how different classes interact through methods.

##### Initializer
* An instance is initialized in the front end and then loadConfig(filename) is called.
* First, the Style XML file is processed internally.  Initializer calls an instance of the Style class in order to parse information
from it and create the empty Grid object (shape: triangle, square, and open to more implementions)
* A new CellManager is created (only once), with the Grid object in its constructor.
* XML parser (parseXMLFile() - creates maps of general simulation level information and cell level information (as described above). The XML 
parsing part is broken down into several methods that handle creating these separate maps, and then passing them to CellManager
* CellManager is passed information about all unique possible cells, all the default attribute maps, and general information maps
* Helper objects such as MoveHelper (stores movement information), DefaultValues, and CurrentParameters are created and passed to CellManager.
* It is best to create these objects in the Initializer class because they require information from the XML files
* Finally, the one CellManager instance is returned from loadConfig(filename) to the front end.

##### CellManager
* After all the information is passed to it via initializer, the CellManager creates different unique cells with information given
to it (defaultvalues, movehelper, currentparameters).  
* Then the grid is created (setMatrix()) by calling a method in the grid object. 
* Public methods such as getShape() and getGrid() serve to help the front end display the current grid.
* performUpdates() is a public method accessed by the front end.  It calls private methods such as computeAndSetState, which
uses the grid object to compute neighbors.  Within computeAndSetState, each cell object updates itself based on its given neighbors.

CellManager interacts with the Grid object by calling grid.setGridParamaters(ArrayList of all possible cells), and then uses a public
get method to get the created grid.  Then, grid methods are only called after that when each update is made, in order to determine
all the neighbors of a cell.

The TriangleGrid class extends the concrete Grid class.

#### Two implemented features

One feature that I implemented that I think is good is the way I handled the creation of the grid according to information
in the Style XML file and separated the cell updates from the aggregate grid.  One issue I dealt with was where to compute
the list of neighbors for each cell, how to store the grid, and how to ensure that each individual cell only had access to the
information that was directly relevant to itself and nothing more.  Originally, I decided to create a Neighbors class, that would
handle different types of neighbors -- cardinal, diagonal, etc.  I thought that this design could easily handle different
shapes of grids as well.  However, as I was implementing this, I realized that computing neighbors was directly dependent on
the shape of the grid, so it would be easiest to combine this functionality instead of passing too much information, like 
the shape of the grid, around to many different classes.  Additionally, the user would be specifying neighbor rules and grid shape
in the same XML file, so this information could be aggregated.  So, I scrapped the Neighbors class and instead went with a Grid
class, that could both initialize and return the full grid to display (--> CellManager --> front end), and compute neighbors
based on grid shape and directions given.  This grid class lends itself to further extension to other shapes of grids and 
added rules that have to do with grids (rather than individual cells).  One possible issue with this code is that each 
grid class creates a matrix of cells, but CellManager also creates some cells (the unique ones) that it passes to the grid class.
This is probably not necessary; cell creation can be entirely encapsulated in the grid class.

The way I implemented the extension of the grid class to other shapes of grids is not ideal.  Although I extended the grid class
to a TriangleGrid class, I did not use the extension to avoid as much repeated code as I could have.  Instead, I would 
improve this design by created a Grid interface that has all the needed method signatures/actions, and then in specific grids 
classes like RectangleGrid, TriangleGrid, etc, I could actually implement these methods how I need to.  The TriangleGrid extension
code works as it should, but repeated code is definitely a code smell I could eliminate with an abstract class or interface.

### Flexibilty

This project has fairly flexible design because we separated out different distinct features/parts of the program.  For
example, the UI code for creating the overall UI design is one class, and another class has code to deal with how users 
can interact with and change the UI/grid.  These two classes don't necessarily have to be separated in our program in 
order for it to work well, but this separation enables us to make it clear what classes are responsible for what actions, and where we could add things
like additional user action events.  Additionally, we made sure we could easily extend certain key classes that can be
customizable, such as the grid, cell, and movement classes. In the future, these extendable classes could be implemented
using an interface as a starting point.

#### Two implemented features by other teammates

Sam implemented many different simulations using extended GeneralCell subclasses.  Cell classes directly implemented the 
rules of each simulation.  At first, he created a general Rules class that would hold all the different simulation rules
and apply them based on what types of cells were being used.  However, this meant that cell classes were extremely passive
objects that only held data and didn't actively control their states and internal information.  So, he implemented rules
directly in each cell class.  This is a very strong design decision in terms of encapsulation because the way in which each
cell changes is totally hidden from every other class, which is especially important for the front end and interface.  So,
the front end basically calls the CellManager to update every cell, and the CellManager accesses every cell and calls its 
update method.  The rules of updating are completely abstracted as the objects that the CellManager returns in its grid are
the same, just hold different information after every time step.  Each cell is a subclass of the parent class, GeneralCell.
CellManager can access all the methods associated with GeneralCell without needing to know the specific type of cell being 
implemented.  Examples of specific concrete cells are the Fish and Shark subclasses. In them, variables like spawncount
and deathcount are updated within methods, and these variables are not need for functionality in the front end or the 
interface.  This fact also enables extension because variables can be added in the XML files or in the classes, and this
will not change how the interface or the front end should be implemented.  One challenge or possible design flaw is that
each subclass implemented is a very specific cell object, so this means adding multiple simulations with multiple types
of cells will result in a huge number of added subclasses.  This will be fine functionality-wise, but a future edit might be
to combine subclasses more and, as an example, use only a general PredatorPreyCell instead of specific Fish and Shark cells.

Sam also implemented movement classes in order to facilitate different rules or user preferences for how cells should move
after every time step.  Three subclasses, MoveStay, MoveRandom, and MoveAdjacent, all extend cellMovement, an abstract class
that has general rules for how to move and record movement using a list of neighbors.  MoveStay, MoveRandom, and MoveAdjacent
all override the computeAndPerformMovement method in order to define movement for the different rules.  For example, MoveStay
overrides this method by simply return false, indicating that a particular cell should not move at all.  These movement
subclasses are called in update methods of the various cells, and the type of movement is determined by XML file specifications.
This design is well designed for extension because any type of movement can be handled in a separate subclass that can be added.
Adding this class and editing an XML file to indicate the new type of movement desired are the only two changes required
to implement new movements.  This shows that the movement functionality is well encapsulated.

### Alternate Designs

Although we were not able to implement all the project extensions within the timeframe of the second sprint, we made some
major changes to our code design and flow in order to handle extensions in the future.  Many of the design discussions were 
focused on making sure we encapsulated our code well so that our separate implementations didn't matter, only the way our
classes interacted with others.  So, for example, after talking to my teammates, and specifically with Sam to figure out 
how his classes could best handle cell data, I then decided how to format XML files and what the flow of information would
look like.  The implementation of cell and simulation data as maps of instance variables was a very good idea that allowed
us to easily extend our program to handle many different simulations and specifications without manually editing XML files 
too much.  An alternate design for this part is to list out all the cells in the grid in order of their locations and directly
translate this information to a grid.  This would make it a little easier for a user to specify the locations of cells, but
would make it more difficult for a user to manually change XML files' other characteristics.  However, an alternative that 
could work is adding an XML file editing part of the UI, so that the user can change raw data easily through a program that 
edits the files.  Ultimately, I decided to shorten XML files as much as possible so that they contained unique data that 
didn't require the reading of a lot of repeated information.

One other big discussion we had was how to handle the movement of cells.  Sam and I discussed this in detail because this 
involved the interface and the back end.  This was complicated because we initially thought simulating movement would
require direct access to a grid where we would need to move cell references to different grid entries.  So, our first implementation
was done via the CellManager, which would access the grid, iterate through each cell, update the cell, and if the update 
involved a movement, move a cell based on some conditions of what its neighbors' states were and whether their future states
had been computed or not.  This was unnecessarily complicated, convoluted, and confusing.  It also meant that cells were passive
objects being moved around a grid and barely changing.  Instead, we decided to make the cells much more active objects, 
which would "move" themselves and change their own internal data.  We decided to encapsulate the move functionality within 
update methods of cells (Sam handled this implementation).  CellManager could call updates to cells, and cells would handle movement
by simply changing their states.  So if a cell was going to move to an adjacent neighbor, the adjacent neighbor cell state
would change to what the moving cell's state is, and the moving cell's state would change to its adjacent neighbor's state.
The visualization of this would show a movement, but it is actually a changing of internal states, while the objects remain
the same.  This reduced confusion, made cell objects active, and enabled clear separation of actions and responsiblities 
between the interface and the back end.

### Conclusions

The best feature of the project's current design is the separation of responsibilities between the interface and the back end, 
specifically between the CellManager, Grid, and cell subclasses as described above.  One of the biggest lessons I learned 
during this project was how to effectively separate components of a projects (encapsulating well) while ensuring fluid,
clear, and succinct communication between different components.  Of course lots of different functionality can fit and work
when put into many different classes, but figuring out what class or what package a particular functionality works best in
in terms of design is sometimes unclear without trying out implementations or planning out program flow in a lot of detail.

The feature that needs the most improvement is a separation of responsibilities between the Grid and CellManager classes.
As I was implementing the interface, I needed to continuously change my implemenations in terms of class responsibilities 
and data passing, so I think some of my code flow became confusing, especially as I added the grid classes.  To fix this, I would
like to encapsulate cell creation in the Grid classes.  I learned that it is really important to review code more continuously
after major changes such as moving functionality to a new class such as grid.







