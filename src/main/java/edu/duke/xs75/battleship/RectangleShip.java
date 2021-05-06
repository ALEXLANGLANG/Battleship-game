package edu.duke.xs75.battleship;

import java.util.HashSet;
import java.util.HashMap;
/**
 * This class handles the Rectange shape of Ships
 */

public class RectangleShip<T> extends BasicShip<T> {
  final String name;
  /**
   * This contructs the Rect ship and the basic ship
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo,makeOrders(upperLeft, width, height));
    this.name = name;
  }

  /**
   * This contructs the Rect ship conveniently
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  /**
   * This contructs the Rect ship conveniently
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
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
   * @param upperLeft, the coordinate of a ship
   * @param width      is the rect ship's width
   * @param height     is the rect ship's height
   * @return myPieces is all coordinates the ship occupies
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashMap<Coordinate,Integer> orders = makeOrders(upperLeft,width,height);
    HashSet<Coordinate> myPieces =  new HashSet<Coordinate>(orders.keySet());
    return myPieces;
  }

  /**
   * This creates a HashMap to contains the orders of coordinates of a ship
   * 
   * @param upperLeft, the coordinate of a ship
   * @param width      is the rect ship's width
   * @param height     is the rect ship's height
   * @return myOrder is order of all coordinates the ship occupies
   */
  static HashMap<Coordinate, Integer> makeOrders(Coordinate upperLeft, int width, int height) {
    HashMap<Coordinate,Integer> myOrder = new HashMap<Coordinate, Integer>();
    int startRow = upperLeft.getRow();
    int startColumn = upperLeft.getColumn();
    int num = 1;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = startRow + row;
        int c = startColumn + col;
        Coordinate p = new Coordinate(r, c);
        myOrder.put(p, Integer.valueOf(num));
        num++;
      }
    }
    return myOrder;
  }  
}
