package edu.duke.xs75.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * This makes the whole text veiw of MyOwnBoard
   * 
   * @return the String that is the text format of the board
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  /**
   * This makes the whole text veiw of Enemy Board
   * 
   * @return the String that is the text format of the board
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * This helps to make the whole text veiw for any Board
   * 
   * @param getSquareFn is the function to get what is at for self or enemy
   * 
   * @return the String that is the text format of the board
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    StringBuilder myboard = new StringBuilder("");
    String header = makeHeader();
    myboard.append(header);
    StringBuilder body = new StringBuilder("");
    for (int j = 0; j < toDisplay.getHeight(); j++) {
      char indx = (char) ('A' + j);
      body.append(indx);
      body.append(" ");
      String sep = "";
      for (int i = 0; i < toDisplay.getWidth(); i++) {
        Coordinate c = new Coordinate(j, i);
        Character dis = getSquareFn.apply(c);
        if (dis == null) {
          dis = ' ';
        }
        body.append(sep);
        body.append(dis);
        sep = "|";
      }
      body.append(" ");
      body.append(indx);
      body.append("\n");
    }
    myboard.append(body.toString());
    myboard.append(header);
    return myboard.toString();
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This create a string filled with space
   * 
   * @param len is the length of the string
   * @param ans is the string filled with space
   */
  private String addSpace(int len) {
    return new String(new char[len]).replace('\0', ' ');
  }

  /**
   * This combines two textboards together
   * 
   * @param enemyView   is the view for enemy
   * @param myHeader    is the header of my Board e.g. My ocean
   * @param enemyHeader is the header of enemy board. e.g. enemy's ocean
   * @return twoBoards is the combined boards
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    int w = toDisplay.getWidth();
    int h = toDisplay.getHeight();
    String combinedHeader = addSpace(5) + myHeader + addSpace(2 * w + 17 - myHeader.length()) + enemyHeader + "\n";
    String[] myLines = displayMyOwnBoard().split("\n");
    String[] enemyLines = enemyView.displayEnemyBoard().split("\n");
    StringBuilder twoBoards = new StringBuilder("");
    twoBoards.append(combinedHeader);
    for(int i = 0; i < h+2; i++){
      String left = myLines[i];
      String right = enemyLines[i] + "\n";
      if(i == 0 || i == h+1){
        left = left + addSpace(2);
      }
      twoBoards.append(left + addSpace(16) + right);
    }
    return twoBoards.toString();
  }
}
