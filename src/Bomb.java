package src;

public class Bomb extends Tile {

  Bomb(int[] coords) {
    // Bomb doesn't use adjacentBombs
    super(coords, 0);
  }

  @Override
  /**
   * Return code 9 is impossible for Tile,
   * signifies bomb
   */
  public int open() {
    return 9;
  }

  @Override
  public String toString() {
    return this.isFlagged
      ? "F"
      : " ";
  }
}
