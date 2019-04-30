class Battleship {

  public static void main(String[] args) {
    System.out.println("Hello Battleship");
    BattleshipBoard brd = new BattleshipBoard();
    System.out.println(brd);
  }
}

class BattleshipBoard {
  final static int ROWS = 10;
  final static int COLS = 10;
  
  public String toString() {
    String brdStr = "";
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        brdStr += " .";
      }
      brdStr += "\n";
    }
    return brdStr;
  }
}
