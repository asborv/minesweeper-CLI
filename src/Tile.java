package src;

public class Tile {
  public int adjacentBombs = 0;
  public boolean isFlagged;
  public boolean isOpen;
  public int[] coords;

  public Tile(int[] coords, int adjacentBombs) {
    this.adjacentBombs = adjacentBombs;
    this.coords = coords;
    this.isFlagged = false;
    this.isOpen = false;
  }

  // TODO Open all adjacents if none are bombs
  public void open() {
    if (!this.isFlagged) this.isOpen = true;
  }

  public void toggleFlag() {
    if (!this.isOpen) this.isFlagged = !this.isFlagged;
  }

  public String toString() {
    return this.isFlagged
      ? "F"
      : (this.isOpen ? Integer.toString(this.adjacentBombs) : " ");
  }
}
