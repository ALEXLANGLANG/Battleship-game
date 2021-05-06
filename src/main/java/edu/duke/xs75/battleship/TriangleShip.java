package edu.duke.xs75.battleship;
/**
 * This class handles triangle ship  like battleships in V2
 */
import java.util.HashSet;

import java.util.HashMap;
public class TriangleShip<T> extends BasicShip<T> {

  final String name;

   /**
   * This construct a ship with two lines
   * @param name is the name of the ship
   * @param upperleft is the upperleft coorinate of the ship
   * @param orientation is how to place the ship
   * @return hashset that contains the line of coordinate
   */

  public TriangleShip(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,
                      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo,makeOrders(upperLeft, orientation));
    this.name = name;
  }
  
  public TriangleShip(String name, Coordinate upperLeft, char orientation, T data, T onHit){
    this(name,upperLeft,orientation,new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }
  
  /**
   * This gives you the name of the ship
   * 
   * @return name is ship's name
   */
  public String getName() {
    return name;
  }

   /**
   * This creates a Hashset to contains all coordinates of a ship
   * 
   * @param upperLeft,  the upper-left coordinate of a ship
   * @param orientation is how to place the ship
   * @return myPieces is all coordinates the ship occupies
   */

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
    HashMap<Coordinate, Integer> myOrders = makeOrders(upperLeft,orientation);
    return new HashSet<Coordinate>(myOrders.keySet());
  }

  
   /**
   * This creates a HashMap to contains all coordinates and their orders of a ship
   * 
   * @param upperLeft, the coordinate of a ship
   * @param orientation  is the orientation of the ship
   * @return myPieces is all coordinates and their corresponding orders the ship occupies
   */
  
  static HashMap<Coordinate,Integer> makeOrders(Coordinate upperLeft, char orientation) {
    HashMap<Coordinate,Integer> myPieces = new HashMap<Coordinate,Integer>();
    int sR = upperLeft.getRow(); // start row
    int sC = upperLeft.getColumn(); // start column
    if (orientation == 'U') {
      myPieces.put(new Coordinate(sR,sC+1), Integer.valueOf(1));
      myPieces = TwoLinesShip.makeOneLine(myPieces, sR + 1, sC, sR + 2, sC + 3, new int[] {2,3,4});
    } else if (orientation == 'R') {
      myPieces.put(new Coordinate(sR+1,sC+1),Integer.valueOf(1));
      myPieces =TwoLinesShip.makeOneLine(myPieces, sR, sC, sR + 3, sC + 1,  new int[] {2,3,4});
    } else if (orientation == 'D') {
      myPieces.put(new Coordinate(sR+1,sC+1),Integer.valueOf(1));
      myPieces =TwoLinesShip.makeOneLine(myPieces, sR, sC, sR + 1 , sC + 3, new int[] {4,3,2});
    } else if (orientation == 'L') {
      myPieces.put(new Coordinate(sR+1,sC),Integer.valueOf(1));
      myPieces =TwoLinesShip.makeOneLine(myPieces, sR, sC + 1, sR + 3, sC + 2, new int[] {4,3,2});
    } else {
      throw new IllegalArgumentException("Orientation of the TwoLinesship must be U, L, R or D");
    }
    return myPieces;
  }
}
