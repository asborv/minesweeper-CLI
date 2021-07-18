package src;
import java.util.Random;

public class Board {
  Tile[][] board;

  Board(int width, int height) {
    this.board = new Tile[width][height];
    Random rd = new Random();

    // Initialize tiles with random value for isBomb
    // TODO Balance, is now 50%?
    for (int y=0; y<width; y++) {
      for (int x=0; x<width; x++) {
        int[] coords = {x, y};
        this.board[y][x] = new Tile(rd.nextBoolean(), coords);
      }
    }
  }

  
  public int getAdjacentBombs(int[] coords) {
    int nBombs = 0;

    for (int x=coords[0]-1; x<=coords[0]+1; x++) {
      for (int y=coords[1]-1; y<=coords[1]+1; y++) {
        // The opened tile should not be checked
        if (x == coords[0] && y == coords[1]) continue;
        
        // Increment adjacent bombs
        try {
          if (this.board[y][x].isBomb) nBombs++;
        } catch (ArrayIndexOutOfBoundsException e) {
          // Left empty; index out of bounds is a tile outside the board
        }
      }
    }

    return nBombs;
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