package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class SonarTest {
  /*
   * This creates a set based on the list of R and C.
   * @param listR is the row coordinates
   * @param listC is the column coordinates
   * @return map is the hashset
*/
  private HashSet<Coordinate> createSonarMapHelper(int [] listR, int [] listC ){
    HashSet<Coordinate> map = new HashSet<Coordinate>();
    assertEquals(listR.length, listC.length);
    for(int i = 0; i < listR.length; i++){
      map.add(new Coordinate(listR[i], listC[i]));
    }
    return map;
  }

  
  @Test
  public void test_createSonarMap() {
    Sonar<Character> s = new Sonar<Character>(10,20);
    s.createSonarMap(new Coordinate(0,0)); 
    HashSet<Coordinate> expected = createSonarMapHelper(new int [] {0,0,0,0,1,1,1,2,2,3}, new int [] {0,1,2,3,0,1,2,0,1,0});
    assertEquals(expected,s.sonarMap);
    s.createSonarMap(new Coordinate(2,1));
    expected = createSonarMapHelper(new int [] {0,0,0,1,1,1,1,2,2,2,2,2,3,3,3,3,4,4,4,5}, new int [] {0,1,2,0,1,2,3,0,1,2,3,4,0,1,2,3,0,1,2,1});
    assertEquals(expected,s.sonarMap);
    s.createSonarMap(new Coordinate(19,9));
    expected = createSonarMapHelper(new int [] {19,19,19,19,18,18,18,17,17,16}, new int [] {6,7,8,9,7,8,9,8,9,9});
    s.createSonarMap(new Coordinate(3, 3));
    expected = createSonarMapHelper(
        new int[] { 0, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 6 },
        new int[] { 3, 2, 3, 4, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 2, 3, 4, 3 });
    assertEquals(expected,s.sonarMap);


}
  @Test
  public void test_scanShip(){
    Sonar<Character> s = new Sonar<Character>(10,20);
    s.createSonarMap(new Coordinate(0,0));
    V1V2ShipFactory f = new V1V2ShipFactory();
    Placement p00 = new Placement(new Coordinate(0, 0), 'V');
    Placement p55 = new Placement(new Coordinate(5, 5), 'U', 4);
    Ship<Character> vSub = f.makeSubmarine(p00);
    Ship<Character> uCar = f.makeCarrier(p55);
    assertEquals(2,s.scanShip(vSub));
    assertEquals(0,s.scanShip(uCar)); 
  }

}










