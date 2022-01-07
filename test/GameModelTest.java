import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dungeon.DungeonGame;
import dungeon.GameModel;
import randoms.RandomGeneratorDummy;
import randoms.Randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the Game Model.
 */
public class GameModelTest {

  @Test
  public void testWrapDungeon() {
    Randomizer r = new RandomGeneratorDummy();
    List<Integer> randomValues = new ArrayList<>();
    int[] kruskals = {17, 34, 2, 3, 0, 14, 40, 42, 17, 24, 36, 14, 12, 24, 27, 24, 10, 20, 6, 1,
                      23, 19, 5, 4, 21, 22, 9, 22, 18, 10, 18, 7, 9, 8, 7, 7, 6, 10, 8, 4, 9, 0,
                      6, 2, 4, 4, 0, 0, 0, 0};
    int[] interconnectivity = {5, 13, 4, 17, 0, 12, 12, 15};
    int[] terminal = {24, 6};

    for (int i = 0; i < kruskals.length; i++) {
      randomValues.add(kruskals[i]);
    }
    for (int i = 0; i < interconnectivity.length; i++) {
      randomValues.add(interconnectivity[i]);
    }
    for (int i = 0; i < terminal.length; i++) {
      randomValues.add(terminal[i]);
    }
    int[] treasure = {21, 21, 9, 13, 22, 13, 4, 15, 12, 5, 13, 1, 20, 22, 13, 14, 20, 23, 20, 21,
                      17};
    for (int i = 0; i < treasure.length; i++) {
      randomValues.add(treasure[i]);
    }
    r.setDummyRandomValue(randomValues);
    GameModel g = new DungeonGame(5, 5, 8,
            true, 40, r);
    assertEquals("11", g.getStartLocation());
    assertEquals("12", g.getEndLocation());
    //bfsHelper method returns distance between the caves
    int result = bfsHelper(g.getStartLocation(), g.getEndLocation(), g.getEdges());
    assertTrue(result >= 5);
    g.createPlayer();
    g.addPlayerInDungeon();
    assertEquals(g.getPlayerLocation(), g.getStartLocation());
    String playerDesc = "\nCurrent player details: \n" + "Location: 11\n"
            + "Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0";
    assertEquals(playerDesc, g.getPlayerDescription());
    String playerLocDesc = "Current location details:\nType: Cave\nTreasure: Diamonds- 0,"
            + " Sapphires- 0, Rubies- 0\nNext possible moves: North South West ";
    assertEquals(playerLocDesc, g.getPlayerLocationDescription());
    String[] directions = {"North", "East", "South", "West"};
    int moveCounter = 0;
    int[] moves = {4, 3, 2, 4, 3, 2, 4, 3, 1, 4, 3, 1, 4, 4, 3, 2, 4, 4, 3, 4, 2, 2, 2, 2, 3, 3,
                   4, 1, 3, 3, 3, 4, 1, 1};
    assertTrue(g.getPlayerDescription().contains("Diamonds- 0, Sapphires- 0, Rubies- 0"));
    while (!g.getPlayerLocation().equals(g.getEndLocation())) {
      String moveResult = g.movePlayerTo(directions[moves[moveCounter] - 1]);
      moveCounter++;
      g.pickTreasureAtLocation();
    }
    assertEquals(g.getPlayerLocation(), g.getEndLocation());
  }

  @Test
  public void testNonWrapDungeon() {
    Randomizer r = new RandomGeneratorDummy();
    List<Integer> randomValues = new ArrayList<>();
    int[] kruskals = {20, 6, 7, 11, 17, 15, 29, 27, 5, 3, 6, 5, 7, 24, 6, 11, 17, 13, 9, 0, 18,
                      5, 1, 16, 5, 8, 6, 3, 6, 5, 0, 1, 1, 3, 4, 2, 2, 0, 0, 0};
    int[] interconnectivity = {8, 9, 6, 10, 0, 5};
    int[] terminal = {19, 1};

    for (int i = 0; i < kruskals.length; i++) {
      randomValues.add(kruskals[i]);
    }
    for (int i = 0; i < interconnectivity.length; i++) {
      randomValues.add(interconnectivity[i]);
    }
    for (int i = 0; i < terminal.length; i++) {
      randomValues.add(terminal[i]);
    }
    int[] treasure = {14, 8, 22, 3, 6};
    for (int i = 0; i < treasure.length; i++) {
      randomValues.add(treasure[i]);
    }
    r.setDummyRandomValue(randomValues);
    GameModel g = new DungeonGame(5, 5, 6,
            false, 40, r);
    assertEquals("01", g.getStartLocation());
    assertEquals("32", g.getEndLocation());
    //bfsHelper method returns distance between the caves
    int result = bfsHelper(g.getStartLocation(), g.getEndLocation(), g.getEdges());
    assertTrue(result >= 5);
    g.createPlayer();
    g.addPlayerInDungeon();
    assertEquals(g.getPlayerLocation(), g.getStartLocation());
    String playerDesc = "\nCurrent player details: \n" + "Location: 01\n"
            + "Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0";
    assertEquals(playerDesc, g.getPlayerDescription());
    String playerLocDesc = "Current location details:\n" + "Type: Cave\nTreasure: Diamonds- 0,"
            + " Sapphires- 0, Rubies- 0\n" + "Next possible moves: East South West ";
    assertEquals(playerLocDesc, g.getPlayerLocationDescription());
    String[] directions = {"North", "East", "South", "West"};
    int moveCounter = 0;
    int[] moves = {4, 2, 2, 2, 2, 3, 4, 4, 4, 4, 2, 3, 2, 2, 2, 3, 4, 1, 4, 4, 4, 3, 2, 4, 3, 2,
                   2, 2, 2, 4, 4, 3, 1};
    assertTrue(g.getPlayerDescription().contains("Diamonds- 0, Sapphires- 0, Rubies- 0"));

    Set<String> allCaves = new HashSet<>();
    Set<String> treasureCaves = new HashSet<>();

    while (!g.getPlayerLocation().equals(g.getEndLocation())) {
      String moveResult = g.movePlayerTo(directions[moves[moveCounter] - 1]);
      if (g.getPlayerLocationDescription().contains("Type: Cave")) {
        if (!g.getPlayerLocationDescription().contains("Type: Cave\nTreasure: Diamonds- 0, "
                + "Sapphires- 0, Rubies- 0")) {
          treasureCaves.add(g.getPlayerLocation());
        }
        allCaves.add(g.getPlayerLocation());
      }
      moveCounter++;
      g.pickTreasureAtLocation();
    }
    int percent = Math.round(((treasureCaves.size() * 100) / allCaves.size() + 5) / 10) * 10;
    assertEquals(40, percent);
    assertEquals(g.getPlayerLocation(), g.getEndLocation());
  }

  @Test
  public void testPlayerMoves() {
    Randomizer r = new RandomGeneratorDummy();
    List<Integer> randomValues = new ArrayList<>();
    int[] kruskals = {20, 6, 7, 11, 17, 15, 29, 27, 5, 3, 6, 5, 7, 24, 6, 11, 17, 13, 9, 0, 18,
                      5, 1, 16, 5, 8, 6, 3, 6, 5, 0, 1, 1, 3, 4, 2, 2, 0, 0, 0};
    int[] interconnectivity = {8, 9, 6, 10, 0, 5};
    int[] terminal = {19, 1};

    for (int i = 0; i < kruskals.length; i++) {
      randomValues.add(kruskals[i]);
    }
    for (int i = 0; i < interconnectivity.length; i++) {
      randomValues.add(interconnectivity[i]);
    }
    for (int i = 0; i < terminal.length; i++) {
      randomValues.add(terminal[i]);
    }
    int[] treasure = {14, 8, 22, 3, 6};
    for (int i = 0; i < treasure.length; i++) {
      randomValues.add(treasure[i]);
    }
    r.setDummyRandomValue(randomValues);
    GameModel g = new DungeonGame(5, 5, 6,
            false, 40, r);
    g.createPlayer();
    g.addPlayerInDungeon();
    assertEquals("01",g.getPlayerLocation());
    String[] directions = {"North", "East", "South", "West"};

    g.movePlayerTo(directions[2]); // move south
    assertEquals("11",g.getPlayerLocation());

    g.movePlayerTo(directions[3]); // move west
    assertEquals("10",g.getPlayerLocation());

    g.movePlayerTo(directions[0]); // move north
    assertEquals("00",g.getPlayerLocation());

    g.movePlayerTo(directions[1]); // move east
    assertEquals("01",g.getPlayerLocation());

    g.movePlayerTo(directions[0]); // move north-invalid move(current location remains same)
    assertEquals("01",g.getPlayerLocation());
  }

  @Test
  public void testCaveReachability() {
    Randomizer r = new RandomGeneratorDummy();
    List<Integer> randomValues = new ArrayList<>();
    int[] kruskals = {20, 6, 7, 11, 17, 15, 29, 27, 5, 3, 6, 5, 7, 24, 6, 11, 17, 13, 9, 0, 18,
                      5, 1, 16, 5, 8, 6, 3, 6, 5, 0, 1, 1, 3, 4, 2, 2, 0, 0, 0};
    int[] interconnectivity = {8, 9, 6, 10, 0, 5};
    int[] terminal = {19, 1};

    for (int i = 0; i < kruskals.length; i++) {
      randomValues.add(kruskals[i]);
    }
    for (int i = 0; i < interconnectivity.length; i++) {
      randomValues.add(interconnectivity[i]);
    }
    for (int i = 0; i < terminal.length; i++) {
      randomValues.add(terminal[i]);
    }
    int[] treasure = {14, 8, 22, 3, 6};
    for (int i = 0; i < treasure.length; i++) {
      randomValues.add(treasure[i]);
    }
    r.setDummyRandomValue(randomValues);
    GameModel g = new DungeonGame(5, 5, 6,
            false, 40, r);
    //for each cave in the dungeon
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        for (int x = 0; x < 5; x++) {
          for (int y = 0; y < 5; y++) {
            //bfsHelper method returns -1 if a path is not found between 2 nodes
            if (i != x && j != y) {
              int result = bfsHelper("" + i + j, "" + x + y, g.getEdges());
              assertNotEquals(-1, result);
            }
          }
        }
      }
    }
  }

  @Test
  public void testTreasurePickUp() {
    Randomizer r = new RandomGeneratorDummy();
    List<Integer> randomValues = new ArrayList<>();
    int[] kruskals = {20, 6, 7, 11, 17, 15, 29, 27, 5, 3, 6, 5, 7, 24, 6, 11, 17, 13, 9, 0, 18,
                      5, 1, 16, 5, 8, 6, 3, 6, 5, 0, 1, 1, 3, 4, 2, 2, 0, 0, 0};
    int[] interconnectivity = {8, 9, 6, 10, 0, 5};
    int[] terminal = {19, 1};

    for (int i = 0; i < kruskals.length; i++) {
      randomValues.add(kruskals[i]);
    }
    for (int i = 0; i < interconnectivity.length; i++) {
      randomValues.add(interconnectivity[i]);
    }
    for (int i = 0; i < terminal.length; i++) {
      randomValues.add(terminal[i]);
    }
    int[] treasure = {14, 8, 22, 3, 6};
    for (int i = 0; i < treasure.length; i++) {
      randomValues.add(treasure[i]);
    }
    r.setDummyRandomValue(randomValues);
    GameModel g = new DungeonGame(5, 5, 6,
            false, 40, r);
    g.createPlayer();
    g.addPlayerInDungeon();
    String[] directions = {"North", "East", "South", "West"};
    int moveCounter = 0;
    int[] moves = {4, 2, 2, 2};
    while (moveCounter < 4) {
      String moveResult = g.movePlayerTo(directions[moves[moveCounter] - 1]);
      moveCounter++;
    }
    String playerDesc = "\nCurrent player details: \n" + "Location: 03\n"
            + "Treasure: Diamonds- 0, Sapphires- 0, Rubies- 0";
    assertEquals(playerDesc, g.getPlayerDescription());
    g.pickTreasureAtLocation();
    assertNotEquals(playerDesc, g.getPlayerDescription());
  }

  /**
   * Helper method to calculate the distance between the start and end location given all the
   * available paths in the dungeon.
   *
   * @param startLocation start location
   * @param endLocation end location
   * @param paths list of all available paths between caves in the dungeon
   * @return
   */
  private int bfsHelper(String startLocation, String endLocation, List<String> paths) {
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
      for (String e : paths) {
        String[] p = e.split("-");
        //if current node is same as p1 vertex of edge, and p2 vertex is not yet visited,
        // and p2 vertex is not already added in the queue
        if (p[0].equals(currLoc) && !visited.contains(p[1])
                && !bfsQueue.containsKey(p[1])) {
          //add the p2 vertex to the queue with new distance
          bfsQueue.put(p[1], currDistance + 1);
          //if p2 vertex is the endLocation , then return the current distance required to reach p2
          if (p[1].equals(endLocation)) {
            return currDistance + 1;
          }
        }
        //if current node is same as p2 vertex of edge, and p1 vertex is not yet visited,
        // and p1 vertex is not already added in the queue
        if (p[1].equals(currLoc) && !visited.contains(p[0])
                && !bfsQueue.containsKey(p[0])) {
          //add the p1 vertex to the queue with new distance
          bfsQueue.put(p[0], currDistance + 1);
          //if p1 vertex is the endLocation , then return the current distance required to reach p1
          if (p[0].equals(endLocation)) {
            return currDistance + 1;
          }
        }
      } //end of for loop
    } //end of while loop
    //if the endlocation was not found then return -1
    return -1;
  }
}