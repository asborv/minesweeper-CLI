package src;
import java.util.Scanner;
import java.util.Arrays;

public class Game {
  
  public static void GameOver() {
    System.out.println("Game over");
  }

  public static void main(String[] args) {

    // link https://stackoverflow.com/questions/15613626/scanner-is-never-closed
    try (Scanner scanner = new Scanner(System.in)) {
      Board b = new Board(5, 5);
  
      System.out.print(">>> ");
      String str = scanner.nextLine();
      
  
      String[] strCoords;
      int[] coords;
      boolean flag = false;
      
      // Split by spaces and commas
      String[] inputArr = str.split("( |,)+");
  
      try {
  
        // Correct format for flag
        if (inputArr.length == 3 && inputArr[0].toLowerCase().equals("flag")) {
          strCoords = Arrays.copyOfRange(inputArr, 1, 3);
          flag = true;
  
        // Correct format for open
        } else if (inputArr.length == 2) {
            strCoords = inputArr;
        
        // Incorrect format
        // ? Remove try/catch - print error here
        } else {
          throw new Exception("Invalid input.\nProvide two integer coordinates, and prepend with 'flag' if you want to flag the tile.");
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
        //! REMOVE - continue when loop is implemented
        strCoords = new String[] {"2", "2"};
      }
  
      // Parse coordinates to ints
      coords = Arrays.stream(strCoords)
                .mapToInt(Integer::parseInt)
                .toArray();
      
      if (flag) {
        b.board[coords[0]][coords[1]].toggleFlag();
      } else {
        b.board[coords[0]][coords[1]].open();
      }
  
      System.out.println(b);
    }

  }
}
