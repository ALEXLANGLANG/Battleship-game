package edu.duke.xs75.battleship;
/**
 * This class handles all Placement rules related to check 
 * if the ship is not out of bounds and the ship
 * does not overlap each other.
*/
public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  /**
   * This helps subclass to define it's own checking rule
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);
  /**
   * This constructs a placement rule checker
   * it points to the next rule checker   
   */
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  public String checkPlacement (Ship<T> theShip, Board<T> theBoard) {
    //if we fail our own rule: stop the placement is not legal
    String res = checkMyRule(theShip, theBoard);
    if (res != null) {
      return res;
    }
    //other wise, ask the rest of the chain.

    if (next != null) {
      res = next.checkMyRule(theShip, theBoard);
      return res; 
    }
    //if there are no more rules, then the placement is legal
    return null;
  }
  
}




