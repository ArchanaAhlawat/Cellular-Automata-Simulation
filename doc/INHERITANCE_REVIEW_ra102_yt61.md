## Part 1
* What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?  
  
  How the Cell is being displayed - The Cell class does not know anything about how it is displayed, nor do any of the other classes.  
  Instead, the FrontEnd class maps the (simulation type, state) combination to a specific Image URL from which an ImageView is constructed to display.
  

* What inheritance hierarchies are you intending to build within your area and what behavior are they based around?  
  
  Inheritance of style and display functionality for certain common layouts
  
* What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?  
  
  The part about adding new visualizations for new cell types is open  
    
  The part about how user actions on existing GUI items are handled is closed
  
   
* What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?  
  
  Upload of file name that doesn't exist  
    
  Badly formatted XML File  
    
  These are handled by being caught by the frontend class and returning an error dialog with an appropriate string read from resource folder
  
* Why do you think your design is good (also define what your measure of good is)?  
  
  Abstraction of display-related logic from all other classes  
    
  Minimal repetition of logic - modularity  
    
  Extensibility - Easy support of new simulation types or states by simply adding mappings of state names to image url strings

## Part 2
* How is your area linked to/dependent on other areas of the project?  
  
  Dependent on Initializer & CellManager to return current states of grid  
    
  Dependent on rules to override config through user actions  
    
  
* Are these dependencies based on the other class's behavior or implementation?  
  
  No, they are only based on a common interface and general abstractions  
  
* How can you minimize these dependencies?  
  
  Function calls with dependencies passed as parameters rather than through back-channels like shared instance variables 
    
    
* Go over one pair of super/sub classes in detail to see if there is room for improvement.   
  
  Panel classes - only visual elements (style, etc) are being extended. Could perhaps extend behaviors too
  
* Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).

  Common : display attributes  
    
  Different : specific behaviors


## Part 3
* Come up with at least five use cases for your part (most likely these will be useful for both teams).  
  
  Display of grid  
    
  Display of current simulation name and state (num cycles)  
    
  User interface to change config / simulation  
    
  User interface to speed up / slow down simulation  
    
  Display of states  
    
     
* What feature/design problem are you most excited to work on?  
  
  Event-handlers for user actions such as file-upload, slow-down simulation, pause simulation, etc  
  
* What feature/design problem are you most worried about working on?  
  
  Inheritance - I don't see a very clear case for inheritance in the Front-End, my part.