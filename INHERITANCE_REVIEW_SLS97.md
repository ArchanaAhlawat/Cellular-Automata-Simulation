### Part 1

* Encapsulation -

There is a class that connects the frontend to the backend. The cell is not cognizant of its location within the overall matrix of cells - this is managed by another class. 

* Inheritance Hierarchies
Cells are made using inheritance - for different games, different subclasses of cells are used. For example, for the Predator-Prey game, an AnimalCell class has two subclasses - Fish and Shark. Inheritance can also be for the rules class to establish different rules for different games. 

*  Closed vs. Open
The fundamental code driving each game is closed from modification. However, these rules are called in such a way as to enable new rules to quickly be implemented and conform with the existing code structure. The exact values of special parameters remain open to change, allowing for user modification in the GUI or in the root XML file. 

* What exceptions
Check for valid file, ensure that the grid size is within certain bounds, prevent movement of cells to other locations that are filled. 

* Why is design good

The design is good because the inheritance structure of the Cell and Rules allows for quick extension to new games. So long as the fundamental logic is dependent on any game specific parameters, new subclasses can be made to address this game-specific behavior and integrate it in the broader framework of the code. 

### Part 2
How is your area linked to/dependent on other areas of the project?

* Linked to other areas
The frontend is connected to a class that manages cell position. This class is also the primary method to communicate with backend code to update cell states and behavior. The specific Rules and Cell subclasses are dependent on the XML Parser, as the XML determines which instance of a subclass to construct. The Cell contains public methods to allow the Rules to get and process certain values of the Cell. 

* Dependencies on other behavior or implementation
 The Rules class is the most difficult to not make dependent on other behavior. That is, it is most difficult to define the rules in such a way that they don’t use many *if* statements to cater to each type of game. Trying to remove the dependency is still a work in progress.




* How to minimize dependencies
To minimize dependencies of the Rules on the specific game, the general structure of applying Rules never changes. To actually implement new Rules, the Rules class is given public access to certain Cell variables, such as the nextState of the Cell. This allows the Rules to tell the Cell what it needs to become and how it needs to move - then the Rules forgets. Later, when Cell states are actually changing, the CellManager class simply lets the Cell become its new state, and moves the Cell according to whether it should be move adjacent or randomly. Certainly, adjacent or random movements don’t generalize to specific Rules for how to move in more complicated scenarios. If that becomes a requirement, it may be beneficial to implement a new Class that handles all movement based on game-specific behavior. 

* Go over one pair of super/sub classes in detail to see if there is room for improvement. 
Currently the Rules class is implemented with inheritance. It possess a general applyRules method that each Cell calls. This method is modified in every subclass so that game-specific rule application can occur. For example, for the PredatorPreyGame, the applyRules first checks the state of the Cell. If it’s a Fish, it applies Rules for fish. If it’s a shark, it applies Rules for sharks. Since the Rules directly updates Cell variables, there is no need for a return statement, so every applyRules call can perform whatever function needed without constraints. 



###Part 3
*Come up with at least five use cases for your part (most likely these will be useful for both teams).

My part is the Rules. For the PredatorPrey Game the Rules checks first to see if the Cell should reproduce. This can only happen if the neighboring Cells have at least one that is empty. Reproducing happens first in the Rules check. Then the Cell moves if possible, or in the case of a Shark, eats a fish. 

In the Segregation Game, the Rules just checks how many neighbors are of a certain type. If there are too few, then the Cell is instructed to move randomly to a new spot next time. Otherwise, it will stay. The movement is handled by the CellManager Class. 

Similarly, for the Fire game, quick checks are done to get the current Cell state. If it is fire, then a random number is generated to see if the fire should spread. The tree that the fire spreads to has its nextState updated to “fire.”

For the GameOfLife, the Rules class is given a map of all the special values for staying alive or dying. Then the neighbors of each Cell are checked to see their statuses. The special values are used to determine if the current Cell should be alive or be dead. The Cell’s nextState is accordingly updated. 

*What feature/design problem are you most excited to work on?

I am most excited about figuring out how to generalize the application of the Rules once new sprints are revealed. I think the new sprints will likely reveal flaws in the current logic. I claim that the current code structure allows new games to be implemented quickly, but I am sure that I haven’t thought through every possible challenge, so it will be interesting to see how my code stands up to the new sprints. 

* What feature/design problem are you most worried about working on?
I am most worried about how the different group members’ code will interact. It is very difficult to all get on the same page for every aspect of the Game. I think communication issues are a big barrier to writing the code. 


