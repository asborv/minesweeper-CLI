package src;
import java.util.Scanner;
import java.util.Arrays;

public class Game {
  boolean gameOver = false;
  Scanner scanner = new Scanner(System.in);

  // TODO Abstract to several functions, each to do ONE thing
  /**
   * Prompt, validate and format user input
   * @return Array of user commands OR empty array for invalid input
   */
  public String[] formatInput() {
    boolean invalidInput;

    // Propmpt
    System.out.print(">>> ");
    String str = this.scanner.nextLine();
    
    // Input string to array by spaces & commas
    String[] inputArr = str.split("( |,)+");

    // Invalid lengths and flag syntax
    invalidInput = inputArr.length < 2 ||
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
  
  /**
   * Complete turn: user input -> validation -> action 
   * @param b Ref. to current board, such that we can open/flag tiles on it
   */
  public void turn(Board b) {
    String[] inputArr = this.formatInput();
    boolean flag = this.getFlag(inputArr);
    int[] coords = this.getCoords(inputArr, flag);
    Tile tile = b.board[coords[0]][coords[1]];

    // Invalid: avoid start over from user input
    if (coords.length == 0 || inputArr.length == 0) {
      System.err.println("INVALID INPUT");
      return;
    }

    if (flag) {
      tile.toggleFlag();
    } else {
      // 9 is a special case for bomb: see Bomb.open()
      int adjacentBombs = tile.open();

      if (adjacentBombs == 0) {
        // TODO open all adjacents
      } else if (adjacentBombs == 9) {
        this.gameOver = true;
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
    // TODO Print entire board open
    System.out.println("Game over");
  }
}
