import java.util.Scanner;
import java.util.InputMismatchException;

class Battleship {

  public static void main(String[] args) {
    BattleshipBoard brd = new BattleshipBoard();
    System.out.println(brd);

    boolean gameOver = false;

    while (!gameOver) {
      System.out.println("Enter 2-digit xy to fire at (x, y), 111 to quit:");
      Scanner sc = new Scanner(System.in);
      int i = 111; // will trigger game over if not being changed
      try {
        i = sc.nextInt();
      } catch (InputMismatchException ime) {
        // ignored
      }
      
      if (i >= 0 && i <= 99) {
        int col = i / 10;
        int row = i % 10;
        brd.fireAt(col, row);
        System.out.println(brd);
      } else {
        gameOver = true;
      }
    }
  }
}

class BattleshipBoard {
  final static int ROWS = 10;
  final static int COLS = 10;

  private int[][] carrier = {
    {0, 0}, // col, row
    {0, 1},
    {0, 2},
    {0, 3},
  };
  
  private int[][] submarine = {
    {2, 2}, // col, row
    {3, 2},
    {4, 2},
  };

  private int[][] battleship = {
    {2, 4}, // col, row
    {3, 4},
  };

  private int[][] bombed = {
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},

    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0,  0, 0, 0, 0, 0},
  };

  void fireAt(int col, int row) {
    bombed[col][row] = 1;
    System.out.println("bombed at (" + col + ", " + row + ")");
  }

  private boolean isCarrier(int col, int row) {
    for (int cellIndex = 0; cellIndex < carrier.length; cellIndex++) {
      if (col == carrier[cellIndex][0] && row == carrier[cellIndex][1]) {
        return true;
      }
    }
    return false;
  }

  private boolean isSubmarine(int col, int row) {
    for (int cellIndex = 0; cellIndex < submarine.length; cellIndex++) {
      if (col == submarine[cellIndex][0] && row == submarine[cellIndex][1]) {
        return true;
      }
    }
    return false;
  }

  private boolean isBattleship(int col, int row) {
    for (int cellIndex = 0; cellIndex < battleship.length; cellIndex++) {
      if (col == battleship[cellIndex][0] && row == battleship[cellIndex][1]) {
        return true;
      }
    }
    return false;
  }

  public String toString() {
    String brdStr = "";
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (isCarrier(col, row)) {
          brdStr += " c";
        } else if (isSubmarine(col, row)) {
          brdStr += " s";
        } else if (isBattleship(col, row)) {
          brdStr += " b";
        } else if (bombed[col][row] == 1) {
          brdStr += " O";
        } else {
          brdStr += " .";
        }
      }
      brdStr += "\n";
    }
    return brdStr;
  }
}
