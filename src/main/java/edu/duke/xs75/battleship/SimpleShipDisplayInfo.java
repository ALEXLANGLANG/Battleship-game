package edu.duke.xs75.battleship;

/**
 * This class handles status of coordinate within a ship
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private  T myData;
  private  T onHit;

  /**
   * This constructs this info with data and hit
   * 
   * @param d   is the value that represnet a coordinate of a ship when this
   *            coordinate was not hit
   * @param hit is the value that represent a coordinate of a ship when this
   *            coordinate was hit
   */
  public SimpleShipDisplayInfo(T d, T hit) {
    this.myData = d;
    this.onHit = hit;

  }

  /**
   * This hanldes the display of the status of any coordinate within a ship
   * 
   * @param where is one of the coordinates of the ship
   * @param hit   is flag to check if this coordinate of the ship was hit.
   * @return myData if it is not hit, otherwise return onHit '*'
   */
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    }
    return myData;
  }

}
