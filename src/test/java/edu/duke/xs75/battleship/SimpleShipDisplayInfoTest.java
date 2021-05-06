package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    char d = 's';
    char onhit = '1';
    Coordinate where = new Coordinate(0,0);
    SimpleShipDisplayInfo<Character> ssdi =  new SimpleShipDisplayInfo<Character>(d,onhit);
    
    assertEquals('1', ssdi.getInfo(where,true));
    assertEquals('s', ssdi.getInfo(where,false));
  }
}









