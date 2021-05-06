package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {

  /**
   * This helps to check rule with a Carrier and customized board and expected value
   * @param where is the Placement where you put your Carrier
   * @param width is the width of the board
   * @param height is the height of the board
   * @param expected is the expected input for assertEquals(). If you are testing valid cases,
   * expected = true, otherwise, false.
   */
  private void checkRuleHelperWithCarrier(Placement where, int width, int height, String expected){
    InBoundsRuleChecker<Character> checkRule = new InBoundsRuleChecker<Character>(null);
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> ship =  f.makeCarrier(where);
    Board<Character> b = new BattleShipBoard<Character>('X',width,height,checkRule);
    assertEquals(true,checkRule.checkPlacement(ship,b) == expected);    
  }
  
  @Test
  public void test_checkMyRule() {
    checkRuleHelperWithCarrier(new Placement(new Coordinate(1, 1), 'U',4), 10, 20, null);
    checkRuleHelperWithCarrier(new Placement(new Coordinate(1, 2), 'R',4), 10, 20, null);
    checkRuleHelperWithCarrier(new Placement(new Coordinate(1, 2), 'R',4), 5, 20, "That placement is invalid: the ship goes off the right of the board.");
    checkRuleHelperWithCarrier(new Placement(new Coordinate(16, 2), 'U',4), 10, 20, "That placement is invalid: the ship goes off the bottom of the board.");
    checkRuleHelperWithCarrier(new Placement(new Coordinate(-5, 2), 'U',4), 10, 20,"That placement is invalid: the ship goes off the top of the board." );
    checkRuleHelperWithCarrier(new Placement(new Coordinate(0, -5), 'L',4), 10, 20,"That placement is invalid: the ship goes off the left of the board.");
  }
}











