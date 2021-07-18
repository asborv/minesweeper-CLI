package src;

public class Tile {
  public boolean isFlagged;
  public boolean isOpen;
  public int[] coords;

  public Tile(int[] coords) {
    this.coords = coords;
    this.isFlagged = false;
    this.isOpen = false;
  }

  public void open() {
    if (!this.isFlagged) this.isOpen = true;
  }

  public void toggleFlag() {
    if (!this.isOpen) this.isFlagged = !this.isFlagged;
  }


  // TODO Pattern to fit grid
  public String toString(int adjacentBombs) {
    // return String.format("This tile is at: (%d, %d)", this.coords[0], this.coords[1]);
    return this.isFlagged
      ? "F"
      : Integer.toString(adjacentBombs);
  }
}
