import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

class Battleship {

  public static void main(String[] args) {
    BattleshipBoard brd = new BattleshipBoard();
    brd.showShips = true;
    System.out.println(brd);

    boolean quitGame = false;

    while (!quitGame) {
      if (brd.gameOver()) {
        System.out.println("You win! :D");
        break;
      } 
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
        quitGame = true;
      }
    }
  }
}

class BattleshipBoard {
  final static int ROWS = 10;
  final static int COLS = 10;
  boolean showShips = false;
  private int hitCount = 0;

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

  private int[][] bombed; 

  BattleshipBoard() {
    bombed = new int[ROWS][COLS];
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        bombed[col][row] = 0;
      }
    }
    randomizeShips();
  }

  boolean gameOver() {
    return hitCount == carrier.length + submarine.length + battleship.length;
  }

  private void randomizeShips() {
    do {
      randomizeShip(carrier);
      randomizeShip(submarine);
      randomizeShip(battleship);
    } while (shipsOverlapped() || shipsOutOfBound());
  }
  
  private boolean shipsOverlapped() {
    return overlapped(carrier, submarine) || overlapped(carrier, battleship) || overlapped(submarine, battleship);
  }

  private boolean overlapped(int[][] shipOne, int[][] shipTwo) {
    for (int shipOneCell = 0; shipOneCell < shipOne.length; shipOneCell++) {
      for (int shipTwoCell = 0; shipTwoCell < shipTwo.length; shipTwoCell++) {
        if (shipOne[shipOneCell][0] == shipTwo[shipTwoCell][0] && shipOne[shipOneCell][1] == shipTwo[shipTwoCell][1] ) {
          return true;
        }
      }
    }
    return false;
  }

  private void randomizeShip(int[][] ship) {
    Random random = new Random();
    int headCol = random.nextInt(COLS);
    int headRow= random.nextInt(ROWS);
    ship[0][0] = headCol;
    ship[0][1] = headRow;
    if (random.nextInt(100) % 2 == 0) {
      for (int i = 1; i < ship.length; i++) {
        ship[i][0] = ship[0][0] + i;
        ship[i][1] = ship[0][1];
      }
    } else {
      for (int i = 1; i < ship.length; i++) {
        ship[i][0] = ship[0][0];
        ship[i][1] = ship[0][1] + i;
      }
    }
  }

  private boolean shipsOutOfBound() {
    return shipOutOfBound(carrier) || shipOutOfBound(submarine) || shipOutOfBound(battleship);
  }

  private boolean shipOutOfBound(int[][] ship) {
    for (int cellIndex = 0; cellIndex < ship.length; cellIndex++) {
      if (ship[cellIndex][0] < 0 || ship[cellIndex][0] >= COLS || ship[cellIndex][1] < 0 || ship[cellIndex][1] >= ROWS) {
        return true;
      }
    }
    return false;
  }

  void fireAt(int col, int row) {
    bombed[col][row] = 1;
    if (isHit(col, row)) {
      hitCount++;
    }
    System.out.println("bombed at (" + col + ", " + row + ") " + (isHit(col, row) ? "hit :-)" : "missed :-("));
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

  private boolean isHit(int col, int row) {
    return isCarrier(col, row) || isSubmarine(col, row) || isBattleship(col, row);
  }

  public String toString() {
    String brdStr = "";
    for (int col = 0; col < COLS; col++) {
      brdStr += " " + col;
    }
    brdStr += "\n";

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (bombed[col][row] == 1) {
          if (isHit(col, row)) {
            brdStr += " x";
          } else {
            brdStr += " o";
          }
        } else if (!showShips) {
          brdStr += " .";
        } else {
          if (isCarrier(col, row)) {
            brdStr += " c";
          } else if (isSubmarine(col, row)) {
            brdStr += " s";
          } else if (isBattleship(col, row)) {
            brdStr += " b";
          } else {
            brdStr += " .";
          }
        }
      }
      brdStr += "\n";
    }
    return brdStr;
  }
}
