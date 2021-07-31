package src;

public class Bomb extends Tile {

  Bomb(int[] coords) {
    // Bomb does not use adjacentBombs
    // 9 to avoid accidental Tile-functionality
    super(coords, 9);
  }

  @Override
  public String toString() {
    return this.isFlagged
      ? "F"
      : (this.isOpen ? "B" : " ");
  }
}
