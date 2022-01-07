package dungeon;

/**
 * Represents a cave or tunnel in the dungeon. If the location has 2 entry points, then it is
 * classified as tunnel. A tunnel cannot have treasure, whereas a cave can contain treasure.
 * Each cave/tunnel maintains the directions which can be used to move to a neighbouring cave.
 * The class is kept package private as it will be used only within the dungeon model package.
 */
class Cave {
  private final boolean moveNorth;
  private final boolean moveSouth;
  private final boolean moveEast;
  private final boolean moveWest;
  private final String location;
  private boolean isTunnel;
  private int diamondCount;
  private int sapphireCount;
  private int rubyCount;

  /**
   * Creates a cave/tunnel and sets its initial values.
   *
   * @param location  location of the cave in the dungeon
   * @param moveNorth true if the door to north side is open, false if there is no door
   * @param moveSouth true if the door to south side is open, false if there is no door
   * @param moveEast  true if the door to east side is open, false if there is no door
   * @param moveWest  true if the door to west side is open, false if there is no door
   */
  Cave(String location, boolean moveNorth, boolean moveSouth, boolean moveEast,
       boolean moveWest) {
    this.location = location;
    this.moveNorth = moveNorth;
    this.moveSouth = moveSouth;
    this.moveEast = moveEast;
    this.moveWest = moveWest;
    setIsTunnel();
  }

  private void setIsTunnel() {
    int openDoorCounter = 0;
    if (moveNorth) {
      openDoorCounter++;
    }
    if (moveSouth) {
      openDoorCounter++;
    }
    if (moveEast) {
      openDoorCounter++;
    }
    if (moveWest) {
      openDoorCounter++;
    }
    if (openDoorCounter == 2) {
      isTunnel = true;
    } else {
      isTunnel = false;
    }
  }

  protected boolean isMoveNorth() {
    return moveNorth;
  }

  protected boolean isMoveSouth() {
    return moveSouth;
  }

  protected boolean isMoveEast() {
    return moveEast;
  }

  protected boolean isMoveWest() {
    return moveWest;
  }

  protected boolean isTunnel() {
    return isTunnel;
  }

  protected void assignInitialTreasure() {
    this.diamondCount = Treasure.DIAMONDS.getRandomQuantity();
    this.sapphireCount = Treasure.SAPPHIRES.getRandomQuantity();
    this.rubyCount = Treasure.RUBIES.getRandomQuantity();
  }

  protected void updatePickedTreasureStatus() {
    this.diamondCount = 0;
    this.sapphireCount = 0;
    this.rubyCount = 0;
  }

  protected int getDiamondCount() {
    return this.diamondCount;
  }

  protected int getSapphireCount() {
    return this.sapphireCount;
  }

  protected int getRubyCount() {
    return this.rubyCount;
  }

  protected String getLocation() {
    return location;
  }
}

