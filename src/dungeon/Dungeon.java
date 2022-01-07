package dungeon;

import java.util.List;

/**
 * Represents the dungeon that contains the caves/tunnels and controls the players movement
 * and state while they are moving in the dungeon. The dungeon is built such that every location
 * within the dungeon can be reached from every other location in the dungeon. The interface and
 * all its methods are kept package private as they will be used only within the dungeon model
 * package.
 */
interface Dungeon {

  /**
   * Adds the player to the dungeon.
   *
   * @param p the player that is to be added to the dungeon
   */
  void addPlayer(Player p);

  /**
   * moves the player in the dungeon.
   *
   * @param p         the player that is being moved
   * @param direction the direction in which the player is to be moved
   * @return 'Invalid' if the player cannot move in given direction or string to indicate that
   *          player has moved successfully to a new location.
   */
  String move(Player p, String direction);

  /**
   * picks the treasure if it exists at the current location of the player.
   *
   * @param p the player to whom the treasure will be assigned.
   */
  void pickTreasure(Player p);

  /**
   * Represents the path that a player can traverse through in the dungeon.
   *
   * @return the list of edges that form the paths in the dungeon
   */
  List<Edge> getEdges();

  /**
   * The start location from where the player will enter the dungeon.
   *
   * @return the cave location from where the player can enter the dungeon
   */
  String getStartLocation();

  /**
   * The end location from where the player will exit the dungeon.
   *
   * @return the cave location from where the player can exit the dungeon
   */
  String getEndLocation();

  /**
   * Gets the type of location i.e. Cave or Tunnel for input  location.
   *
   * @param loc the input location
   * @return the type of location i.e. Cave or Tunnel
   */
  String getLocationType(String loc);

  /**
   * Gets the description of treasure that exists at the given location.
   *
   * @param loc the input location
   * @return the description of treasure at the location
   */
  String getLocationTreasure(String loc);

  /**
   * Gets the next possible moves a player can take from the given location.
   *
   * @param loc the input location
   * @return the next possible moves
   */
  String getNextPossibleMoves(String loc);
}
