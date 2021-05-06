package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_NoCollisInbound() {
    Placement u1_0= new Placement(new Coordinate(1, 0), 'R',4);
    Placement r0_1= new Placement(new Coordinate(0, 1), 'U',4);
    Placement v5_5 = new Placement(new Coordinate(5, 5), 'V');
    InBoundsRuleChecker<Character> inBound = new InBoundsRuleChecker<Character>(null);
    int width = 10;
    int height = 20;
    NoCollisionRuleChecker<Character> noCollis = new NoCollisionRuleChecker<Character>(inBound);
    V1V2ShipFactory f = new V1V2ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>('X',width,height,noCollis);
    Ship<Character> ship6H1_0 =  f.makeCarrier(u1_0);
    Ship<Character> ship6V0_1 =  f.makeCarrier(r0_1);
    Ship<Character> ship2V5_5=  f.makeSubmarine(v5_5);
    String expected = "That placement is invalid: the ship overlaps another ship.";
    assertEquals(null,b.tryAddShip(ship6H1_0));
    assertEquals(true,noCollis.checkPlacement(ship6V0_1,b)==expected);
    assertEquals(null,noCollis.checkPlacement(ship2V5_5,b));
  }

 
}










