package src;

public class Point {
  private final Integer x, y;

  /**
   * Construct Point from coords Array, as such: {@code [x, y]}
   * @param coords Array of x and y coordinates, in order
   */
  Point(int[] coords) {
    if (coords.length != 2) throw new IllegalArgumentException("Point must consist of two coordinates.");

    this.x = coords[0];
    this.y = coords[1];
  }

  /**
   * Construct Point from x and y, respectively
   * @param x
   * @param y
   */
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Construct non existent Point
   */
  Point() {
    this.x = null;
    this.y = null;
  }

  public Integer getX() {
    return this.x;
  }

  public Integer getY() {
    return this.y;
  }
}