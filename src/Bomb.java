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
    // return String.format("This is a bomb at: (%d, %d)", this.coords[0], this.coords[1]);
    return this.isFlagged
      ? "F"
      : "B";
  }
  
}
