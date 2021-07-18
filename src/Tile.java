package src;

public class Tile {
  public boolean isBomb;
  public boolean isFlagged;
  public boolean isOpen;
  public int[] coords;

  public Tile(boolean isBomb, int[] coords) {
    this.isBomb = isBomb;
    this.coords = coords;
    this.isFlagged = false;
    this.isOpen = false;
  }

  public void open() {
    if (this.isBomb) {
      Game.GameOver();
    } else {
      this.isOpen = true;
    }
  }

  public void toggleFlag() {
    if (!this.isOpen) this.isFlagged = !this.isFlagged;
  }


  // TODO Pattern to fit grid
  public String toString() {
    return "This tile is " + (!this.isBomb ? "not " : "") + "a bomb, and " + (!this.isFlagged ? "not " : "") + "flagged.";
  }  
}
