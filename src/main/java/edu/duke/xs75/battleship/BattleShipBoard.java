package edu.duke.xs75.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
/**
 * This class the shipboard and it contain the size of the board and all ships
 * on the board.
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  final T missInfo;
  protected HashMap<Coordinate, T> enemyMasks; 

  private Sonar<T> sonar;
  
  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(T missInfo, int w, int h, PlacementRuleChecker<T> boundChecker) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.missInfo = missInfo;
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.enemyMisses = new HashSet<Coordinate>();
    this.placementChecker = boundChecker;
    this.enemyMasks = new HashMap<Coordinate,T>();
    this.sonar = new Sonar<T>(w,h);
  }

  /**
   * A conveient constructor with witdth and height of the board
   */
  public BattleShipBoard(T missInfo, int w, int h) {
    this(missInfo, w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)));
  }

  /**
   * This searches for any ship that occupies the coordinate c
   * 
   * @param c is the coordinate to be searched
   * @return if succeed, return a ship that occupies that coordinate Otherwise,
   *         record the miss in the missSet and return null
   */
  @Override
  public Ship<T> fireAt(Coordinate c) {
    if(enemyMasks.containsKey(c)){
      enemyMasks.remove(c);
    }
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        return ship;
      }
    }
    enemyMisses.add(c);
    return null;
  }


  /**
   * This searches for any ship that occupies the coordinate c
   * 
   * @param c is the coordinate to be searched
   * @throws if not found, throw an invalid argument exception
   * @return if succeed, return a ship that occupies that coordinate Otherwise,
   *         throw an expcetion
   */
  @Override
  public Ship<T> findShip(Coordinate c){
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        return ship;
      }
    }
    throw new IllegalArgumentException("Coordinate must be part of a ship.");
  }
  /**
   * This gives you the width of the board
   * 
   * @return width is the width of the board
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * This gives you the height of the board
   * 
   * @return height is the height of the board
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * This tries to add a ship into myShips
   * 
   * @param toAdd is the ship object we want to add
   * @return if the toAdd is legal ship, return null if toAdd is not legal, return
   *         a String.
   */
  @Override
  public String tryAddShip(Ship<T> toAdd) {
    String res = placementChecker.checkPlacement(toAdd, this);
    if (res == null) {
      myShips.add(toAdd);
    }

    return res;
  }

  /**
   * This tries to delete a ship from  myShips
   * 
   * @param toDelete is the ship object we want to remove
   * @return if the toDelete is legal ship, return true. Otherwise return false.
   *         
   */
  @Override
  public boolean removeShip(Ship<T> toDelete) {
    return myShips.remove(toDelete);
  }
  /**
   * This takes a Coordinate see the info of that coordiante within Ship itself.
   * 
   * @param where is the Coodinate object we want to check
   * @return the info of the coordiante within Myownship if found, null otherwise
   */
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * This takes a Coordinate see the info of that coordiante within Enemy Ship
   * itself.
   * 
   * @param where is the Coodinate object we want to check
   * @return the info of the coordiante within the ship if found, null otherwise
   */
  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * This takes a Coordinate see the info of that coordiante for enemy ship or
   * player's own ship.
   * 
   * @param where  is the Coodinate object we want to check
   * @param isSelf is a flag to select which ship you want to check
   * @return the info of the coordiante within the ship if found, 
   * if coordinate is not within any ship, the missinfo will be returned
   * if isSelf == false. For other cases, return  null.
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    if(!isSelf && enemyMasks.containsKey(where)){
      return enemyMasks.get(where);
    }
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if(!isSelf && enemyMisses.contains(where)){
      return missInfo;
    }
    return null;
  }

  /**
   * this checks if all ships on board are sunk
   * 
   * @return true if all are sunk or no ships, fase otherwise.
   */
  @Override
  public boolean isAllSunk() {
    if (myShips.size() == 0) {
      return false;
    }
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * This makes sure that enemy will know nothing about move of ship
   * Add coords and their the orignal values for enemy board to hashmap
   * @param coords is a set of coordinates you want to masks
*/
  @Override
  public void addMaskToEnemyBoard(HashMap<Coordinate, T> masks ){
    for(Coordinate c : masks.keySet()){
      enemyMasks.put(c,masks.get(c));
    }
  }

/**
 * This makes sure that enemy will know nothing about move of ship
 * Remove coords and their the orignal values for enemy board from hashmap
 * @param coords is a set of coordinates you want to remove
 * @return a hashmap that mask coordinate to the old value
*/
  @Override
  public HashMap<Coordinate, T> getMasks(Iterable<Coordinate> coords){
    HashMap<Coordinate, T> masks = new HashMap<Coordinate, T>();
    for(Coordinate c : coords){
      masks.put(c,whatIsAtForEnemy(c));
    }    
    return masks;
  }

  /**
   * This will scan an area centered at a coordinate to record
   * how much squares each ship occupies.
   * @param where is the coordinate of the center of the area
   * @return resFromSonar is the hashmap to record name of ship and number of squares
   * in this area     
*/  
  @Override
  public HashMap<String, Integer> scanWithSonarAt(Coordinate where){
    sonar.createSonarMap(where);
    HashMap<String, Integer> resFromSonar = new HashMap<String, Integer>();
    for(Ship<T> s: myShips){
      int num = sonar.scanShip(s);
      String name = s.getName();
      if (resFromSonar.containsKey(name)){
        num += resFromSonar.get(name).intValue();
      }
      resFromSonar.put(name, Integer.valueOf(num));
    }
    return resFromSonar;
  }
}










