### **Overview**
The current project creates the model for an adventure game in
which a player can walk around a dungeon and collect treasure.
This model can be paired with other controller to perform the 
operations that the model allows. The model creates a dungeon
based on user input and can move the player in any direction in
the dungeon. The model also assigns the Start and End locations
and the game ends when the player reaches the end location.

### **List of features**
- The dungeon created by this model is such that there exists
at least one path between each location in the dungeon.
- The dungeon contains caves as well as tunnels which the player
can use to move around.
- The model allows the player to move in any direction if movement
is possible. If the player chooses to move in the direction
which is invalid (a wall exists in the direction), the model notifies
the player about the same.
- The model assigns a particular percentage of caves to contain
treasure. This percentage can be input by the user when creating
the dungeon.
- The player can move around the dungeon and collect treasure. 
Once the treasure from a particular cave is collected that treasure 
is alloted to the player.
- The model can print the state of the dungeon and players 
postion and description after each move which can be used to 
generate a visual representation of the game.
- The model gives the possible next moves a player can take 
from current location.

### **How To Run**
In Command prompt/Terminal, navigate to the /res folder and run the 
command: 
```
java -jar Project3-Dungeon.jar <rowcount> <columncount> <interconnectivity> <IsWrapping(Y/N)> <% of caves with treasure>
```

### **How to Use the Program**
In order to start the game, a dungeon needs to be created using 
the command line for dimensions and other properties of the 
dungeon. Once the dungeon is created, the Player can be created 
and added to the Dungeon. The moves for the player can be input
by the user to move the player around the dungeon and perform 
actions like picking up treasure. The game ends when the player
reaches the End location in the dungeon.

### **Description of Examples**
Run1-NonWrap.txt (Non-Wrapping dungeon example)
Run2-Wrap.txt (Wrapping dungeon example)

- On creating a Wrapping Dungeon and printing its initial State,
we get the following output:
```
----------------------------------------------------
Dungeon Initial State: 
0--0--0--0--0--
   |     |  |  
0--S  E  0  0  
|  |  |  |  |  
0--0  0  0--0  
|     |  |     
0--0  0--0--0--
|     |     |  
0  0--0--0  0  
   |  |  |  |  
Start Location: 11
End Location: 12
----------------------------------------------------
```
For a non-wrapping dungeon, the output looks as follows:
```
----------------------------------------------------
Dungeon Initial State:
0--S--0--0--0
|  |     |  |
0--0--0--0--0
   |
0--0--0--0--0
|        |  |
0--0--E--0--0
|     |
0--0--0--0--0

Start Location: 01
End Location: 32
----------------------------------------------------

```

- On adding the Player in the Dungeon, the output looks like:
```
----------------------------------------------------
0--P--0--0--0
|  |     |  |
0--0--0--0--0
   |
0--0--0--0--0
|        |  |
0--0--E--0--0
|     |
0--0--0--0--0

Current location details:
Type: Cave
Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0
Next possible moves: East South West

Current player details:
Location: 01
Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0
----------------------------------------------------
```

- The player can be moved to any neighbouring locations. The
output in such case will look like:
```
----------------------------------------------------
Move West- Player moved to Location 00
----------------------------------------------------
P--S--0--0--0
|  |     |  |
0--0--0--0--0
   |
0--0--0--0--0
|        |  |
0--0--E--0--0
|     |
0--0--0--0--0

Current location details:
Type: Tunnel
Next possible moves: East South

Current player details:
Location: 00
Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0
----------------------------------------------------
```
- If the player is moved in a direction where a wall exists. 
The output for the same looks as follows:
```
----------------------------------------------------
Move South- Invalid move.
----------------------------------------------------
0--S--0--0--0
|  |     |  |
0--0--0--0--0
   |
0--0--0--0--0
|        |  |
0--0--E--0--0
|     |
0--0--P--0--0

Current location details:
Type: Cave
Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0
Next possible moves: North East West

Current player details:
Location: 42
Treasure: Diamonds- 20, Sapphires- 18, Rubies- 5
----------------------------------------------------
```
- A player can also collect the Treasure in a particular cave.
If the player collects the treasure in a cave, the output
looks as follows:
```
----------------------------------------------------
Move West- Player moved to Location 11
----------------------------------------------------
0--S--0--0--0
|  |     |  |
0--P--0--0--0
   |
0--0--0--0--0
|        |  |
0--0--E--0--0
|     |
0--0--0--0--0

Current location details:
Type: Cave
Treasure: Diamonds- 6, Sapphires- 5, Rubies- 0
Next possible moves: North East South West

Current player details:
Location: 11
Treasure: Diamonds- 11, Sapphires- 13, Rubies- 5
----------------------------------------------------
Move West- Player moved to Location 10
----------------------------------------------------
```

- Once the player reaches the End location, the following message
is printed:
```
----------------------------------------------------
Move North- Player moved to Location 32
----------------------------------------------------
0--S--0--0--0
|  |     |  |
0--0--0--0--0
   |
0--0--0--0--0
|        |  |
0--0--P--0--0
|     |
0--0--0--0--0

Current location details:
Type: Cave
Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0

Current player details:
Location: 32
Treasure: Diamonds- 20, Sapphires- 18, Rubies- 5
----------------------------------------------------
Player reached the End location.
```

### **Design Changes**
- A new Game model class has been added to maintain the state of
game at all times.
- The earlier design of the model considered a single method for 
creating a Dungeon. This has now been divided into multiple private 
methods that perform different operations in Dungeon creation 
process.
- The functionality to move the player has now been moved
to the Game Model as it is able to maintain the state of all
the variables of the game in the same place. 
- A new class Edge is added to help in the building of dungeon and to maintain the path that
the player can use to travel in the dungeon. This class replaces 
the Grid type that was to be used earlier.


### **Assumptions**
The model assumes few details in order to make the working less complicated. Few such assumptions 
are as follows:

- The command line inputs for Dungeon dimensions and properties will
always be non-negative integer values and boolean value for wrap
condition.
- The treasure amount assigned to a cave is randomly decided.
- The dungeon of dimensions less than 3X3 cannot be created.
- The game ends once the player reaches the End location.
- Start and End location can be any cave within the dungeon
provided the minimum distance between them is 5 or more.
- The player can move only 1 step in any direction at a time 
provided the move is valid.
- If a player tries to move in a direction where a wall exists,
the player shall remain in the same location as before.
- The player can enter the Dungeon only at the predefined Start location.
- If the players picks the treasure in any cave, the entire treasure
of the cave is picked all together and assigned to the player.
- If treasure is picked from any cave, it is not replenished.
- The player can pick any amount of treasure.
- There are only 3 types of treasure available in any cave of the
dungeon.
- The treasure picked by a player in a particular cave is shown 
in the same step when the player moves into the cave.


### **Limitations**
The program has a few limitations that could be worked on in future versions:

- The player can move from Start location to End location 
without collecting any treasure, thereby making the purpose of
having treasure non-essential.
- The model cannot determine or suggest the next steps that a 
player should take to reach the End in least possible steps. 


### **Citations**
Nil.