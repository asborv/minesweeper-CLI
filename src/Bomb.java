package src;

public class Bomb extends Tile {

  Bomb(int[] coords) {
    // Bomb doesn't use adjacentBombs
    super(coords, 0);
  }

  @Override
  public void open() {
    Game.GameOver();
  }

  @Override
  public String toString() {
    return this.isFlagged
      ? "F"
      : " ";
  }
}
