package src;
import java.util.Arrays;
import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.ArrayList;

public class Board {
  int width, height;
  Tile[][] board;
  Boolean[][] bombMap;

  Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.board = new Tile[height][width];
    
    // Complete bombMap before Tile initialization such that getAdjacentBombs works correctly
    
    // width has no effect as rows as reassigned later - left for readability
    this.bombMap = new Boolean[height][width];
    Random rd = new Random();
    DoubleFunction<Boolean> bombIfGreater = x -> x > .9;

    for (int y=0; y<this.height; y++) {
      // row with random doubles - mapped to true/false if bigger than threshold
      Boolean[] row = rd.doubles(width)
        .mapToObj(bombIfGreater)
        .toArray(Boolean[]::new);
        
      this.bombMap[y] = row;
    }

    // Initialize tiles with random value for isBomb
    for (int y=0; y<this.height; y++) {
      for (int x=0; x<this.width; x++) {
        Point p = new Point(x, y);

        this.board[y][x] = this.bombMap[y][x]
          ? new Bomb()
          : new Tile(this.getAdjacentBombs(p));
      }
    } 
  }

  Tile tileAt(Point p) {
    return this.board[p.getY()][p.getX()];
  }

  public Point[] getAdjacentCoords(Point p) {
    ArrayList<Point> adjacents = new ArrayList<>();

    for (int y=p.getY()-1; y<=p.getY()+1; y++) {
      for (int x=p.getX()-1; x<=p.getX()+1; x++) {

        // The opened tile should not be checked
        if (x == p.getX() && y == p.getY()) continue;
        
        // Adds all adjacents
        try {
          Point adjacent = new Point(x, y);
          this.tileAt(adjacent); // Throws if outside board
          adjacents.add(adjacent);
        } catch (ArrayIndexOutOfBoundsException e) {
          // Left empty; index out of bounds is a tile outside the board
        }
      }
    }

    return adjacents.toArray(Point[]::new);
  }
  
  public int getAdjacentBombs(Point p) {
    int nBombs = 0;

    for (Point adjacentCoord : this.getAdjacentCoords(p)) {
      if (this.bombMap[adjacentCoord.getY()][adjacentCoord.getX()]) nBombs++;
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
