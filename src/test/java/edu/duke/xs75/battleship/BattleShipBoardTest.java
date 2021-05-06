package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
public class BattleShipBoardTest {
  /**
   * This is an abstract function to check if the board is empty
   * 
   * @param b        is the battleshipboard
   * @param expected is the expected value that the ship on the board should be
   */

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    int w = b.getWidth();
    int h = b.getHeight();
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Coordinate p = new Coordinate(i, j);
        assertEquals(true, b.whatIsAtForSelf(p) == expected);
      }
    }
  }

  /**
   * This is a abstract function to add_Check_Ship
   * 
   * @param b        is the battleshipboard
   * @param expected is the expected value that the ship on the board should be
   * @param p        is the coordinate to set the ship
   */

  private <T> void add_Check_Ship(BattleShipBoard<T> b, T expected, Coordinate c) {
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    assertEquals(null, b.tryAddShip((Ship<T>) s));
    assertEquals(true, b.whatIsAtForSelf(c) == expected);
  }

  @Test
  public void test_empty_boards() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>('X', 2, 2);
    checkWhatIsAtBoard(b1, null);
    BattleShipBoard<Character> b2 = new BattleShipBoard<Character>('X', 25, 10);
    checkWhatIsAtBoard(b2, null);
    BattleShipBoard<Character> b3 = new BattleShipBoard<Character>('X', 1, 10);
    checkWhatIsAtBoard(b3, null);
    BattleShipBoard<Character> b4 = new BattleShipBoard<Character>('X', 25, 1);
    checkWhatIsAtBoard(b4, null);
  }

  @Test
  public void test_Ship_board() {
    Coordinate c1 = new Coordinate(0, 0);
    Coordinate c2 = new Coordinate(2, 3);
    Coordinate c3 = new Coordinate(19, 9);
    Coordinate c4 = new Coordinate(19, 8);
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>('X', 10, 20);
    add_Check_Ship(b1, 's', c1);
    add_Check_Ship(b1, 's', c2);
    add_Check_Ship(b1, 's', c3);
    assertEquals(true, b1.whatIsAtForSelf(c4) == null);
  }

  @Test
  public void test_width_and_height() {

    Board<Character> b1 = new BattleShipBoard<Character>('X', 10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());

  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>('X', 10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>('X', 0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>('X', 10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>('X', -8, 20));
  }

  /**
   * This test is finsihed after task15 Try to add ships to see all rules work
   */
  @Test
  public void test_addShips() {
    Board<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Placement r1_0 = new Placement(new Coordinate(1, 0), 'R',4);
    Placement u0_1 = new Placement(new Coordinate(0, 1), 'U',4);
    Placement v5_5 = new Placement(new Coordinate(18, 5), 'v');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> ship6H1_0 = f.makeCarrier(r1_0);
    Ship<Character> ship6V0_1 = f.makeCarrier(u0_1);
    Ship<Character> ship2V5_5 = f.makeSubmarine(v5_5);
    assertEquals(null, b.tryAddShip(ship6H1_0));
    assertEquals("That placement is invalid: the ship overlaps another ship.", b.tryAddShip(ship6V0_1));
    assertEquals(null, b.tryAddShip(ship2V5_5));
  }

  @Test
  public void test_fireAt() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Placement h1_0 = new Placement(new Coordinate(1, 0), 'H');
    Placement h5_0 = new Placement(new Coordinate(5, 0), 'H');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> ship6H1_0 = f.makeSubmarine(h1_0);
    assertEquals(null, b.tryAddShip(ship6H1_0));
    assertSame(ship6H1_0, b.fireAt(h1_0.getWhere()));
    assertSame(ship6H1_0, b.fireAt(new Coordinate(1, 1)));
    assertEquals(true, ship6H1_0.isSunk());
    assertSame(null, b.fireAt(h5_0.getWhere()));
  }

  @Test
  public void test_whatIsAtForEnemy() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Placement h1_0 = new Placement(new Coordinate(1, 0), 'H');
    Placement h5_0 = new Placement(new Coordinate(5, 0), 'H');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> ship6H1_0 = f.makeSubmarine(h1_0);
    assertEquals(null, b.tryAddShip(ship6H1_0));
    assertEquals(null, b.fireAt(h5_0.getWhere()));
    assertSame(ship6H1_0, b.fireAt(h1_0.getWhere()));
    assertEquals('s', b.whatIsAtForEnemy(h1_0.getWhere()));
    assertEquals('X', b.whatIsAtForEnemy(h5_0.getWhere()));
    assertEquals(null, b.whatIsAtForEnemy(new Coordinate(7, 0)));
  }

  @Test
  public void test_isAllSunk(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Placement h1_0 = new Placement(new Coordinate(1, 0), 'H');
    Placement h5_0 = new Placement(new Coordinate(5, 0), 'H');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> sub1 = f.makeSubmarine(h1_0);
    Ship<Character> sub2 = f.makeSubmarine(h5_0);
    assertEquals(false, b.isAllSunk());
    assertEquals(null, b.tryAddShip(sub1));
    assertEquals(false, b.isAllSunk());
    assertEquals(null, b.tryAddShip(sub2));
    assertEquals(false, b.isAllSunk());
    assertEquals(sub1, b.fireAt(new Coordinate(1, 0)));
    assertEquals(false, b.isAllSunk());
    assertEquals(sub1, b.fireAt(new Coordinate(1, 1)));
    assertEquals(false, b.isAllSunk());
    assertEquals(sub2, b.fireAt(new Coordinate(5, 0)));
    assertEquals(false, b.isAllSunk());
    assertEquals(sub2, b.fireAt(new Coordinate(5, 1)));
    assertEquals(true, b.isAllSunk());
  }


  @Test
  public void test_removeShip(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Placement r1_0 = new Placement(new Coordinate(1, 0), 'R',4);
    Placement u0_1 = new Placement(new Coordinate(0, 1), 'U',4);
    Placement v5_5 = new Placement(new Coordinate(18, 5), 'v');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> ship6H1_0 = f.makeCarrier(r1_0);
    Ship<Character> ship6V0_1 = f.makeCarrier(u0_1);
    Ship<Character> ship2V5_5 = f.makeSubmarine(v5_5);
    assertEquals(null, b.tryAddShip(ship6H1_0));
    assertEquals(null, b.tryAddShip(ship2V5_5));
    assertEquals(true, b.removeShip(ship6H1_0));
    assertEquals(true, b.removeShip(ship2V5_5));
    assertEquals(false,b.removeShip(ship6V0_1));
    assertEquals(null, b.fireAt(new Coordinate(1,0)));
  }

  @Test
  public  void test_findShip(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Placement r1_0 = new Placement(new Coordinate(1, 0), 'R',4);
    Placement u0_1 = new Placement(new Coordinate(0, 1), 'U',4);
    Placement v5_5 = new Placement(new Coordinate(18, 5), 'v');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> ship6H1_0 = f.makeCarrier(r1_0);
    Ship<Character> ship6V0_1 = f.makeCarrier(u0_1);
    Ship<Character> ship2V5_5 = f.makeSubmarine(v5_5);
    assertEquals(null, b.tryAddShip(ship6H1_0));
    assertEquals(null, b.tryAddShip(ship2V5_5));    
    assertEquals(ship6H1_0,b.findShip(new Coordinate(2, 0)));
    assertThrows(IllegalArgumentException.class, () -> b.findShip(new Coordinate(0, 0)));
  } 
  @Test
  public void test_masks(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Coordinate c1 = new Coordinate(1, 1);
    Placement p1 = new Placement(c1,'h');
    Coordinate c2 = new Coordinate(2,1);
    Placement p2 = new Placement(c2,'h');
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> sub1 = f.makeSubmarine(p1);
    Ship<Character> sub2 = f.makeSubmarine(p2);     
    assertEquals(null, b.tryAddShip(sub1));
    assertEquals(sub1,b.fireAt(c1));
    assertEquals(null,b.fireAt(c2));
    assertEquals('s',b.whatIsAtForEnemy(c1));
    assertEquals('X',b.whatIsAtForEnemy(c2));    
    b.addMaskToEnemyBoard(b.getMasks(sub1.getCoordinates()));
    b.addMaskToEnemyBoard(b.getMasks(sub2.getCoordinates()));
    assertEquals('s',b.enemyMasks.get(c1));
    assertEquals('X',b.enemyMasks.get(c2));
    b.removeShip(sub1);
    assertEquals('s',b.whatIsAtForEnemy(c1));
    assertEquals('X',b.whatIsAtForEnemy(c2));
    assertEquals(null, b.tryAddShip(sub2));
    assertEquals('X',b.whatIsAtForEnemy(c2));
    assertEquals('s',b.whatIsAtForEnemy(c1));
    b.fireAt(c2);
    assertEquals('s',b.whatIsAtForEnemy(c1));
    assertEquals('s',b.whatIsAtForEnemy(c2));
    b.addMaskToEnemyBoard(b.getMasks(sub1.getCoordinates()));
    b.fireAt(c1);
  }

  private void checkSonar(String[] listName, int [] listNum, HashMap<String, Integer> tested){
    HashMap<String, Integer> expected = new HashMap<String, Integer>();
    for(int i = 0; i < listNum.length; i++){
      expected.put(listName[i], Integer.valueOf(listNum[i]));
    }

    assertEquals(expected.size(), tested.size());
    for(String name : tested.keySet()){
      assertEquals(expected.get(name), tested.get(name));
    }
  }

  @Test
  public void test_sonar(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X', 10, 20);
    Coordinate c1 = new Coordinate(1, 1);
    Placement p1 = new Placement(c1,'h');
    Coordinate c2 = new Coordinate(2,1);
    Placement p2 = new Placement(c2,'u',4);
    Coordinate c3 = new Coordinate(5, 5);
    Placement p3 = new Placement(c3,'h');
    Coordinate c4 = new Coordinate(6, 5);
    Placement p4 = new Placement(c4,'u',4);
    Coordinate c5 = new Coordinate(10, 0);
    Placement p5 = new Placement(c5,'u',4);
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> sub = f.makeSubmarine(p1);
    Ship<Character> carr = f.makeCarrier(p2);
    Ship<Character> des = f.makeDestroyer(p3);
    Ship<Character> bat = f.makeBattleship(p4);  
    Ship<Character> bat2 = f.makeBattleship(p5);
    assertEquals(null, b.tryAddShip(sub));
    assertEquals(null, b.tryAddShip(carr));
    assertEquals(null, b.tryAddShip(bat));
    assertEquals(null, b.tryAddShip(des));
    assertEquals(null, b.tryAddShip(bat2));
    String [] listName = new String [] {"Submarine", "Carrier", "Destroyer", "Battleship"};
    checkSonar(listName,new int[] {2,1,0,0}, b.scanWithSonarAt(new Coordinate(0,0)) );
    checkSonar(listName,new int[] {0,3,3,2}, b.scanWithSonarAt(new Coordinate(5,4)) );
    checkSonar(listName,new int[] {0,5,0,0}, b.scanWithSonarAt(new Coordinate(6,1)) );
  }
}










