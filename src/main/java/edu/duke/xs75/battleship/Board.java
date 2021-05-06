package edu.duke.xs75.battleship;
import java.util.HashMap;
/**
 * This is the interface of the board
 */
public interface Board<T> {
  /**
   * This gives you the width of the board
   * 
   * @return width is the width of the board
   */

  public int getWidth();

  /**
   * This gives you the height of the board
   * 
   * @return height is the height of the board
   *
   */
  public int getHeight();

  /**
   * This takes a Coordinate see the info of that coordiante within Ship itself.
   * 
   * @param where is the Coodinate object we want to check
   * @return the info of the coordiante within Myownship if found, null otherwise
   */

  public T whatIsAtForSelf(Coordinate where);

  /**
   * This takes a Coordinate see the info of that coordiante within Enemy Ship
   * itself.
   * 
   * @param where is the Coodinate object we want to check
   * @return the info of the coordiante within the ship if found, null otherwise
   */

  public T whatIsAtForEnemy(Coordinate where);

  /**
   * This tries to add a ship into myShips
   * 
   * @param toAdd is the ship object we want to add
   * @return if the toAdd is legal ship, return null if toAdd is not legal, return
   *         a String.
   */

  public String tryAddShip(Ship<T> toAdd);

  /**
   * This searches for any ship that occupies the coordinate c
   * 
   * @param c is the coordinate to be searched
   * @return if succeed, return a ship that occupies that coordinate Otherwise,
   *         throws an expection
   */

  
  public Ship<T> findShip(Coordinate c);
  /**
   * This searches for any ship that occupies the coordinate c
   * 
   * @param c is the coordinate to be searched
   * @return if succeed, return a ship that occupies that coordinate Otherwise,
   *         record the miss in the missSet and return null
   */

  public Ship<T> fireAt(Coordinate c);

  /**
   * This iterates over ships to see if they are all sunk
   * 
   * @return true if they are all sunk, false otherwise.
   */

  public boolean isAllSunk();

  /**
   * This remove a ship from the board
   * @return true if succeed, false otherwise.
   */
  
  public boolean removeShip(Ship<T> s);

  /**
   * This makes sure that enemy will know nothing about move of ship 
   * Add mask to the board
   * @param masks is a hashmap of coordinates and their enemyview values 
   */
  public void addMaskToEnemyBoard(HashMap<Coordinate,T> masks );
  
  /**
   * This makes sure that enemy will know nothing about move of ship 
   * get enemyview for all  coords 
   * @param coords is a set of coordinates you want to search
   * @return masks a hashmap from coordinate to enemy view vlues.
   */
  public HashMap<Coordinate,T> getMasks(Iterable<Coordinate> coords);

  
  /**
   * This will scan an area centered at a coordinate to record
   * how much squares each ship occupies.
   * @param where is the coordinate of the center of the area
   * @return resFromSonar is the hashmap to record name of ship and number of squares
   * in this area     
*/  

  public HashMap<String, Integer> scanWithSonarAt(Coordinate where);

}
