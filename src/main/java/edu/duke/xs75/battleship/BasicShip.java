package edu.duke.xs75.battleship;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
/**
 * This class handles the basic ship including rectangle,etc. It includes all
 * basic methods and a hashmap to if check the ship is hit.
 */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  protected HashMap<Coordinate, Integer> myOrder;
  /**
   * This is an abstract function to hlep us check if a coordinate in the ship.
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException("The Coordinate must be within the ship.");
    }
  }

  /**
   * Constructs the Basicship with multiple suqares
   * 
   * @param where         is the upperleft of the coorinate of the ship
   * @param myDisplayInfo is the value for the Myownship on the board we want to show
   *                      based on if it is hit or not. e.g. if it is hit '*', if
   *                      not 's', if miss, show 'blank'
   * @param enemyDisplayInfo is the value for the anemyShip on the board we want to show
   *                      based on if it is hit or not. e.g. if it is hit 's', if
   *                      not 'blank'. If miss, show 'X' 
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo,HashMap<Coordinate,Integer> theOrder ) {
    this.myPieces = new HashMap<Coordinate, Boolean>();
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
    this.myOrder = new HashMap<Coordinate,Integer> (theOrder);
  }

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */

  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (!wasHitAt(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for ownShip
   * or enemy's
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */

  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    checkCoordinateInThisShip(where);
    boolean wasHit = wasHitAt(where);
    if (myShip) {
      return myDisplayInfo.getInfo(where, wasHit);
    }
    return enemyDisplayInfo.getInfo(where, wasHit);
  }

  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * Get the order of the coordinate in the ship
   * @param where is the coordinate of the ship 
   * @return the corresponding integer of the coordinate.
   */

  @Override
  public Integer getIntFromCoord(Coordinate where){
    checkCoordinateInThisShip(where);
    return myOrder.get(where);
  }
  /**
   * Get the Coordinate from the order in the ship
   * @param num  is the corresponding integer for the coordinate of the ship 
   * @return the coordinate corresponding to the integer.
   */

  @Override
  public Coordinate getCoordFromInt(Integer num){
    for(Map.Entry<Coordinate, Integer> entry: myOrder.entrySet()){
      if(Objects.equals(num,entry.getValue())){
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("The number must be in the range of length of the ship.");
  }
  /**
   * Copy the ship info from the other one
   * @return true if succeed, false otherwise
   */  

  @Override
  public boolean copyInfoFrom(Ship<T> other){
    for(Coordinate c : other.getCoordinates()){
      if(other.wasHitAt(c)){
        Integer num = other.getIntFromCoord(c);
        Coordinate c1 = this.getCoordFromInt(num);
        this.recordHitAt(c1);
      }
    }
    return true;
  }
  
 }







