package edu.duke.xs75.battleship;

/**
 * This class helps us to parameterize our BasicShip
*/
public interface ShipDisplayInfo<T> {
  /**
   * This hanldes the display of the status of any coordinate within a ship
   * 
   * @param where is one of the coordinates of the ship
   * @param hit   is flag to check if this coordinate of the ship was hit.
   * @return myData if it is not hit, otherwise return onHit '*'
   */
  public T getInfo(Coordinate where, boolean hit);
}
