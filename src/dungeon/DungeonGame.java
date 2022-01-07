package dungeon;

import randoms.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * DungeonGame represents the class that will perform all the operations in the dungeon
 * game. This model is responsible for creating the dungeon and player and adding the player
 * to the dungeon and later on provides the interactivity between the controller and other
 * concrete classes in the model.
 */
public class DungeonGame implements GameModel {
  private final Dungeon d;
  private Player p;

  /**
   * Creates an instance of a game that will create the dungeon.
   *
   * @param rowCount                the no. of rows in the dungeon grid
   * @param colCount                the no. of columns in the dungeon grid
   * @param degOfInterconnectivity  the no of paths that can be added to the kruskals output
   *                                minimum spanning tree.
   * @param isWrap                  true if the dungeon has paths wrapping type
   * @param percentCavesForTreasure the percentage of caves to which treasure is to be assigned
   * @param r                       the randomizer object
   */
  public DungeonGame(int rowCount, int colCount, int degOfInterconnectivity, boolean isWrap,
                     int percentCavesForTreasure, Randomizer r) {
    this.d = new MasterDungeon(rowCount, colCount, degOfInterconnectivity, isWrap,
            percentCavesForTreasure, r);
  }

  @Override
  public String getStartLocation() {
    return d.getStartLocation();
  }

  @Override
  public String getEndLocation() {
    return d.getEndLocation();
  }

  @Override
  public List<String> getEdges() {
    List<String> dungeonPaths = new ArrayList<>();
    for (Edge e : d.getEdges()) {
      String s = e.toString();
      dungeonPaths.add(s);
    }
    return dungeonPaths;
  }

  @Override
  public void createPlayer() {
    if (this.p == null) {
      p = new MasterDungeonPlayer(0, 0, 0, "");
    } else {
      throw new IllegalStateException("Player is already created for this game.");
    }
  }

  @Override
  public void addPlayerInDungeon() {
    if (this.p == null) {
      throw new IllegalStateException("Player does not exist for this game.");
    }
    d.addPlayer(p);
  }

  @Override
  public String getPlayerLocation() {
    if (this.p == null) {
      throw new IllegalStateException("Player does not exist for this game.");
    }
    return p.getLocation();
  }

  @Override
  public String getPlayerLocationDescription() {
    if (this.p == null) {
      throw new IllegalStateException("Player does not exist for this game.");
    }
    String s = String.format("Current location details:\nType: %s",
            d.getLocationType(p.getLocation()));
    if (d.getLocationType(p.getLocation()).equals("Cave")) {
      s += "\nTreasure: " + d.getLocationTreasure(p.getLocation());
    }
    if (!p.getLocation().equals(d.getEndLocation())) {
      s += "\nNext possible moves: " + d.getNextPossibleMoves(p.getLocation());
    }
    return s;
  }

  @Override
  public void pickTreasureAtLocation() {
    if (this.p == null) {
      throw new IllegalStateException("Player does not exist for this game.");
    }
    d.pickTreasure(p);
  }

  @Override
  public String getPlayerDescription() {
    if (this.p == null) {
      throw new IllegalStateException("Player does not exist for this game.");
    }
    return String.format("\nCurrent player details: \nLocation: %s\nTreasure: Diamonds- %d, "
                    + "Sapphires- %d, Rubies- %d", p.getLocation(), p.getDiamondCount(),
            p.getSapphireCount(), p.getRubyCount());
  }

  @Override
  public String movePlayerTo(String direction) {
    if (this.p == null) {
      throw new IllegalStateException("Player does not exist for this game.");
    }
    return d.move(p, direction);
  }
}
