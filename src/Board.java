package src;
import java.util.Random;

public class Board {
  int width, height;
  Tile[][] board;
  boolean[][] bombMap;

  Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.board = new Tile[width][height];
    
    // Complete bombMap before Tile initialization such that getAdjacentBombs works correctly
    // TODO Inline/map?
    this.bombMap = new boolean[width][height];
    Random rd = new Random();
    for (int y=0; y<width; y++) {
      for (int x=0; x<width; x++) {
        this.bombMap[y][x] = rd.nextBoolean();
      }
    }
    
    // Initialize tiles with random value for isBomb
    for (int y=0; y<width; y++) {
      for (int x=0; x<width; x++) {
        int[] coords = {x, y};

        this.board[y][x] = bombMap[y][x]
          ? new Bomb(coords)
          : new Tile(coords, this.getAdjacentBombs(coords));
      }
    } 
  }

  
  public int getAdjacentBombs(int[] coords) {
    int nBombs = 0;

    for (int y=coords[1]-1; y<=coords[1]+1; y++) {
      for (int x=coords[0]-1; x<=coords[0]+1; x++) {

        // The opened tile should not be checked
        if (x == coords[0] && y == coords[1]) continue;
        
        // Increment adjacent bombs
        try {
          if (this.bombMap[y][x]) nBombs++;
        } catch (ArrayIndexOutOfBoundsException e) {
          // Left empty; index out of bounds is a tile outside the board
        }
      }
    }

    return nBombs;
  }

  public String toString() {
    String str = "";
    String horizontal = "\n+" + "-+".repeat(width) + "\n";

    for (Tile[] row: this.board) {
      // Left edge
      str += horizontal + "|";

      // Cells + right edge
      for (Tile tile: row) {
        str += tile.toString() + "|";
      }
    }

    // Bottom edge
    str += horizontal;

    return str;
  }
}
