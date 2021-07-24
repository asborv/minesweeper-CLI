package src;
import java.util.Random;
import java.util.ArrayList;

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
        this.bombMap[y][x] = rd.nextDouble() > .9;
      }
    }
    
    // Initialize tiles with random value for isBomb
    for (int y=0; y<this.height; y++) {
      for (int x=0; x<this.width; x++) {
        int[] coords = {x, y};

        this.board[y][x] = bombMap[y][x]
          ? new Bomb(coords)
          : new Tile(coords, this.getAdjacentBombs(coords));
      }
    } 
  }

  public int[][] getAdjacentCoords(int[] coords) {
    ArrayList<int[]> adjacents = new ArrayList<int[]>();

    for (int y=coords[1]-1; y<=coords[1]+1; y++) {
      for (int x=coords[0]-1; x<=coords[0]+1; x++) {

        // The opened tile should not be checked
        if (x == coords[0] && y == coords[1]) continue;
        
        // Adds all adjacents
        // Throws where Tile outside board
        try {
          Tile throwIfIndexOutOfBounds = this.board[y][x];
          adjacents.add(new int[] {x, y});
        } catch (ArrayIndexOutOfBoundsException e) {
          // Left empty; index out of bounds is a tile outside the board
        }
      }
    }

    return adjacents.toArray(new int[][]{});
  }
  
  public int getAdjacentBombs(int[] coords) {
    int nBombs = 0;

    for (int[] adjacentCoords : this.getAdjacentCoords(coords)) {
      if (this.bombMap[adjacentCoords[1]][adjacentCoords[0]]) nBombs++;
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
