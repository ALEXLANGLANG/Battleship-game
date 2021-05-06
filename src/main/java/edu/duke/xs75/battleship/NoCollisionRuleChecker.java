package edu.duke.xs75.battleship;

/**
 * This class is to make sure that all rules related to if all coordinates of a
 * ship are within the bounds.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  /**
   * This constructs the noCollisionRule checker
   */
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * This iterates over all coordiantes within the ship to check if the coordinate
   * they will occupy was occupied. If it was, reutrn false.
   * 
   * @param theShip  is the ship we want to test
   * @param theBorad is the board we are using
   * @return null if it pass all rule, otherwise string related to 
   * corresponding error.
   */

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> allCoords = theShip.getCoordinates();
    for (Coordinate c : allCoords) {
      if (theBoard.whatIsAtForSelf(c) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }
}
