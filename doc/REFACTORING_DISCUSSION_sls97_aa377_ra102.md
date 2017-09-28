### GENERAL

+ Convert Cell from passive to active class that contains logic for modification of its own state, instead of delegating all logic to Rules class


#### SAM 
+ Implement helper classes to control cell motion and cell specific behavior - allows for quick extension of existing code to new games. Rather than having individual classes for each Cell type, each simulation will instead have one subclass cell that has a *state* variable. This variable is the key in a HashMap to different Objects that implement the corresponding Cell's functionality. This was useful to eliminate the need to destroy or make Cell Objects and change Cell positions in the grid. Rather, Cells do not move, but *state* values change, allowing for different functionality to occur. If this *state* to function HashMap were not used, there would have to be many *if* statements in every Cell subclass.

+ Created new class to handle user changes to game-wide variables. Added code in Cell's compute state method to check for new user-modified variables and update accordingly. As a team, have now decided to make a new class to maintain current parameter values. This is analogous to a new class that will maintain the default values. 

+ Created new class to maintain the default values of all game-specific variables. Cells no longer contain all default info, but rather get the default info from this new class.

+ 



#### ARCHANA




#### ADI
+ Extract all control-panel related display logic from SimulationDisplay into PanelDisplay for modularity and ease of reading
  
+ Extract all non-display related, event-handler etc. logic of GUI (like what happens when a button is clicked it) from SimulationDisplay into UIActionDispatcher  
  
+ Extract all image-manipulation logic from SimulationDisplay into UIImageUtils  
  
+ Pass reference to UITextReader object in construction of scene by SimulationDisplay to avoid verbose unwrapping of string values from UITextReader  
  
+ TODO for extension - inheritance hierarchy based on PanelDisplay / SimulationDisplay superclass and possibly new SimulationTile super-class with game-specific sub-class in place of raw TilePane class to represent a tile in the grid

  




