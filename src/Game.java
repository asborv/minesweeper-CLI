package src;
import java.util.Scanner;
import java.util.Arrays;

public class Game {
  boolean gameOver = false;
  Scanner scanner = new Scanner(System.in);

  // TODO Abstract to several functions, each to do ONE thing
  // TODO Exception handling on malformed inputs
  /**
   * Prompt, validate and format user input
   * @return Array of user commands OR empty array for invalid input
   */
  public String[] formatInput() {
    // Propmpt
    System.out.print(">>> ");
    String str = this.scanner.nextLine();
    
    // Input string to array by spaces & commas
    String[] inputArr = str.split("( |,)+");

    // Invalid lengths and flag syntax
    boolean invalidInput = inputArr.length < 2 ||
                           inputArr.length > 3 ||
                           inputArr.length == 3 && !inputArr[0].equalsIgnoreCase("flag");
    
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
   * @return Array of int coordinates OR empty array for invalid input
   */
  public int[] getCoords(String[] inputArr, boolean flag) {
    try {
      // Cut off first element if flag
      String[] strCoords = flag
        ? Arrays.copyOfRange(inputArr, 1, 3)
        : inputArr;
      
      // Cast String[] to int[]
      return Arrays.stream(strCoords)
        .mapToInt(Integer::parseInt)
        .toArray();
    } catch (Exception e) {
      // Empty Array signifies invalid input
      return new int[] {};
    }
  }

  // TODO Check win criteria
  /**
   * Complete turn: user input -> validation -> action 
   * @param b Ref. to current board, such that we can open/flag tiles on it
   */
  public void turn(Board b) {
    String[] inputArr = this.formatInput();
    boolean flag = this.getFlag(inputArr);
    int[] coords = this.getCoords(inputArr, flag);
    Tile tile = b.board[coords[1]][coords[0]];

    // Invalid: avoid start over from user input
    if (coords.length == 0 || inputArr.length == 0) {
      System.err.println("INVALID INPUT");
      return;
    }

    if (flag) {
      tile.toggleFlag();
    } else {
      // 9 is a special case for bomb: see Bomb constructor
      int adjacentBombs = tile.open();

      // Opens all adjacent when none are bombs
      // NOTE: Does not propagate to adjacent 0-tiles
      if (adjacentBombs == 0) {
        for (int[] safeCoords : b.getAdjacentCoords(coords)) {
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
  }

  // TODO Board constructor from args
  public static void main(String[] args) {
    Board b = new Board(5, 5);
    Game game = new Game();

    while (!game.gameOver) {
      game.turn(b);
    }

    game.scanner.close();
    System.out.println("Game over");
  }
}
