package edu.duke.xs75.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.EOFException;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("B2V\nC8H\na4v\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>('X', 10, 20);

    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1V2ShipFactory());
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt,2);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  /**
   * This is an abstract function to create a Textplayer
   *
   */
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>('X', w, h);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  @Test
  void test_doOnePlacementTwoConsecutively() throws IOException {
    String expectedPrompt = "Player A where would you like to put your Destroyer?\n";
    String displayBoard ="Current ocean:\n"+ "  0|1|2|3\n" + "A d|d|d|  A\n" + "B  | | |  B\n" + "C  | | |  C\n" + "  0|1|2|3\n";
    String allDisplay = "";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(4, 3, "A0H\nB0H\n", bytes);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    allDisplay = expectedPrompt + displayBoard + "\n";
    assertEquals(allDisplay, bytes.toString());
    player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    String displayBoard1 ="Current ocean:\n" + "  0|1|2|3\n" + "A d|d|d|  A\n" + "B d|d|d|  B\n" + "C  | | |  C\n" + "  0|1|2|3\n";
    allDisplay += expectedPrompt + displayBoard1 + "\n";
    assertEquals(allDisplay, bytes.toString());
  }

  /**
   * This helps testing doOnepalcementSingle It is an abstract of the whole process
   * 
   * @param p          is the Placement you want to add
   * @param w          is the width of the board,
   * @param h          is the height of the board
   * @param Alldisplay is the textview of the board after do one placement and the
   *                   prompt
   */
  private void doOnePlacementHelper(String placement, int num, int w, int h, String allDisplay) throws IOException {
    StringReader sr = new StringReader(placement);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>('X', w, h);
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1V2ShipFactory());
    player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    assertEquals(allDisplay, bytes.toString());
  }

  @Test
  void test_doOnePlacementSingle() throws IOException {
    String expectedPrompt = "Player A where would you like to put your Destroyer?\n";
    String displayBoard ="Current ocean:\n"+ "  0|1|2|3\n" + "A d|d|d|  A\n" + "B  | | |  B\n" + "C  | | |  C\n" + "  0|1|2|3\n";
    doOnePlacementHelper("A0H", 1, 4, 3, expectedPrompt + displayBoard + "\n");
    String displayBoard1 ="Current ocean:\n"+ "  0|1|2|3\n" + "A  | | |d A\n" + "B  | | |d B\n" + "C  | | |d C\n" + "  0|1|2|3\n";
    doOnePlacementHelper("A3v", 1, 4, 3, expectedPrompt + displayBoard1 + "\n");

  }

  @Test
  void test_invalid_doOnePlacement() throws IOException{
    String expectedP1 = "Player A where would you like to put your Destroyer?\n";
    String displayBoard1 ="Current ocean:\n"+ "  0|1|2|3\n" + "A  | | |d A\n" + "B  | | |d B\n" + "C  | | |d C\n" + "  0|1|2|3\n";
    String feedback = " Please enter your Placement again.\n";
    String expectedP2 = "That placement is invalid: the ship goes off the bottom of the board." + feedback;
    String expectedP3 = "java.lang.IllegalArgumentException: The length of String input of Placement must be 3 but is 2" + feedback;   
    doOnePlacementHelper("T0v\nA3v\n", 1, 4, 3,  expectedP1 + expectedP2 +  displayBoard1 + "\n");
    assertThrows(EOFException.class, () -> doOnePlacementHelper("", 1, 4, 3, "1"));
    doOnePlacementHelper("0v\nT0v\nA3v\n", 1, 4, 3,  expectedP1 + expectedP3 + expectedP2 +  displayBoard1 + "\n");

    
  }
  @Test
  void test_readCoordinate() throws IOException {
    String expectedPrompt = "Player A where would you like to fire at?\n";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(4, 3, "A0\nB0\nT0\nA9\n", bytes);
    Coordinate expectedC1 = new Coordinate(0, 0);
    Coordinate expectedC2 = new Coordinate(1, 0);
    assertEquals(expectedC1, player.readCoordinate(expectedPrompt));
    assertEquals(expectedC2, player.readCoordinate(expectedPrompt));
    //coordinates out of bounds
    assertThrows(IllegalArgumentException.class, () -> player.readCoordinate(expectedPrompt));
    assertThrows(IllegalArgumentException.class, () -> player.readCoordinate(expectedPrompt));
    TextPlayer player1 = createTextPlayer(4, 3, "", bytes); // null from readline
    assertThrows(EOFException.class, () ->player1.readCoordinate(expectedPrompt));
  }

  /*
  @Test
  public void test_playerOneTurn() throws IOException{
    String expected = "Player A's turn:\n"+
      "     Mine                     Enemy\n"+
      "  0|1|2|3                    0|1|2|3\n" +
      "A  | | |  A                A s| | |  A\n" +
      "B *| | |  B                B X| | |  B\n" +
      "C s| | |  C                C  | | |  C\n" +
      "  0|1|2|3                    0|1|2|3\n";
    String expected2 = "Player A's turn:\n"+
      "     Mine                     Enemy\n"+
      "  0|1|2|3                    0|1|2|3\n" +
      "A  | | |  A                A s| | |  A\n" +
      "B *| | |  B                B X| | |  B\n" +
      "C s| | |  C                C X| | |  C\n" +
      "  0|1|2|3                    0|1|2|3\n"; 
    String prompt = "Player A where do you want to fire at?\n";
    BattleShipBoard<Character> myBoard = new BattleShipBoard<Character>('X',4, 3);
    BoardTextView myView = new BoardTextView(myBoard); 
    BattleShipBoard<Character> enemyBoard = new BattleShipBoard<Character>('X',4, 3);
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    StringReader sr = new StringReader("A0H\nB0");
    StringReader mySr = new StringReader("B0v\nA0\nB0\nSS\nc0\na1\n");
    PrintStream ps = new PrintStream(bytes, true);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    TextPlayer enemyPlayer = new TextPlayer("B", enemyBoard, new BufferedReader(sr), ps, new V1V2ShipFactory());    
    TextPlayer myPalyer = new TextPlayer("A", myBoard, new BufferedReader(mySr), ps, new V1V2ShipFactory());
    enemyPlayer.doOnePlacement("Submarine", (p) -> shipFactory.makeSubmarine(p));
    myPalyer.doOnePlacement("Submarine", (p) -> shipFactory.makeSubmarine(p));
    enemyPlayer.playOneTurn(myBoard, myView, "Mine","Enemy");
    myPalyer.playOneTurn(enemyBoard, enemyView, "Mine", "Enemy");  
    myPalyer.playOneTurn(enemyBoard, enemyView, "Mine", "Enemy");
    bytes.reset();
    myPalyer.playOneTurn(enemyBoard, enemyView, "Mine", "Enemy");
    assertEquals(expected + "\n" + prompt + "java.lang.IllegalArgumentException: The second axis of coordinate should be a number. Please enter your Coordinate again.\n" + "You missed!\n\n", bytes.toString());
    bytes.reset();
    myPalyer.playOneTurn(enemyBoard, enemyView, "Mine", "Enemy");
    assertEquals(expected2 + "\n" + prompt + "You hit a Submarine!\n\n", bytes.toString());
    bytes.reset();        
  }*/
  
  @Test
  public void test_isLost_declareWinner() throws IOException{
    StringReader sr = new StringReader("A0H\nA0\nA1\n");
    BattleShipBoard<Character> myBoard = new BattleShipBoard<Character>('X',4, 3);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true); 
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    TextPlayer myPlayer = new TextPlayer("A", myBoard, new BufferedReader(sr), ps, new V1V2ShipFactory());
    myPlayer.doOnePlacement("Submarine", (p) -> shipFactory.makeSubmarine(p));
    myBoard.fireAt(new Coordinate(0,0));
    assertEquals(false, myPlayer.isLost());
    myBoard.fireAt(new Coordinate(0,1));
    assertEquals(true, myPlayer.isLost());
    bytes.reset();
    myPlayer.declareWinner();
    assertEquals("Player A you are the winner!\n", bytes.toString());
  }

  @Test
  public void test_tryMoveShip()throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "A0U\nG0R\nB1\nD0D\nH0\nD0D\n", bytes);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    player.doOnePlacement("Battleship", (p) -> shipFactory.makeBattleship(p));
    player.doOnePlacement("Carrier",(p) -> shipFactory.makeCarrier(p));
    player.theBoard.fireAt(new Coordinate("A1"));
    assertEquals(null,player.tryMoveShip());
    Ship<Character> newShip = player.theBoard.findShip(new Coordinate(3,0));
    assertEquals(Integer.valueOf(1),newShip.getIntFromCoord(new Coordinate(4,1)));
    assertEquals(null,player.theBoard.whatIsAtForSelf(new Coordinate("A1")));
    assertEquals('b',player.theBoard.whatIsAtForEnemy(new Coordinate("A1"))); 
    assertEquals('*',player.theBoard.whatIsAtForSelf(new Coordinate("E1")));
    assertEquals(null,player.theBoard.whatIsAtForEnemy(new Coordinate("E1")));
    assertEquals(true, player.tryMoveShip() != null);
    Ship<Character> carr = player.theBoard.findShip(new Coordinate("G3"));
    assertEquals(Integer.valueOf(1),carr.getIntFromCoord(new Coordinate("H0")));    
  }


  @Test
  public void test_tryFireAship()throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "A0U\nG0R\nB1\nT0\nAA\n", bytes);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    player.doOnePlacement("Battleship", (p) -> shipFactory.makeBattleship(p));
    player.doOnePlacement("Carrier",(p) -> shipFactory.makeCarrier(p));
    assertEquals(null, player.tryFireAShip(player.theBoard));
    assertEquals(null, player.tryFireAShip(player.theBoard));
    assertEquals(true, player.tryFireAShip(player.theBoard) != null);
        
  }

  @Test
  public void test_readActions()throws IOException{
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
  TextPlayer player = createTextPlayer(10, 20, "F\nM\nf\n", bytes);
  assertEquals('F',player.readActions("enter"));
  assertEquals('M',player.readActions("enter"));
  assertEquals('F',player.readActions("enter"));
  }

  @Test
  public void test_tryScanShip()throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "a0U\nc0U\nk0H\nt0H\na0\nF\n", bytes);
    V1V2ShipFactory shipFactory = new V1V2ShipFactory();
    player.doOnePlacement("Battleship", (p) -> shipFactory.makeBattleship(p));
    player.doOnePlacement("Carrier", (p) -> shipFactory.makeCarrier(p));
    player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    player.doOnePlacement("Submarine", (p) -> shipFactory.makeSubmarine(p));
    assertEquals(null, player.tryScanShips(player.theBoard));
    assertEquals(true, player.tryScanShips(player.theBoard)!= null);
  }
}












