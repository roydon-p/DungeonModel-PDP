package dungeon;

/**
 * Represents a player that can be added to a dungeon. The player can collect the treasures
 * from the location they visit in the dungeon. The interface and all its methods are kept
 * package private as they will be used only within the dungeon model package.
 */
interface Player {
  /**
   * get the count of diamonds collected by the player.
   *
   * @return count of diamonds collected
   */
  int getDiamondCount();

  /**
   * get the count of sapphires collected by the player.
   *
   * @return count of sapphires collected
   */
  int getSapphireCount();

  /**
   * get the count of rubies collected by the player.
   *
   * @return count of rubies collected
   */
  int getRubyCount();

  /**
   * Get the current location of the player.
   *
   * @return location of the player
   */
  String getLocation();

  /**
   * Add the newly picked treasure values to existing treasure values.
   *
   * @param diamondCount the count of diamonds picked from current location
   * @param sapphireCount the count of sapphires picked from current location
   * @param rubyCount the count of rubies picked from current location
   */
  void addTreasure(int diamondCount, int sapphireCount, int rubyCount);

  /**
   * Set the location of the player.
   *
   * @param location location set by the dungeon model.
   */
  void setLocation(String location);

}
