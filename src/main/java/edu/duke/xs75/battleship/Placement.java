package edu.duke.xs75.battleship;

/**
 * This class handles placement of a ship in a Board (i.e., record the
 * coordinate and how to place the ship: vertically or horizontally for 
 * Rectangluar ships and Up, down, left or rigth for ships with other shape).
 */

public class Placement {
  final Coordinate where;
  final char orientation;
  final int types;
/**
 * This class handles placement of a ship in a Board (i.e., record the
 * coordinate and how to place the ship: vertically or horizontally).
 */

  private char checkOrientation(char o) {
    char upperO = Character.toUpperCase(o);
    if (this.types == 2 && upperO != 'V' && upperO != 'H') {
      throw new IllegalArgumentException("Orientation should be \'v\', \'V\', \'h\', or \'H\'");
    }
    if (this.types == 4 && upperO != 'U' && upperO != 'R' && upperO != 'D' && upperO != 'L') {
      throw new IllegalArgumentException("Orientation should be \'u\', \'U\',\'r\', \'R\',\'d\', \'D\', \'l\'or \'L\'");
    }    
    return upperO;
  }
  /**
   * Constructs a Placement in the board.
   * 
   * @param where is the upper left coordinate of the ship 
   * @param orientation is how to place the ship: vertically or hotizontally
   * @param t is the number of types of this placement. e.g. 2 for H,V and 4 for UPLR
   */

  public Placement(Coordinate w, char o, int t) {
    this.where = new Coordinate(w.getRow(), w.getColumn());
    this.types = t;
    char upperO = checkOrientation(o);
    this.orientation = upperO;

  }

  /**
   * Simply constructs a placement for V and H
   */
  public Placement(Coordinate w, char o){
    this(w,o,2);
  }
  
  /**
   * Constructs a Placement in the board
   * 
   * @param descr is the string format of the Placement
   */
  public Placement(String descr, int t) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("The length of String input of Placement must be 3 but is " + descr.length());
    }
    String w = descr.substring(0, 2);
    char o = descr.charAt(2);
    this.types = t;
    char upperO = checkOrientation(o);
    this.where = new Coordinate(w);
    this.orientation = upperO;
  }

  public Placement(String descr) {
    this(descr,2);
  }
  /**
   * This gets the coordinate of Placement
   * 
   * @return coordinate of Placement
   */

  public Coordinate getWhere() {
    return where;
  }

  /**
   * This gets the orientation of Placement
   * 
   * @return orientation of the Placement
   */

  public char getOrientation() {
    return orientation;
  }

  /**
   * This compares two Placement.
   * 
   * @return True if they are equal and false if they are not.
   */

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.getWhere()) && orientation == p.getOrientation();
    }
    return false;
  }

  /**
   * This get the hashcode of the Placement
   * 
   * @return the hashcode of the Placement
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * This makes the Placement as the string format, e.g. "A0V" or "A0v"
   * 
   * @return the String that is the string format of the Placement.
   */
  @Override
  public String toString() {
    String r = Character.toString(where.getRow() + 'A');
    String c = Character.toString(where.getColumn() + '0');
    String o = Character.toString(orientation);
    return r + c + o;
  }
}
