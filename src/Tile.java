package src;

public class Tile {
  public int adjacentBombs;
  public boolean isFlagged;
  public boolean isOpen;

  public Tile(int adjacentBombs) {
    this.adjacentBombs = adjacentBombs;
    this.isFlagged = false;
    this.isOpen = false;
  }

  // TODO Open all adjacents if none are bombs
  public int open() {
    if (!this.isFlagged) this.isOpen = true;
    return this.adjacentBombs;
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
