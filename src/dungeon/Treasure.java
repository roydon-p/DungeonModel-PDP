package dungeon;

import java.util.Random;

/**
 * Represents the types of Treasure that is available in the dungeon. The quantity of each type
 * of treasure is randomly generated.
 */
public enum Treasure {
  DIAMONDS(),
  SAPPHIRES(),
  RUBIES();

  /**
   * Generates the count of treasure for each type of treasure randomly.
   *
   * @return the randomly generated count
   */
  public int getRandomQuantity() {
    int maxLimit = 10;
    int minLimit = 0;
    Random r = new Random();
    return r.nextInt(maxLimit - minLimit) + minLimit;
  }

}










