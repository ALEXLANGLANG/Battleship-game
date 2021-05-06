package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  void test_normal_constructor_valid_cases() {
    Coordinate c1 = new Coordinate(1, 3);
    Coordinate c2 = new Coordinate(2, 3);
    Coordinate c3 = new Coordinate(2, 4);
    char o1 = 'v';
    char o2 = 'H';
    char o3 = 'L';
    Placement p1 = new Placement(c1, o1);
    Placement p2 = new Placement(c2, o2);
    Placement p3 = new Placement(c3, o3,4);
    assertEquals(true, c1.equals(p1.getWhere()));
    assertEquals('V', p1.getOrientation());
    assertEquals(true, c2.equals(p2.getWhere()));
    assertEquals('H', p2.getOrientation());
    assertEquals(true, c3.equals(p3.getWhere()));
    assertEquals('L', p3.getOrientation());
  }

  @Test
  void test_normal_constructor_invalid_cases() {
    Coordinate c1 = new Coordinate(1, 3);
    Coordinate c2 = new Coordinate(2, 3);
    char o1 = '1';
    char o2 = 's';
    assertThrows(IllegalArgumentException.class, () -> new Placement(c2, o2));
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, o1));
  }

  @Test
  void test_toString_valid_cases() {
    Coordinate c1 = new Coordinate(1, 3);
    Coordinate c2 = new Coordinate(2, 3);
    char o1 = 'v';
    char o2 = 'H';
    Placement p1 = new Placement(c1, o1);
    Placement p2 = new Placement(c2, o2);
    assertEquals("B3V", p1.toString());
    assertEquals("C3H", p2.toString());
  }

  @Test
  void test_equals() {
    Coordinate c1 = new Coordinate(2, 2);
    Coordinate c2 = new Coordinate(2, 3);
    char o1 = 'v';
    char o2 = 'H';
    Placement p1 = new Placement(c1, o1);
    Placement p2 = new Placement(c1, o1);
    Placement p3 = new Placement(c2, o1);
    Placement p4 = new Placement(c1, o2);

    assertEquals(p1, p1); // equals should be reflexsive
    assertEquals(p1, p2); // different objects bu same contents
    assertNotEquals(p1, p3); // different contents
    assertNotEquals(p1, p4);
    assertNotEquals(p3, p4);
    assertNotEquals(p1, "Wrong type"); // different types
  }

  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(2, 2);
    Coordinate c2 = new Coordinate(2, 3);
    char o1 = 'v';
    char o2 = 'H';
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c1, 'V');
    Placement p3 = new Placement(c2, o1);
    Placement p4 = new Placement(c1, o2);
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());

  }

  @Test
  public void test_string_constructor_valid_cases() {
    Coordinate c1 = new Coordinate(1, 3);
    Placement p1 = new Placement("B3v");
    assertEquals(true, c1.equals(p1.getWhere()));
    assertEquals('V', p1.getOrientation());
    Coordinate c2 = new Coordinate(3, 5);
    Placement p2 = new Placement("D5h");
    assertEquals(true, c2.equals(p2.getWhere()));
    assertEquals('H', p2.getOrientation());
    Coordinate c3 = new Coordinate(0, 9);
    Placement p3 = new Placement("A9V");
    assertEquals(true, c3.equals(p3.getWhere()));
    assertEquals('V', p1.getOrientation());
    Coordinate c4 = new Coordinate(25, 0);
    Placement p4 = new Placement("Z0H");
    assertEquals(true, c4.equals(p4.getWhere()));
    assertEquals('H', p4.getOrientation());

  }

  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("00v"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAv"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0S"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0/"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0:"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0s1"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0s",4));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0x",4));
  }

}
