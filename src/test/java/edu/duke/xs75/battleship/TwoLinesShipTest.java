package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
public class TwoLinesShipTest {
  /**  
   *  This helps to check makeCoords
   * @param expectedCoords is the expected hashset
   * @param tested is the hasdset created by makeCoords
   */
  private void checkCoords(HashSet<Coordinate> expectedCoords,HashSet<Coordinate> tested){
    for(Coordinate c  : tested){
      assertEquals(true,tested.contains(c));
    }
    assertEquals(tested.size(),expectedCoords.size());
  }
  
  /**  
   *  This helps to check makeOrders
   * @param expectedOrders is the expected hashMap
   * @param tested is the hasdMap created by makeCoords
   */
   private void checkOrders(HashMap<Coordinate, Integer> expectedOrders, HashMap<Coordinate, Integer> tested) {
     for(Coordinate k : tested.keySet()){
       assertEquals(tested.get(k),expectedOrders.get(k));
      }
     assertEquals(tested.size(),expectedOrders.size());
    }

  @Test
  public void test_makeCoords() {
    HashSet<Coordinate> expectedCoords = new HashSet<Coordinate>();
    expectedCoords.add(new Coordinate(1, 1));
    expectedCoords.add(new Coordinate(2, 1));
    expectedCoords.add(new Coordinate(3, 1));
    expectedCoords.add(new Coordinate(3, 2));
    expectedCoords.add(new Coordinate(4, 2));
    expectedCoords.add(new Coordinate(5, 2));
    checkCoords(expectedCoords, TwoLinesShip.makeCoords(new Coordinate(1, 1), 3, 'U'));
    expectedCoords.clear();
    expectedCoords.add(new Coordinate(1, 2));
    expectedCoords.add(new Coordinate(2, 2));
    expectedCoords.add(new Coordinate(3, 2));
    expectedCoords.add(new Coordinate(3, 1));
    expectedCoords.add(new Coordinate(4, 1));
    expectedCoords.add(new Coordinate(5, 1));
    checkCoords(expectedCoords, TwoLinesShip.makeCoords(new Coordinate(1, 1), 3, 'D'));
    expectedCoords.clear();
    expectedCoords.add(new Coordinate(2, 1));
    expectedCoords.add(new Coordinate(2, 2));
    expectedCoords.add(new Coordinate(2, 3));
    expectedCoords.add(new Coordinate(1, 3));
    expectedCoords.add(new Coordinate(1, 4));
    expectedCoords.add(new Coordinate(1, 5));
    checkCoords(expectedCoords, TwoLinesShip.makeCoords(new Coordinate(1, 1), 3, 'R'));
    expectedCoords.clear();
    expectedCoords.add(new Coordinate(2, 3));
    expectedCoords.add(new Coordinate(2, 4));
    expectedCoords.add(new Coordinate(2, 5));
    expectedCoords.add(new Coordinate(1, 1));
    expectedCoords.add(new Coordinate(1, 2));
    expectedCoords.add(new Coordinate(1, 3));
    checkCoords(expectedCoords, TwoLinesShip.makeCoords(new Coordinate(1, 1), 3, 'L'));
    assertThrows(IllegalArgumentException.class, () -> TwoLinesShip.makeCoords(new Coordinate(1, 1), 3, 'S'));
  }

  @Test
  public void test_coordIsNotOnShip() {
    Coordinate upperLeft = new Coordinate(1, 1);
    TwoLinesShip<Character> ship = new TwoLinesShip<Character>("Carrier", upperLeft, 3,'L','s', '*');
    Coordinate p1 = new Coordinate(5, 5);
    assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(p1));
    assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(p1));
  }

  @Test
  public void test_name() {
    Coordinate upperLeft = new Coordinate(2, 3);
    //    Coordinate p1 = new Coordinate(3, 3);
    TwoLinesShip<Character> ship = new TwoLinesShip<Character>("Carrier", upperLeft, 3,'L','s', '*');
    assertEquals(true, ship.getName() == "Carrier");
  }

  @Test
  public void makeOrders(){
    HashMap<Coordinate, Integer> expectedOrders = new HashMap<Coordinate,Integer>();
    expectedOrders.put(new Coordinate(1,1),Integer.valueOf(1));
    expectedOrders.put(new Coordinate(2,1),Integer.valueOf(2));
    expectedOrders.put(new Coordinate(3,1),Integer.valueOf(3));
    expectedOrders.put(new Coordinate(3,2),Integer.valueOf(4));
    expectedOrders.put(new Coordinate(4,2),Integer.valueOf(5));
    expectedOrders.put(new Coordinate(5,2),Integer.valueOf(6));
    checkOrders(expectedOrders, TwoLinesShip.makeOrders(new Coordinate(1,1), 3, 'U'));
    expectedOrders.clear();
    expectedOrders.put(new Coordinate(1,2),Integer.valueOf(6));
    expectedOrders.put(new Coordinate(2,2),Integer.valueOf(5));
    expectedOrders.put(new Coordinate(3,2),Integer.valueOf(4));
    expectedOrders.put(new Coordinate(3,1),Integer.valueOf(3));
    expectedOrders.put(new Coordinate(4,1),Integer.valueOf(2));
    expectedOrders.put(new Coordinate(5,1),Integer.valueOf(1));
    checkOrders(expectedOrders, TwoLinesShip.makeOrders(new Coordinate(1,1), 3, 'D'));
    expectedOrders.clear();
    expectedOrders.put(new Coordinate(2,1),Integer.valueOf(1));
    expectedOrders.put(new Coordinate(2,2),Integer.valueOf(2));
    expectedOrders.put(new Coordinate(2,3),Integer.valueOf(3));
    expectedOrders.put(new Coordinate(1,3),Integer.valueOf(4));
    expectedOrders.put(new Coordinate(1,4),Integer.valueOf(5));
    expectedOrders.put(new Coordinate(1,5),Integer.valueOf(6)); 
    checkOrders(expectedOrders, TwoLinesShip.makeOrders(new Coordinate(1,1), 3, 'R'));
    expectedOrders.clear();
    expectedOrders.put(new Coordinate(2,3),Integer.valueOf(3));
    expectedOrders.put(new Coordinate(2,4),Integer.valueOf(2));
    expectedOrders.put(new Coordinate(2,5),Integer.valueOf(1));
    expectedOrders.put(new Coordinate(1,1),Integer.valueOf(6));
    expectedOrders.put(new Coordinate(1,2),Integer.valueOf(5));
    expectedOrders.put(new Coordinate(1,3),Integer.valueOf(4));
    checkOrders(expectedOrders, TwoLinesShip.makeOrders(new Coordinate(1,1), 3, 'L'));        
    assertThrows(IllegalArgumentException.class, () -> TwoLinesShip.makeOrders(new Coordinate(1,1), 3, 'S'));        
  }
}











