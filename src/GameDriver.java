import dungeon.GameModel;
import dungeon.DungeonGame;
import randoms.RandomGenerator;
//import randoms.RandomGeneratorDummy;//Uncomment to test for deterministic dungeons(Wrap/non-Wrap)
import randoms.Randomizer;

//import java.util.ArrayList; //Uncomment to test for deterministic dungeons(Wrap/non-Wrap)
import java.util.List;
//import java.util.Scanner; //Uncomment to test player movement by user given directions

/**
 * The driver class to test the game model.
 */
public class GameDriver {

  static int rowCount;
  static int colCount;
  static int deg;
  static boolean isWrapping;

  /**
   * Main method contains all the calls to the public methods of the game model.
   *
   * @param args passes parameters used to create the dungeon in a string array through the
   *             console window. The order is no. of Rows, no. of columns, degree of
   *             interconnectivity, is dungeon wrapping(Y/N), percentage of caves to which treasure
   *             is to be assigned.
   */
  public static void main(String[] args) {
    if (args.length < 5) {
      throw new IllegalArgumentException("Not enough inputs arguments");
    }
    //get input
    rowCount = Integer.parseInt(args[0]);
    colCount = Integer.parseInt(args[1]);
    deg = Integer.parseInt(args[2]);
    String wrap = args[3];
    isWrapping = wrap.equals("Y") || wrap.equals("y");
    Randomizer r = new RandomGenerator();

    //Uncomment to test for auto moving player for deterministic dungeons(Wrap/non-Wrap)
    /*Randomizer r = new RandomGeneratorDummy();
    List<Integer> randomValues = new ArrayList<>();
    //getNonWrapDummyValues(randomValues); //uncomment 5X5, deg-6, non-wrap dungeon
    //getWrapDummyValues(randomValues);  //uncomment 5X5, deg-8, wrap dungeon
    r.setDummyRandomValue(randomValues);*/

    //creating the dungeon
    int tp = Integer.parseInt(args[4]);
    System.out.println("----------------------------------------------------");
    System.out.print("creating dungeon....");
    GameModel g = new DungeonGame(rowCount, colCount, deg, isWrapping, tp, r);
    System.out.println("DONE\n----------------------------------------------------");
    System.out.println("Dungeon Initial State: ");
    printDungeonPaths(g.getStartLocation(), g.getEndLocation(), "", g.getEdges());
    System.out.println("Start Location: " + g.getStartLocation() + "\nEnd Location: "
            + g.getEndLocation());

    //adding player to the dungeon
    System.out.println("----------------------------------------------------");
    System.out.print("adding player to the dungeon....");
    g.createPlayer();
    g.addPlayerInDungeon();
    System.out.println("DONE");

    //print initial game state
    System.out.println("----------------------------------------------------");
    printDungeonPaths(g.getStartLocation(), g.getEndLocation(), g.getPlayerLocation(),
            g.getEdges());
    System.out.println(g.getPlayerLocationDescription());
    g.pickTreasureAtLocation();
    System.out.println(g.getPlayerDescription());
    System.out.println("----------------------------------------------------");

    String[] directions = {"North", "East", "South", "West"};

    //get moves for deterministic dungeons
    //int[] moves = getNonWrapMoves(); //uncomment 5X5, deg-6, non-wrap dungeon
    //int[] moves = getWrapMoves();  //uncomment 5X5, deg-8, wrap dungeon
    //int moveCounter = 0; //Uncomment to test for deterministic dungeons(Wrap/non-Wrap)

    while (!g.getPlayerLocation().equals(g.getEndLocation())) {

      //Uncomment to test player movement by user given directions
      /*System.out.print("Enter move(1-North, 2-East, 3-South, 4-West): ");
      Scanner s = new Scanner(System.in);
      int dir = s.nextInt();
      String result = g.movePlayerTo(directions[dir - 1]);
      System.out.println("Move " + directions[dir - 1] + "- " + result);*/

      //Uncomment to test for auto moving player for deterministic dungeons(Wrap/non-Wrap)
      /*String result = g.movePlayerTo(directions[moves[moveCounter] - 1]);
      System.out.println("Move " + directions[moves[moveCounter] - 1] + "- " + result);
      moveCounter++;*/

      //test random auto movements for any dungeon(infinite loop possible)
      int dir_index = r.getRandomInt(1, 5);
      String result = g.movePlayerTo(directions[dir_index - 1]);
      System.out.println("Move " + directions[dir_index - 1] + "- " + result);

      //print game state
      System.out.println("----------------------------------------------------");
      printDungeonPaths(g.getStartLocation(), g.getEndLocation(), g.getPlayerLocation(),
              g.getEdges());
      System.out.println(g.getPlayerLocationDescription());
      g.pickTreasureAtLocation();
      System.out.println(g.getPlayerDescription());
      System.out.println("----------------------------------------------------");
    }
    System.out.print("Player reached the End location.");
  }

  /**
   * Print the dungeon state.
   *
   * @param startLoc     start location of the dungeon
   * @param endLoc       end location of the dungeon
   * @param playerLoc    player location in the dungeon
   * @param dungeonPaths list of traversable paths in the dungeon
   */
  private static void printDungeonPaths(String startLoc, String endLoc, String playerLoc,
                                        List<String> dungeonPaths) {
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < colCount; j++) {
        boolean edgeFound_h = false;
        for (String edge : dungeonPaths) {
          String[] s = edge.split("-");
          String p1 = s[0];
          String p2 = s[1];
          if (p1.equals("" + i + j) && p2.equals("" + i + (j + 1))
                  || p1.equals("" + i + j) && p2.equals("" + i + "0")) {
            edgeFound_h = true;
          }
        }
        if (playerLoc.equals("" + i + j)) {
          System.out.print("P");
        } else if (startLoc.equals("" + i + j)) {
          System.out.print("S");
        } else if (endLoc.equals("" + i + j)) {
          System.out.print("E");
        } else {
          System.out.print("0");
        }
        if (edgeFound_h) {
          System.out.print("--");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println();
      for (int j = 0; j < colCount; j++) {
        boolean edgeFound_v = false;
        for (String edge : dungeonPaths) {
          String[] s = edge.split("-");
          String p1 = s[0];
          String p2 = s[1];
          if (p1.equals("" + i + j) && p2.equals("" + (i + 1) + j)
                  || p1.equals("" + i + j) && p2.equals("" + "0" + j)) {
            edgeFound_v = true;
          }
        }
        System.out.print("");
        if (edgeFound_v) {
          System.out.print("|  ");
        } else {
          System.out.print("   ");
        }
      }
      System.out.println();
    }
  }

  //get dummy random values for 5X5, deg-6, non-wrap dungeon
  private static List<Integer> getNonWrapDummyValues(List<Integer> randomValues) {
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
    return randomValues;
  }

  //get moves for 5X5, deg-6, non-wrap dungeon
  private static int[] getNonWrapMoves() {
    return new int[]{4, 2, 2, 2, 2, 3, 4, 4, 4, 4, 2, 3, 2, 2, 2, 3, 4, 1, 4, 4, 4, 3, 2, 4, 3, 2,
                     2, 2, 2, 4, 4, 3, 1};
  }

  //get dummy random values for 5X5, deg-8, wrap dungeon
  private static List<Integer> getWrapDummyValues(List<Integer> randomValues) {
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
    return randomValues;
  }

  //get moves for 5X5, deg-8, wrap dungeon
  private static int[] getWrapMoves() {
    return new int[]{4, 3, 2, 4, 3, 2, 4, 3, 1, 4, 3, 1, 4, 4, 3, 2, 4, 4, 3, 4, 2, 2, 2, 2, 3, 3,
                     4, 1, 3, 3, 3, 4, 1, 1};
  }

}
