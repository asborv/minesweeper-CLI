package src;

public class Bomb extends Tile {

  Bomb(int[] coords) {
    super(coords);
  }

  @Override
  public void open() {
    Game.GameOver();
  }

  @Override
  public String toString() {
    return String.format("This is a bomb at: (%d, %d)", this.coords[0], this.coords[1]);
  }
  
}
