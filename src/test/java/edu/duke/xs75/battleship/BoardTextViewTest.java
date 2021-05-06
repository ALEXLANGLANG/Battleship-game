package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  /**
   * This function helps to test emptyboard
   * @param w, width of the board 
   * @param h, height of the board
   * @param expectedHeader, expectedHeader that board should be matched with
   * @param expectedBody, expectedBody that board should be matched with   
   */
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>('X',w, h);
    BoardTextView view = new BoardTextView(b1);
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
  
  /**
   * This function helps to test board with ships   
   * @param w, width of the board 
   * @param h, height of the board
   * @param expected, expected content of the board  
   */
  private void shipBoardHelper(Board<Character>b, int w, int h, String expected) {
    BoardTextView view = new BoardTextView(b);
    assertEquals(expected, view.displayMyOwnBoard());
  }
  
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>('X',2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n" + "B  |  B\n";
    assertEquals(expectedHeader,view.makeHeader());
    //    assertEquals(expectedBody, view.makeBody());
    String expected = "  0|1\n" + "A  |  A\n" + "B  |  B\n" + "  0|1\n";
    assertEquals(expected, view.displayMyOwnBoard());
    emptyBoardHelper(2, 2, expectedHeader, expectedBody);
  }

  //  @Test
  //  public void test_board_10by20()
  
  @Test
  public void test_display_nonempty_board_4by3(){
    Board<Character> b1 = new BattleShipBoard<Character>('X',4, 3);
    Coordinate c1 = new Coordinate(0,0);
    Coordinate c2 = new Coordinate(2, 3);
    Coordinate c3 = new Coordinate(0, 1);
    RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    RectangleShip<Character> s3 = new RectangleShip<Character>(c3, 's', '*');
    //    Ship<Character> s1 = new BasicShip(c1);
    //Ship<Character> s2 = new BasicShip(c2);
    //Ship<Character> s3 = new BasicShip(c3);
    String expected1 = "  0|1|2|3\n" + "A s| | |  A\n" + "B  | | |  B\n"
      +"C  | | |  C\n" + "  0|1|2|3\n"; 
    String expected2 = "  0|1|2|3\n" + "A s| | |  A\n" + "B  | | |  B\n"
      +"C  | | |s C\n" + "  0|1|2|3\n";
    String expected3 = "  0|1|2|3\n" + "A s|s| |  A\n" + "B  | | |  B\n"
      +"C  | | |s C\n" + "  0|1|2|3\n";

   
    b1.tryAddShip(s1);
    shipBoardHelper(b1, 4, 3, expected1);
    b1.tryAddShip(s2);
    shipBoardHelper(b1, 4, 3, expected2);
    b1.tryAddShip(s3);
    shipBoardHelper(b1, 4, 3, expected3);

    
  }
  
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>('X',11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>('X',10, 27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));

  }

  @Test
  public void test_display_empty_3by2() {
    int w = 2;
    int h = 3;
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n" + "B  |  B\n" + "C  |  C\n";
    emptyBoardHelper(w, h, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5() {
    int w = 5;
    int h = 3;
    String expectedHeader = "  0|1|2|3|4\n";
    String expectedBody = "A  | | | |  A\n" + "B  | | | |  B\n" + "C  | | | |  C\n";
    emptyBoardHelper(w, h, expectedHeader, expectedBody);
  }

  @Test
  public void test_enemyBoard(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>('X',4, 3);
    BoardTextView view = new BoardTextView(b); 
    String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    String myEnemy =
      "  0|1|2|3\n" +
      "A  | | |  A\n" +
      "B  | | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";
    String myViewAfterFire =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B *|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n"; 
    String myEnemyAfterFire =
      "  0|1|2|3\n" +
      "A  | | |  A\n" +
      "B s| | |  B\n" +
      "C  | | |  C\n" +
      "  0|1|2|3\n";
    V1V2ShipFactory f = new V1V2ShipFactory();
    Ship<Character> sub =  f.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));
    Ship<Character> des =  f.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));
    assertEquals(true,b.tryAddShip(sub)==null);
    assertEquals(true,b.tryAddShip(des)==null);
    assertEquals(myView, view.displayMyOwnBoard());
    assertEquals(myEnemy, view.displayEnemyBoard());
    b.fireAt(new Coordinate(1, 0));
    assertEquals(myViewAfterFire, view.displayMyOwnBoard());
    assertEquals(myEnemyAfterFire, view.displayEnemyBoard());
  }

  @Test
  public void test_displayMyBoardWithEnemyNextToIt(){
    String expected =
      "     Mine                     Enemy\n"+
      "  0|1|2|3                    0|1|2|3\n" +
      "A  | | |  A                A  | | |  A\n" +
      "B  | | |  B                B  | | |  B\n" +
      "C  | | |  C                C  | | |  C\n" +
      "  0|1|2|3                    0|1|2|3\n";
    BattleShipBoard<Character> myB = new BattleShipBoard<Character>('X',4, 3);
    BattleShipBoard<Character> enemyB = new BattleShipBoard<Character>('X',4, 3);
    BoardTextView myView = new BoardTextView(myB);
    BoardTextView enemyView = new BoardTextView(enemyB);
    String combined = myView.displayMyBoardWithEnemyNextToIt(enemyView,"Mine","Enemy");
    assertEquals(expected, combined);
  }
}
