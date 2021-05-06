package edu.duke.xs75.battleship;

/**
 * This class handles the coordinates in the board It support two ways to
 * construct: one is number coordinate. e.g. (x, y), and one is string format.
 * e.g. "A0"
 */
public class Coordinate {
  /**
   * The row and column of coordinate
   */

  private final int row;
  private final int column;

  /**
   * Constructs a Coordinate of a point in the board.
   * 
   * @param r is the row of the coordinate
   * @param c is the colum of the coordinate
   */

  public Coordinate(int r, int c) {
    this.row = r;
    this.column = c;
  }

  /**
   * Constructs a Coordinate of a point in the board
   * 
   * @param descr is the string formate of the coordinate
   */

  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate length must be 2.");
    }
    descr = descr.toUpperCase();
    char rowLetter = descr.charAt(0);
    char colNum = descr.charAt(1);
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("The first axis of coordinate should be a letter.");
    }

    if (colNum < '0' || colNum > '9') {
      throw new IllegalArgumentException("The second axis of coordinate should be a number.");
    }

    this.row = rowLetter - 'A';
    this.column = colNum - '0';

  }

  /**
   * This gets the row of coordinate
   * 
   * @return row of the corrdinate
   */

  public int getRow() {
    return row;
  }

  /**
   * This gets the column of coordinate
   * 
   * @return column of the corrdinate
   */
  public int getColumn() {
    return column;
  }

  /**
   * This compares two coordinate.
   * 
   * @return True if they are qual and false if they are not.
   */

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  /**
   * This get the hashcode of the coordinate
   * 
   * @return the hashcode of the coordinate
   */

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * This makes the coordinate as the string format, e.g. "(row, column)"
   * 
   * @return the String that is the string format of the coordinate.
   */

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }
}
