package dungeon;

import randoms.Randomizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents the MasterDungeon that is created for the player to move and collect treasure.
 * This dungeon contains caves and tunnels using which the player can move from start position
 * to end position. Both the start and end positions are caves in the tunnels. The dungeon is
 * created based on the dimensions given by the user and the paths are created based on the
 * minimum spanning tree created using Kruskals algorithm and degree of interconnectivity.
 * The dungeons can be wrapping or non wrapping as well. The class is kept package private as it
 * will be used only within the dungeon model package.
 */
class MasterDungeon implements Dungeon {
  private final int rowCount;
  private final int colCount;
  private final int degOfInterconnectivity;
  private final boolean isWrap;
  private final int percentCavesForTreasure;
  private Randomizer r;
  private Edge e;
  private List<Edge> updatedEdges = new ArrayList<>();
  private List<Edge> interconnectivityEdges = new ArrayList<>();
  private List<Edge> edges = new ArrayList<>();
  private List<Set> listOfSets = new ArrayList();
  private List<Cave> caves = new ArrayList<>();
  private String startLocation = "";
  private String endLocation = "";

  /**
   * Creates an instance of a dungeon that the player can enter and play.
   *
   * @param rowCount                the no. of rows in the dungeon grid
   * @param colCount                the no. of columns in the dungeon grid
   * @param degOfInterconnectivity  the no of paths that can be added to the kruskals output minimum
   *                                spanning tree.
   * @param isWrap                  true if the dungeon has paths wrapping type
   * @param percentCavesForTreasure the percentage of caves to which treasure is to be assigned
   * @param r                       the randomizer object
   */
  MasterDungeon(int rowCount, int colCount, int degOfInterconnectivity, boolean isWrap,
                int percentCavesForTreasure, Randomizer r) {
    if (rowCount < 3 || colCount < 3) {
      throw new IllegalArgumentException("Minimum dimension of the dungeon should be 3X3.");
    }
    if (degOfInterconnectivity < 0
            || !isDegOfInterconnectivityValid(degOfInterconnectivity, rowCount, colCount, isWrap)) {
      throw new IllegalArgumentException("Degree of interconnectivity is Invalid.");
    }
    if (percentCavesForTreasure < 0) {
      throw new IllegalArgumentException("Percentage of caves to get treasure cannot be negative.");
    }
    this.rowCount = rowCount;
    this.colCount = colCount;
    this.degOfInterconnectivity = degOfInterconnectivity;
    this.isWrap = isWrap;
    this.percentCavesForTreasure = percentCavesForTreasure;
    this.r = r;
    createDungeon();
  }

  private boolean isDegOfInterconnectivityValid(int degOfInterconnectivity, int rows, int cols,
                                                boolean isWrap) {
    int max;
    if (isWrap) {
      max = (rows * cols * 2) - ((rows * cols) - 1);
    } else {
      max = ((rows * cols * 2) - rows - cols) - ((rows * cols) - 1);
    }
    return degOfInterconnectivity <= max;
  }

  private void createDungeon() {
    //initialize all the edges in the maze
    initializeAllEdges();

    //create maze using kruskals algorithm
    createKruskalsMaze();

    //use degree of interconnectivity to added additional traversal paths to the maze
    applyInterconnectivity();

    //create the caves in the dungeon
    createCaves();

    //set the Start and End Locations
    setTerminals();

    //assign treasure to the specified percentage of caves
    assignTreasure();
  }

  private void initializeAllEdges() {
    //check if it is wrapping type dungeon or not, accordingly create the edges.
    //creating edges for wrapping dungeon
    if (isWrap) {
      for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount; j++) {
          if (j == colCount - 1) {
            e = new DungeonEdge("" + i + j, "" + i + "0");
          } else {
            e = new DungeonEdge("" + i + j, "" + i + (j + 1));
          }
          edges.add(e);
        }
      }
      for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount; j++) {
          if (i == rowCount - 1) {
            e = new DungeonEdge("" + i + j, "" + "0" + j);
          } else {
            e = new DungeonEdge("" + i + j, "" + (i + 1) + j);
          }
          edges.add(e);
        }
      }
    }
    //creating edges for non-wrapping dungeon
    else {
      for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount - 1; j++) {
          e = new DungeonEdge("" + i + j, "" + i + (j + 1));
          edges.add(e);
        }
      }
      for (int i = 0; i < rowCount - 1; i++) {
        for (int j = 0; j < colCount; j++) {
          e = new DungeonEdge("" + i + j, "" + (i + 1) + j);
          edges.add(e);
        }
      }
    }
    updatedEdges = edges;
  }

  private void createKruskalsMaze() {
    updatedEdges = new ArrayList<>();
    //create sets for each node 00-ij
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < colCount; j++) {
        Set<String> a = new HashSet<String>();
        a.add("" + i + j);
        listOfSets.add(a);
      }
    }
    //for all edges in the edges list
    while (edges.size() > 0) {
      boolean skipMerging = false;
      Set<String> mergedSet = new HashSet<String>();
      //select a random edge from edges list
      int r_index = r.getRandomInt(0, edges.size());
      String p1 = edges.get(r_index).getP1();
      String p2 = edges.get(r_index).getP2();
      //check if p1 and p2 are in same set
      for (Set<String> s : listOfSets) {
        //if yes, then add the edge to interconnectivity list
        if (s.contains(p1) && s.contains(p2)) {
          interconnectivityEdges.add(edges.get(r_index));
          skipMerging = true;
        }
        //if no, then merge the sets containing p1 and p2
        else {
          if (s.contains(p1)) {
            mergedSet.addAll(s);
          }
          if (s.contains(p2)) {
            mergedSet.addAll(s);
          }
        }
      }
      if (!skipMerging) {
        //remove the original sets from the list
        List<Set> l_copy = new ArrayList();
        for (Set<String> s : listOfSets) {
          l_copy.add(s);
        }
        for (Set<String> s : l_copy) {
          if (s.contains(p1)) {
            listOfSets.remove(s);
          }
          if (s.contains(p2)) {
            listOfSets.remove(s);
          }
        }
        //add the new merged set to the list
        listOfSets.add(mergedSet);
        updatedEdges.add(edges.get(r_index));
      }
      //remove the edge from edges list
      edges.remove(r_index);
    }
  }

  private void applyInterconnectivity() {
    for (int i = 0; i < degOfInterconnectivity; i++) {
      int index = r.getRandomInt(0, interconnectivityEdges.size());
      updatedEdges.add(interconnectivityEdges.get(index));
      interconnectivityEdges.remove(index);
    }
  }

  private void createCaves() {
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < colCount; j++) {
        boolean northOpen = false;
        boolean southOpen = false;
        boolean westOpen = false;
        boolean eastOpen = false;
        for (Edge e : updatedEdges) {
          //open the doors for available path (when current location is p1 vertex of edge)
          if (e.getP1().equals("" + i + j)) {
            if (e.getP2().equals("" + (i - 1) + j)) {
              northOpen = true;
            }
            if (e.getP2().equals("" + i + (j + 1))) {
              eastOpen = true;
            }
            if (e.getP2().equals("" + (i + 1) + j)) {
              southOpen = true;
            }
            if (e.getP2().equals("" + i + (j - 1))) {
              westOpen = true;
            }
          }
          //open the doors for available path (when current location is p2 vertex of edge)
          if (e.getP2().equals("" + i + j)) {
            if (e.getP1().equals("" + (i - 1) + j)) {
              northOpen = true;
            }
            if (e.getP1().equals("" + i + (j + 1))) {
              eastOpen = true;
            }
            if (e.getP1().equals("" + (i + 1) + j)) {
              southOpen = true;
            }
            if (e.getP1().equals("" + i + (j - 1))) {
              westOpen = true;
            }
          }
          //for last row and last column edges (wrapping special case)
          if (isWrap && e.getP1().equals("" + i + j) && ((j == colCount - 1)
                  || (i == rowCount - 1))) {
            if (e.getP2().equals("" + i + "0")) {
              eastOpen = true;
            }
            if (e.getP2().equals("" + "0" + j)) {
              southOpen = true;
            }
            if (e.getP2().equals("" + (i - 1) + j)) {
              northOpen = true;
            }
            if (e.getP2().equals("" + i + (j - 1))) {
              westOpen = true;
            }
          }
          if (isWrap && e.getP2().equals("" + i + j) && ((j == colCount - 1)
                  || (i == rowCount - 1))) {
            if (e.getP1().equals("" + i + "0")) {
              eastOpen = true;
            }
            if (e.getP1().equals("" + "0" + j)) {
              southOpen = true;
            }
            if (e.getP1().equals("" + (i - 1) + j)) {
              northOpen = true;
            }
            if (e.getP1().equals("" + i + (j - 1))) {
              westOpen = true;
            }
          }
          //for first row and first column edges(wrapping special case)
          if (isWrap && e.getP1().equals("" + i + j) && ((j == 0) || (i == 0))) {
            if (e.getP2().equals("" + i + (j + 1))) {
              eastOpen = true;
            }
            if (e.getP2().equals("" + (i + 1) + j)) {
              southOpen = true;
            }
            if (e.getP2().equals("" + (rowCount - 1) + j)) {
              northOpen = true;
            }
            if (e.getP2().equals("" + i + (colCount - 1))) {
              westOpen = true;
            }
          }
          if (isWrap && e.getP2().equals("" + i + j) && ((j == 0) || (i == 0))) {
            if (e.getP1().equals("" + i + (j + 1))) {
              eastOpen = true;
            }
            if (e.getP1().equals("" + (i + 1) + j)) {
              southOpen = true;
            }
            if (e.getP1().equals("" + (rowCount - 1) + j)) {
              northOpen = true;
            }
            if (e.getP1().equals("" + i + (colCount - 1))) {
              westOpen = true;
            }
          }
        }
        Cave c = new Cave("" + i + j, northOpen, southOpen, eastOpen, westOpen);
        caves.add(c);
      }
    }
  }

  private void setTerminals() {
    int caveIndex = 0;
    int randomLoopCounter = 0;
    boolean terminalsFound = false;
    String startLocation = "";
    String endLocation = "";
    while (!terminalsFound && randomLoopCounter < 50) {
      caveIndex = r.getRandomInt(0, caves.size());
      randomLoopCounter++;
      if (!caves.get(caveIndex).isTunnel()) {
        //set start location
        startLocation = caves.get(caveIndex).getLocation();
        //for all other locations find shortest distance from start location
        for (Cave c : caves) {
          if (!c.isTunnel()) {
            //select an end location
            endLocation = c.getLocation();
            //check that start location is not same as end location
            if (!endLocation.equals(startLocation)) {
              //find shortest path from start to end location
              int movementCounter = bfs(startLocation, endLocation);
              //if shortest path >= 5, then select this end location
              if (movementCounter >= 5) {
                terminalsFound = true;
                break;
              }
            }
          }
        }
      }
    }
    if (terminalsFound) {
      this.startLocation = startLocation;
      this.endLocation = endLocation;
    } else {
      throw new IllegalStateException("No path of length 5 or more can be constructed between "
              + "any of the existing cave locations.");
    }
  }

  private int bfs(String startLocation, String endLocation) {
    //create queue to store node and distance required to reach the node
    Map<String, Integer> bfsQueue = new LinkedHashMap<String, Integer>();

    //create visited hashmap to store the visited nodes
    List<String> visited = new ArrayList<>();

    //add the start node to queue with distance 0
    bfsQueue.put(startLocation, 0);

    //do this while queue is not empty
    while (!bfsQueue.isEmpty()) {
      //get the location of first element in the queue
      String currLoc = bfsQueue.keySet().toArray()[0].toString();

      //get the distance of current node from start location
      int currDistance = bfsQueue.get(currLoc);

      //add the current node to visited list
      visited.add(currLoc);

      //remove the current node from the queue
      bfsQueue.remove(currLoc);

      //find the next locations to traverse from current node
      for (Edge e : updatedEdges) {
        //if current node is same as p1 vertex of edge, and p2 vertex is not yet visited,
        // and p2 vertex is not already added in the queue
        if (e.getP1().equals(currLoc) && !visited.contains(e.getP2())
                && !bfsQueue.containsKey(e.getP2())) {
          //add the p2 vertex to the queue with new distance
          bfsQueue.put(e.getP2(), currDistance + 1);
          //if p2 vertex is the endLocation , then return the current distance required to reach p2
          if (e.getP2().equals(endLocation)) {
            return currDistance + 1;
          }
        }
        //if current node is same as p2 vertex of edge, and p1 vertex is not yet visited,
        // and p1 vertex is not already added in the queue
        if (e.getP2().equals(currLoc) && !visited.contains(e.getP1())
                && !bfsQueue.containsKey(e.getP1())) {
          //add the p1 vertex to the queue with new distance
          bfsQueue.put(e.getP1(), currDistance + 1);
          //if p1 vertex is the endLocation , then return the current distance required to reach p1
          if (e.getP1().equals(endLocation)) {
            return currDistance + 1;
          }
        }
      } //end of for loop
    } //end of while loop
    //if the endlocation was not found then return -1
    return -1;
  }

  private void assignTreasure() {
    //throw exception if input percentage is negative
    if (percentCavesForTreasure < 0 || percentCavesForTreasure > 100) {
      throw new IllegalArgumentException("Please enter a valid percentage(Range: 0-100) of caves "
              + "for which treasure is to be assigned");
    }
    //create a copy of the caves list
    List<Cave> cavesCopy = new ArrayList<>();
    for (Cave c : caves) {
      cavesCopy.add(c);
    }
    //calculate the no of caves to which treasure will be assigned
    int treasureCaveCounter = Math.round(percentCavesForTreasure * caveCounter() / 100);
    //check that no of caves is greater than 0
    if (treasureCaveCounter > 0) {
      //while no of caves pending to be assigned treasure is not 0, do the following
      while (treasureCaveCounter != 0) {
        //select a random cave index
        int caveIndex = r.getRandomInt(0, caves.size());
        //check if the cave is not a tunnel and has not been assigned treasure before
        if (!caves.get(caveIndex).isTunnel() && cavesCopy.contains(caves.get(caveIndex))) {
          //assigne treaasure to the cave
          caves.get(caveIndex).assignInitialTreasure();
          //remove the cave from cave copy list to ensure that it is not selected again
          cavesCopy.remove(caves.get(caveIndex));
          //decrease the counter for no of caves pending to be assigned treasure
          treasureCaveCounter--;
        }
      }
    } else {
      throw new IllegalArgumentException("Percentage too low. Please enter a higher percentage "
              + "of caves for which treasure is to be assigned");
    }
  }

  private int caveCounter() {
    int counter = 0;
    for (Cave c : caves) {
      if (!c.isTunnel()) {
        counter++;
      }
    }
    return counter;
  }

  @Override
  public void addPlayer(Player p) {
    if (p == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    p.setLocation(startLocation);
  }

  @Override
  public String move(Player p, String direction) {
    if (p == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    String[] s = p.getLocation().split("");
    int i = Integer.parseInt(s[0]);
    int j = Integer.parseInt(s[1]);
    String nextLoc = "";
    boolean invalidMove = true;
    for (Cave c : caves) {
      if (c.getLocation().equals(p.getLocation())) {
        if (direction.equals("North") && c.isMoveNorth()) {
          invalidMove = false;
          if (isWrap && i == 0) {
            i = rowCount;
          }
          nextLoc = "" + (i - 1) + j;
        } else if (direction.equals("South") && c.isMoveSouth()) {
          invalidMove = false;
          if (isWrap && i == rowCount - 1) {
            i = -1;
          }
          nextLoc = "" + (i + 1) + j;
        } else if (direction.equals("West") && c.isMoveWest()) {
          invalidMove = false;
          if (isWrap && j == 0) {
            j = colCount;
          }
          nextLoc = "" + i + (j - 1);
        } else if (direction.equals("East") && c.isMoveEast()) {
          invalidMove = false;
          if (isWrap && j == colCount - 1) {
            j = -1;
          }
          nextLoc = "" + i + (j + 1);
        }
      }
    }
    if (invalidMove) {
      return "Invalid move.";
    } else {
      p.setLocation(nextLoc);
      return "Player moved to Location " + p.getLocation();
    }
  }

  @Override
  public void pickTreasure(Player p) {
    if (p == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    for (Cave c : caves) {
      if (c.getLocation().equals(p.getLocation())) {
        p.addTreasure(c.getDiamondCount(), c.getSapphireCount(), c.getRubyCount());
        c.updatePickedTreasureStatus();
      }
    }
  }

  @Override
  public List<Edge> getEdges() {
    return updatedEdges;
  }

  @Override
  public String getStartLocation() {
    return startLocation;
  }

  @Override
  public String getEndLocation() {
    return endLocation;
  }

  @Override
  public String getLocationType(String loc) {
    if (loc == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    for (Cave c : caves) {
      if (c.getLocation().equals(loc)) {
        if (c.isTunnel()) {
          return "Tunnel";
        } else {
          return "Cave";
        }
      }
    }
    return "Location Not Found. Please try entering a valid location";
  }

  @Override
  public String getLocationTreasure(String loc) {
    if (loc == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    for (Cave c : caves) {
      if (c.getLocation().equals(loc)) {
        return "Diamonds- " + c.getDiamondCount() + ", Sapphires- " + c.getSapphireCount()
                + ", Rubies- " + c.getRubyCount();
      }
    }
    return "Treasure details Not Found. Please try entering a valid location";
  }

  @Override
  public String getNextPossibleMoves(String loc) {
    String moves = "";
    for (Cave c : caves) {
      if (c.getLocation().equals(loc)) {
        if (c.isMoveNorth()) {
          moves += "North ";
        }
        if (c.isMoveEast()) {
          moves += "East ";
        }
        if (c.isMoveSouth()) {
          moves += "South ";
        }
        if (c.isMoveWest()) {
          moves += "West ";
        }
      }
    }
    return moves;
  }
}
