package edu.duke.xs75.battleship;

/**
 * This class is to make sure that 
 * all rules related to if all coordinates of a ship are within the bounds.
*/
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  /**
   * This constructs a InBoundRule checker
   */
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * This iterates over all coordiantes to check they are in bounds If we fail our
   * own rule, return false
   * 
   * @param theShip  is the ship we want to test
   * @param theBorad is the board we are using
   * @return null if it pass all rule, otherwise return the string related
   * to the corresponding error.
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int width = theBoard.getWidth();
    int height = theBoard.getHeight();
    Iterable<Coordinate> allCoords = theShip.getCoordinates();
    for (Coordinate c : allCoords) {
      int row = c.getRow();
      int col = c.getColumn();
      if (row < 0){
        return "That placement is invalid: the ship goes off the top of the board.";
      }
      if(row>=height){
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if(col < 0){
        return "That placement is invalid: the ship goes off the left of the board.";
      }
      if(col >= width){
        return "That placement is invalid: the ship goes off the right of the board.";
      }
    }
    return null;
  }
}
