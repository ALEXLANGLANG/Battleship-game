package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.HashMap;
public class TriangleShipTest {
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
    expectedCoords.add(new Coordinate(1,2));
    expectedCoords.add(new Coordinate(2,1));
    expectedCoords.add(new Coordinate(2,2));
    expectedCoords.add(new Coordinate(2,3));
    checkCoords(expectedCoords, TriangleShip.makeCoords(new Coordinate(1,1), 'U'));
    expectedCoords.clear();
    expectedCoords.add(new Coordinate(1,1));
    expectedCoords.add(new Coordinate(2,1));
    expectedCoords.add(new Coordinate(3,1));
    expectedCoords.add(new Coordinate(2,2));
    checkCoords(expectedCoords, TriangleShip.makeCoords(new Coordinate(1,1),'D'));
    expectedCoords.clear();
    expectedCoords.add(new Coordinate(1,1));
    expectedCoords.add(new Coordinate(1,2));
    expectedCoords.add(new Coordinate(1,3));
    expectedCoords.add(new Coordinate(2,2));
    checkCoords(expectedCoords, TriangleShip.makeCoords(new Coordinate(1,1),'R'));
    expectedCoords.clear();
    expectedCoords.add(new Coordinate(1,2));
    expectedCoords.add(new Coordinate(2,2));
    expectedCoords.add(new Coordinate(3,2));
    expectedCoords.add(new Coordinate(2,1));
    checkCoords(expectedCoords, TriangleShip.makeCoords(new Coordinate(1,1),'L'));        
    assertThrows(IllegalArgumentException.class, () -> TriangleShip.makeCoords(new Coordinate(1,1), 'S'));
    
  }


    @Test
  public void test_name() {
    Coordinate upperLeft = new Coordinate(2, 3);
    //    Coordinate p1 = new Coordinate(3, 3);
    TriangleShip<Character> ship = new TriangleShip<Character>("Battleship", upperLeft,'L','b', '*');
    assertEquals(true, ship.getName() == "Battleship");
  }

  @Test
  public void test_coordIsNotOnShip() {
    Coordinate upperLeft = new Coordinate(1, 1);
    TriangleShip<Character> ship = new TriangleShip<Character>("Battleship", upperLeft, 'L', 'b', '*');
    Coordinate p1 = new Coordinate(5, 5);
    assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(p1));
    assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(p1));
  }

  @Test
  public void test_super_thing() {
    Coordinate upperLeft = new Coordinate(1, 1);
    TriangleShip<Character> ship = new TriangleShip<Character>("Battleship", upperLeft, 'U', 'b', '*');

    assertEquals(true, ship.occupiesCoordinates(new Coordinate(1, 2)));
    assertEquals(true, ship.occupiesCoordinates( new Coordinate(2, 1)));
    assertEquals(true, ship.occupiesCoordinates( new Coordinate(2, 2)));
    assertEquals(true, ship.occupiesCoordinates( new Coordinate(2, 3))); 
  }

  @Test
  public void test_makeOrders(){
    HashMap<Coordinate,Integer> expectedCoords = new HashMap<Coordinate,Integer>();
    expectedCoords.put(new Coordinate(1,2),Integer.valueOf(1));
    expectedCoords.put(new Coordinate(2,1),Integer.valueOf(2));
    expectedCoords.put(new Coordinate(2,2),Integer.valueOf(3));
    expectedCoords.put(new Coordinate(2,3),Integer.valueOf(4));
    checkOrders(expectedCoords, TriangleShip.makeOrders(new Coordinate(1,1), 'U'));
    expectedCoords.clear();
    expectedCoords.put(new Coordinate(1,1),Integer.valueOf(2));
    expectedCoords.put(new Coordinate(2,1),Integer.valueOf(3));
    expectedCoords.put(new Coordinate(3,1),Integer.valueOf(4));
    expectedCoords.put(new Coordinate(2,2),Integer.valueOf(1));
    checkOrders(expectedCoords, TriangleShip.makeOrders(new Coordinate(1,1),'R'));
    expectedCoords.clear();
    expectedCoords.put(new Coordinate(1,1),Integer.valueOf(4));
    expectedCoords.put(new Coordinate(1,2),Integer.valueOf(3));
    expectedCoords.put(new Coordinate(1,3),Integer.valueOf(2));
    expectedCoords.put(new Coordinate(2,2),Integer.valueOf(1));
    checkOrders(expectedCoords, TriangleShip.makeOrders(new Coordinate(1,1),'D'));
    expectedCoords.clear();
    expectedCoords.put(new Coordinate(1,2),Integer.valueOf(4));
    expectedCoords.put(new Coordinate(2,2),Integer.valueOf(3));
    expectedCoords.put(new Coordinate(3,2),Integer.valueOf(2));
    expectedCoords.put(new Coordinate(2,1),Integer.valueOf(1));
    checkOrders(expectedCoords, TriangleShip.makeOrders(new Coordinate(1,1),'L'));        
    assertThrows(IllegalArgumentException.class, () -> TriangleShip.makeOrders(new Coordinate(1,1), 'S'));
  }


  @Test
  public void test_copyInfoFrom(){
    Coordinate upperLeft = new Coordinate(1,1);
    TriangleShip<Character> s1 = new TriangleShip<Character>("Battleship",upperLeft,'U','b','*');
    TriangleShip<Character> s2 = new TriangleShip<Character>("Battleship",upperLeft,'R','b','*');
    s1.recordHitAt(new Coordinate(1,2));
    assertEquals(true,s2.copyInfoFrom(s1));
    assertEquals(true,s2.wasHitAt(new Coordinate(2,2)));
    assertEquals(false,s2.wasHitAt(new Coordinate(1,1)));
    assertEquals(false,s2.wasHitAt(new Coordinate(2,1)));
    assertEquals(false,s2.wasHitAt(new Coordinate(3,1)));
  }
  
}











