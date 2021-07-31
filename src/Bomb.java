package src;

public class Bomb extends Tile {

  Bomb(int[] coords) {
    // Bomb has special case for adjacentBombs
    super(coords, 9);
  }

  @Override
  public String toString() {
    return this.isFlagged
      ? "F"
      : (this.isOpen ? "B" : " ");
  }
}
