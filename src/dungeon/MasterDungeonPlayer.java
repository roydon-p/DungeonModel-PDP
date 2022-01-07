package dungeon;

/**
 * Represents the player that is added to the master dungeon. This player will move through the
 * dungeon and collect treasures from the caves in the dungeon. The class is kept package private
 * as it will be used only within the dungeon model package.
 */
class MasterDungeonPlayer implements Player {
  private int diamondCount;
  private int sapphireCount;
  private int rubyCount;
  private String location;

  /**
   * Creates a player that will be entered into the dungeon. The player is created with 0 treasure
   * and dungeon start location as its initial location.
   *
   * @param diamondCount  count of diamonds that the player has before starting the game
   * @param sapphireCount count of sapphires that the player has before starting the game
   * @param rubyCount     count of rubies that the player has before starting the game
   * @param location      current location of the player
   */
  MasterDungeonPlayer(int diamondCount, int sapphireCount, int rubyCount, String location) {
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    this.diamondCount = diamondCount;
    this.sapphireCount = sapphireCount;
    this.rubyCount = rubyCount;
    this.location = location;
  }

  @Override
  public void addTreasure(int diamondCount, int sapphireCount, int rubyCount) {
    this.diamondCount += diamondCount;
    this.sapphireCount += sapphireCount;
    this.rubyCount += rubyCount;
  }

  @Override
  public void setLocation(String location) {
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    this.location = location;
  }

  @Override
  public int getDiamondCount() {
    return diamondCount;
  }

  @Override
  public int getSapphireCount() {
    return sapphireCount;
  }

  @Override
  public int getRubyCount() {
    return rubyCount;
  }

  @Override
  public String getLocation() {
    return location;
  }
}
