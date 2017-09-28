### GENERAL

+ Convert Cell from passive to active class that contains logic for modification of its own state, instead of delegating all logic to Rules class


#### SAM 


#### ARCHANA




#### ADI
+ Extract all control-panel related display logic from SimulationDisplay into PanelDisplay for modularity and ease of reading
  
+ Extract all non-display related, event-handler etc. logic of GUI (like what happens when a button is clicked it) from SimulationDisplay into UIActionDispatcher  
  
+ Extract all image-manipulation logic from SimulationDisplay into UIImageUtils  
  
+ Pass reference to UITextReader object in construction of scene by SimulationDisplay to avoid verbose unwrapping of string values from UITextReader  
  
+ TODO for extension - inheritance hierarchy based on PanelDisplay / SimulationDisplay superclass and possibly new SimulationTile super-class with game-specific sub-class in place of raw TilePane class to represent a tile in the grid

  




