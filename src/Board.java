package src;
import java.util.Random;

public class Board {
  Tile[][] board;

  Board(int width, int height) {
    this.board = new Tile[width][height];
    Random rd = new Random();

    // Initialize tiles with random value for isBomb
    // TODO Balance, is now 50%?
    for (int i=0; i<width; i++) {
      for (int j=0; j<width; j++) {
        this.board[i][j] = new Tile(rd.nextBoolean());
      }
    }
  }

  // TODO Pattern to fit grid
  public String toString() {
    String s = "";

    for (Tile[] row: this.board) {
      for (Tile tile: row) {
        s += tile.toString() + "\n";
      }
      s += "\n\n\n";
    }
    return s;
  }
}
