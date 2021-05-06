package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1V2ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName,char expectedLetter, Coordinate... expectedLocs){
    assertEquals(true,testShip.getName() == expectedName);
    for(Coordinate c : expectedLocs){
      //assertEquals(true,testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter,testShip.getDisplayInfoAt(c,true));
    }
  }
  
  @Test
  public void test_makeShips() {
    V1V2ShipFactory f = new V1V2ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Placement h1_2 = new Placement(new Coordinate(1, 2), 'h');
    Ship<Character> vSub = f.makeSubmarine(v1_2);
    checkShip(vSub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
    Ship<Character> hSub = f.makeSubmarine(h1_2);
    checkShip(hSub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));
    Ship<Character> vDst = f.makeDestroyer(v1_2);
    checkShip(vDst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    Ship<Character> hDst = f.makeDestroyer(h1_2);
    checkShip(hDst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));
    Placement u1_1 = new Placement(new Coordinate(1, 1), 'U',4);
    Ship<Character> uBat = f.makeBattleship(u1_1);
    checkShip(uBat, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(2, 3));
    Ship<Character> uCar = f.makeCarrier(u1_1);
    checkShip(uCar, "Carrier", 'c', new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1),  new Coordinate(3, 2), new Coordinate(4, 2),new Coordinate(5, 2));
    
    
    
  }

}




