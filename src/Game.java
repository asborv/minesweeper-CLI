package src;
import java.util.Scanner;
import java.util.Arrays;

public class Game {
  boolean gameOver = false;
  boolean didWin = false;
  Scanner scanner = new Scanner(System.in);

  /**
   * Prompt user for input
   * @return User input as {@code String}
   */
  public String userInput() {
    System.out.print(">>> ");
    String str = this.scanner.nextLine();

    return str;
  }

  /**
   * Check if input length and flag syntax is valid
   * @param inputArr User input split to substrings
   * @return {@code true} if input is invalid, otherwise {@code false}
   */
  public boolean validateInput(String[] inputArr) {
    // Invalid lengths and flag syntax
    return  inputArr.length < 2 ||
            inputArr.length > 3 ||
            inputArr.length == 3 && !inputArr[0].equalsIgnoreCase("flag");
  }

  /**
   * Prompt, validate and format user input
   * @return {@code Array} of user commands OR empty rray for invalid input
   */
  public String[] getUserCommand() {
    
    String str = userInput();
    String[] inputArr = str.split("( |,)+");
    boolean invalidInput = validateInput(inputArr);
    
    // Empty String[] signifies invalid input
    return invalidInput
      ? new String[] {}
      : inputArr;
  }

  /**
   * Flag or open, based on user input
   * @param inputArr Formatted input
   * @return Tile should be flagged
   */
  public boolean getFlag(String[] inputArr) {
    return inputArr.length == 3 &&
           inputArr[0].toLowerCase().equals("flag");
  }

  /**
   * Validates and casts string coordinates to ints
   * @param inputArr Formatted user input
   * @param flag Should tile be flagged
   * @return Array of int coordinates OR empty {@code Array} for invalid input
   */
  public Point getCoords(String[] inputArr, boolean flag) {
    try {
      // Cut off first element if flag
      String[] strCoords = flag
        ? Arrays.copyOfRange(inputArr, 1, 3)
        : inputArr;
      
      // Cast String[] to int[]
      int[] coordArr = Arrays.stream(strCoords)
        .mapToInt(Integer::parseInt)
        .toArray();

      return new Point(coordArr);

    } catch (Exception e) {
      // Non existent point signifies invalid input
      return new Point();
    }
  }

  /**
   * Checks if all non {@code Bomb}s are open,
   * and all {@code Bomb}s are flagged (win criteria)
   * @param b Ref. to current {@code Board}, such that {@codeTile}s can be accessed.
   * @return {@code true} if win criteria are met, {@code false} otherwise.
   */
  public boolean checkWin(Board b) {
    // Flatten 2D board -> filter away Bombs -> check if all are open
    boolean allTilesOpen = Arrays.stream(b.board)
      .flatMap(Arrays::stream)
      .filter(t -> !(t instanceof Bomb))
      .allMatch(t -> t.isOpen);

    // Flatten 2D board -> filter to only Bombs -> check if all are flagged
    boolean allBombsFlagged = Arrays.stream(b.board)
      .flatMap(Arrays::stream)
      .filter(t -> t instanceof Bomb)
      .allMatch(bomb -> bomb.isFlagged);
    
    return allTilesOpen && allBombsFlagged;  
  }

  /**
   * Complete turn: user input -> validation -> action 
   * @param b Ref. to current board, such that we can open/flag tiles on it
   */
  public void turn(Board b) {
    String[] inputArr = this.getUserCommand();
    boolean flag = this.getFlag(inputArr);
    Point p = this.getCoords(inputArr, flag);

    // Invalid: avoid start over from user input
    if (p.getX() == null || p.getY() == null) {
      System.err.println("\nINVALID INPUT\nWrite two coordinates, separated by space or comma.\nPrepend with 'flag' if you want to flag the tile.\n");
      return;
    }
    
    // try/catch to handle x and y values outside board
    try {
      Tile tile = b.board[p.getY()][p.getX()];

      if (flag) {
        tile.toggleFlag();
      } else {
        int adjacentBombs = tile.open();

        // Opens all adjacent when none are bombs
        // TODO: Does not propagate to adjacent 0-tiles
        if (adjacentBombs == 0) {
          for (int[] safeCoords : b.getAdjacentCoords(p)) {
            b.board[safeCoords[1]][safeCoords[0]].open();
          }
        } else if (tile instanceof Bomb) {
          this.gameOver = true;

          // Open all Tiles and Bombs (to print board when gameOver)
          // link https://stackoverflow.com/questions/22601036/stream-from-two-dimensional-array-in-java
          Arrays.stream(b.board)
            .flatMap(Arrays::stream)
            .forEach(Tile::open);
        }
      }

      System.out.println(b);
      this.didWin = this.checkWin(b);

    } catch (Exception e) {
      // Handle selected tile outside board
      System.out.println(String.format("Input outside board. Max x is %d and y is %d.", b.width - 1, b.height - 1));
      return;
    }
  }

  // TODO Board constructor from args
  public static void main(String[] args) {
    Board b = new Board(5, 5);
    Game game = new Game();

    while (!game.gameOver && !game.didWin) {
      game.turn(b);
    }

    System.out.println(game.didWin
      ? "You win!"
      : "Game over...");

    game.scanner.close();
  }
}
