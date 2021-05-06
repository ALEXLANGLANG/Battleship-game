package edu.duke.xs75.battleship;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangleShipTest {

  @Test
  public void test_makeCoords() {
    Coordinate upperLeft1 = new Coordinate(0, 0);
    // check ship1
    int width = 2;
    int height = 2;
    HashSet<Coordinate> myPieces = RectangleShip.makeCoords(upperLeft1, width, height);
    for (int c = 0; c < width; c++) {
      for (int r = 0; r < height; r++) {
        Coordinate p = new Coordinate(r, c);
        assertEquals(true, myPieces.contains(p));
      }
    }
  }

  @Test
  public void test_super_thing() {
    Coordinate upperLeft2 = new Coordinate(2, 2);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft2, 2, 2, 's', '*');
    Coordinate p1 = new Coordinate(2, 3);
    assertEquals(true, Rectship.occupiesCoordinates(p1));
    assertEquals(true, Rectship.occupiesCoordinates(upperLeft2));
  }

  @Test
  public void test_coordIsNotOnShip() {
    Coordinate upperLeft = new Coordinate(1, 1);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft, 1, 2, 's', '*');
    Coordinate p1 = new Coordinate(5, 5);
    assertThrows(IllegalArgumentException.class, () -> Rectship.wasHitAt(p1));
    assertThrows(IllegalArgumentException.class, () -> Rectship.recordHitAt(p1));
  }

  @Test
  public void test_recordAtHit_wasHitAt() {
    Coordinate upperLeft = new Coordinate(0, 0);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft, 2, 2, 's', '*');
    assertEquals(false, Rectship.wasHitAt(upperLeft));
    Rectship.recordHitAt(upperLeft);
    assertEquals(true, Rectship.wasHitAt(upperLeft));
    Coordinate p1 = new Coordinate(1, 1);
    assertEquals(false, Rectship.wasHitAt(p1));
    Rectship.recordHitAt(p1);
    assertEquals(true, Rectship.wasHitAt(p1));

  }

  @Test
  public void test_isSunk() {
    Coordinate upperLeft = new Coordinate(2, 3);
    Coordinate p1 = new Coordinate(3, 3);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft, 1, 2, 's', '*');
    assertEquals(true, Rectship.getDisplayInfoAt(upperLeft,true) == 's');
    assertEquals(true, Rectship.getDisplayInfoAt(upperLeft,false) == null);
    assertEquals(true, Rectship.getDisplayInfoAt(p1,true) == 's');
    assertEquals(false, Rectship.isSunk());
    Rectship.recordHitAt(upperLeft);
    assertEquals(true, Rectship.getDisplayInfoAt(upperLeft,true) == '*');
    assertEquals(true, Rectship.getDisplayInfoAt(p1,true) == 's');
    assertEquals(false, Rectship.isSunk());
    Rectship.recordHitAt(p1);
    assertEquals(true, Rectship.getDisplayInfoAt(upperLeft,true) == '*');
    assertEquals(true, Rectship.getDisplayInfoAt(p1,true) == '*');
    assertEquals(true, Rectship.isSunk());
  }

  @Test
  public void test_name() {
    Coordinate upperLeft = new Coordinate(2, 3);
    //    Coordinate p1 = new Coordinate(3, 3);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft, 1, 2, 's', '*');
    assertEquals(true, Rectship.getName() == "submarine");
  }

  @Test
  public void test_getCoordinates() {
    Coordinate upperLeft = new Coordinate(2, 3);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft, 1, 2, 's', '*');
    Iterable <Coordinate> allCoords = Rectship.getCoordinates();
    int size = 0;
    for(Coordinate c : allCoords){
      size +=1;
      assertEquals(true,Rectship.occupiesCoordinates(c));
    }
    assertEquals(true, size == 2);
  }

  @Test
  public void test_makeOrders(){
    Coordinate upperLeft = new Coordinate(2, 3);
    Coordinate p1 = new Coordinate(3, 3);
    RectangleShip<Character> Rectship = new RectangleShip<Character>("submarine", upperLeft, 1, 2, 's', '*');
    
    assertEquals(1,Rectship.getIntFromCoord(upperLeft));
    assertEquals(2,Rectship.getIntFromCoord(p1));
    assertEquals(Rectship.getCoordFromInt(1),upperLeft);
    assertEquals(Rectship.getCoordFromInt(2),p1);
    assertThrows(IllegalArgumentException.class, ()-> Rectship.getCoordFromInt(3));
  }
}






