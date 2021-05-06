package edu.duke.xs75.battleship;

/**
 * This class is a factory that creates all ships in V1 and V2
 */
public class V1V2ShipFactory implements AbstractShipFactory<Character> {

  /**
   * This is an abstract function to create a rectship
   * 
   * @param where  is the upperleft placement of the ship
   * @param w      is the width of the ship
   * @param h      is the height of the ship
   * @param letter is the value of the ship shown on the board
   * @param name   is the name of the ship
   * @return rectship is a rectangle ship
   */
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
    Coordinate p = where.getWhere();
    char orientation = where.getOrientation();
    if (orientation == 'H') {
      int tmp = h;
      h = w;
      w = tmp;
    }
    // In Placement, it will throw an exception if the orientation is not legal
    RectangleShip<Character> rectShip = new RectangleShip<Character>(name, p, w, h, letter, '*');
    return rectShip;
  }

  /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }


  /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    Coordinate p = where.getWhere();
    char orientation = where.getOrientation();
    return new TriangleShip<Character>("Battleship", p, orientation,'b','*');
  }



  /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return new TwoLinesShip<Character>( "Carrier",where.getWhere(), 3, where.getOrientation(),'c', '*' );
  }

}
