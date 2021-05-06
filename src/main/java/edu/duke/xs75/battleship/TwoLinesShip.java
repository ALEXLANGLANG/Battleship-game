package edu.duke.xs75.battleship;

import java.util.HashSet;
import java.util.HashMap;

/**
 * This class handles ship with two lines like Carrier in V2
 */
public class TwoLinesShip<T> extends BasicShip<T> {
  final String name;

  /**
   * This construct a ship with two lines
   * 
   * @param name        is the name of the ship
   * @param upperleft   is the upperleft coorinate of the ship
   * @param length      is the lengh of one line of the ship
   * @param orientation is hwo to place the ship
   * @return hashset that contains the line of coordinate
   */
  public TwoLinesShip(String name, Coordinate upperLeft, int length, char orientation, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, length, orientation), myDisplayInfo, enemyDisplayInfo, makeOrders(upperLeft, length, orientation));
    this.name = name;
  }

  public TwoLinesShip(String name, Coordinate upperLeft, int length, char orientation, T data, T onHit) {
    this(name, upperLeft, length, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
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
   * @param length      is the length of one line in the ship
   * @param orientation is how to place the ship
   * @return myPieces is all coordinates the ship occupies
   */

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int length, char orientation) {
    HashMap<Coordinate, Integer> myOrders = makeOrders(upperLeft,length,orientation);
    return new HashSet<Coordinate>(myOrders.keySet());
  }

  /**
   * Creates one line of coordinates and their corresponding orders
   * 
   * @param myPieces is the hashMap to contain coordinates and it's orders whitin the ship
   * @param sR,      sC, eR, eC is the start and end of corrdinate
   * @return hashmap that contains the line of coordinate and corresponding orders
   */
  protected static HashMap<Coordinate, Integer> makeOneLine(HashMap<Coordinate, Integer> myPieces, int sR, int sC, int eR, int eC,
      int[] orders) {
    int index = 0;
    for (int r = sR; r < eR; r++) {
      for (int c = sC; c < eC; c++) {
        Coordinate p = new Coordinate(r, c);
        myPieces.put(p, Integer.valueOf(orders[index]));
        index++;
      }
    }
    return myPieces;
  }

   /**
   * This creates a HashMap to contains all coordinates and their orders of a ship
   * 
   * @param upperLeft, the coordinate of a ship
   * @param length      is the length of one line in the ship
   * @return myPieces is all coordinates and their corresponding orders the ship occupies
   */

  static HashMap<Coordinate,Integer> makeOrders(Coordinate upperLeft, int length, char orientation) {
    HashMap<Coordinate, Integer> myPieces = new HashMap<Coordinate,Integer>();
    int sR = upperLeft.getRow(); // start row
    int sC = upperLeft.getColumn(); // start column

    if (orientation == 'U') {
      myPieces = makeOneLine(myPieces, sR, sC, sR + length, sC + 1,new int[] {1,2,3});
      myPieces = makeOneLine(myPieces, sR + 2, sC + 1, sR + length + 2, sC + 2, new int[] {4,5,6});
    } else if (orientation == 'R') {
      myPieces = makeOneLine(myPieces, sR, sC + 2, sR + 1, sC + 2 + length, new int[] {4,5,6});
      myPieces = makeOneLine(myPieces, sR + 1, sC, sR + 2, sC + length,new int[] {1,2,3});
    } else if (orientation == 'D') {
      myPieces = makeOneLine(myPieces, sR, sC + 1, sR + length, sC + 2,new int[] {6,5,4});
      myPieces = makeOneLine(myPieces, sR + 2, sC, sR + 2 + length, sC + 1,new int[] {3,2,1});
    } else if (orientation == 'L') {
      myPieces = makeOneLine(myPieces, sR, sC, sR + 1, sC + length,new int[] {6,5,4});
      myPieces = makeOneLine(myPieces, sR + 1, sC + 2, sR + 2, sC + 2 + length,new int[] {3,2,1});
    } else {
      throw new IllegalArgumentException("Orientation of the TwoLinesship must be U, L, R or D");
    }
    return myPieces;
  }
}
