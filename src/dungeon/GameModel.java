package dungeon;

import java.util.List;

/**
 * Represents the game model which can interact with the controller to receive and send data that
 * is required and generated by the Game.
 */
public interface GameModel {

  /**
   * The start location of the dungeon.
   *
   * @return the cave location from where the player can enter the dungeon
   */
  public String getStartLocation();

  /**
   * The end location of the dungeon.
   *
   * @return the cave location from where the player can exit the dungeon
   */
  public String getEndLocation();

  /**
   * Represents the path that a player can traverse through in the dungeon.
   *
   * @return the list of edges that form the paths in the dungeon
   */
  public List<String> getEdges();

  /**
   * Creates the player that will be added in the dungeon.
   */
  public void createPlayer();

  /**
   * Adds the player to the dungeon.
   */
  public void addPlayerInDungeon();

  /**
   * Get the current location of the player in the dungeon.
   *
   * @return location of player in the dungeon
   */
  public String getPlayerLocation();

  /**
   * Gets the description of the treasure and next possible moves from the current location
   * of the player.
   *
   * @return the description of treasure at the location
   */
  public String getPlayerLocationDescription();

  /**
   * Instructs the dungeon to pick the treasure from current cave and assign it to the player.
   */
  public void pickTreasureAtLocation();

  /**
   * Get the description of the player along with the information of treasure they have collected
   * till this point in game.
   *
   * @return player description
   */
  public String getPlayerDescription();

  /**
   * moves the player in the direction mentioned.
   *
   * @param direction the direction in which the player is to be moved
   * @return 'Invalid' if the player cannot move in given direction or string to indicate that
   *          player has moved successfully to a new location.
   */
  public String movePlayerTo(String direction);

}