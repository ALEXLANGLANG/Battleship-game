package edu.duke.xs75.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class handles how each player interacts with the game
 */

public class TextPlayer extends BasicPlayer{
  final BufferedReader inputReader;
  protected int[] counter;
  
  /**
   * This constructs a textPlayer
   */
  public TextPlayer(String name, Board<Character> b, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    super(name,b,out,shipFactory);
    this.inputReader = inputSource;
    this.counter = new int[] { 3, 3 };
  }

  /**
   * This handles the placement to place the ship, after users input
   * 
   * @param prompt, the prompt shown on the beigning to ask users to give a string
   *                format of the placement
   * @param types   is the number of how to place the ship. 2 (v,h)or 4(u,d,l,r)
   * @return a Placement that users input
   */
  public Placement readPlacement(String prompt, int types) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("Nothing read from the terminal.");
    }
    return new Placement(s, types);
  }

  /**
   * This handles the operation from users to add a ship to the board it will read
   * a line from users and parse it as a placement and print it out
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {

    String prompt = "Player " + this.name + " where would you like to put your " + shipName + "?";
    String feedback = " Please enter your Placement again.";
    String res = " ";
    int types = 2;
    while (res != null) {
      try {
        if (shipName == "Battleship" || shipName == "Carrier") {
          types = 4;
        }
        Placement p = readPlacement(prompt, types);
        Ship<Character> s = createFn.apply(p);
        res = theBoard.tryAddShip(s);
        if (res != null) {
          prompt = res + feedback;
        }
      } catch (IllegalArgumentException illegalArg) {
        prompt = illegalArg + feedback;
      }
    }
    String displayBoard = view.displayMyOwnBoard();
    out.print("Current ocean:\n");
    out.println(displayBoard);
  }

  /**
   * This mainly handles all phase a player needs
   */
  public void doPlacementPhase() throws IOException {
    out.println(this.view.displayMyOwnBoard());
    String instructions = "Player " + this.name
        + ": you are going to place the following ships. For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical) for Submarines and Destroyers, and followed by U (up), R (right), L (left) or D (down) for Battleships and Carriers.  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n\n";
    String list_ships =
      "2 \"Submarines\" ships that are 1x2 rectangles (represented by \"s\")\n" +
      "3 \"Destroyers\" that are 1x3 rectangles (represented by \"d\")\n" +
      "3 \"Battleships\" that are shown as below\n" +
      "        b      OR    b         bbb          b\n"+
      "        bbb          bb   OR    b     OR   bb\n"+
      "                     b                      b\n"+
      " \n"+
      "        Up          Right      Down        Left\n"+
      "2 \"Carriers\" that are shown as below\n"+
      "        c                        c\n"+            
      "        c            ccc         c\n"+         
      "        cc   OR    ccc      OR  cc     OR  ccc\n"+     
      "        c                       c            ccc\n"+ 
      "        c                       c\n"+
      "             \n"+
      "        Up          Right      Down        Left\n";
    out.println(instructions + list_ships);
    for (int i = 0; i < shipsToPlace.size(); i++) {
      doOnePlacement(shipsToPlace.get(i), shipCreationFns.get(shipsToPlace.get(i)));
    }
  }

  /**
   * This checks if a coorinate is out of bounds. If it out of bounds, throw
   * expcetions
   */

  private void checkRuleCoordinate(Coordinate where) {
    if (where.getRow() >= this.theBoard.getHeight()) {
      throw new IllegalArgumentException(
          "Row of coordinate must be within " + theBoard.getHeight() + "but is " + where.getRow());
    }
    if (where.getColumn() >= this.theBoard.getWidth()) {
      throw new IllegalArgumentException(
          "Column of coordinate must be within " + theBoard.getWidth() + "but is " + where.getColumn());
    }

  }

  /**
   * This handles the Coordinate to attack the ship, after users input It will be
   * used in Player's turn to do attacking
   * 
   * @param prompt, the prompt shown on the beigning to ask users to give a string
   *                format of the Coordinate
   * @throws IOExpection if coordinate is out of bounds
   * @return a Placement that users input
   */
  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("Nothing read from the terminal.");
    }
    Coordinate where = new Coordinate(s);
    checkRuleCoordinate(where);
    return where;
  }

  public char readActions(String prompt) throws IOException {
    out.println(prompt);
    String act = inputReader.readLine();
    if (act.length() != 1) {
      throw new IllegalArgumentException("Actions must be one letter.");
    }
    act = act.toUpperCase();
    char s = act.charAt(0);
    if (s != 'F' && s != 'M' && s != 'S') {
      throw new IllegalArgumentException("Actions must be \'f\',\'F\', \'m\', \'M\', \'s\' or \'S\'.");
    }
    return s;
  }

  /**
   * This enables player to play one turn: select a coordinate to fire at and
   * display the result to the player after the attacking.
   * 
   * @param enemyBoard is enemy board
   * @param enemyView  is the enemy boardview
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    String allDisplay = view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader);
    out.println("Player " + this.name + "'s turn:");
    out.println(allDisplay);

    String res = "Invalid action.";
    while (res != null) {
      out.println("Possible actions for Player "+this.name+ ":\n\n" + " F Fire at a square");
      String actMove = " M Move a ship to another square (" + counter[0] + " remaining)";
      String actSonar = " S Sonar scan (" + counter[1] + " remaining)";
      if (counter[0] > 0) {
        out.println(actMove);
      }
      if (counter[1] > 0) {
        out.println(actSonar);
      }
      try {
        char s = readActions("\nPlayer " + this.name + " what would you like to do?");
        if (s == 'F') {
          res = tryFireAShip(enemyBoard);
        } else if (counter[0] > 0 && s == 'M') {
          res = tryMoveShip();
        } else if (counter[1] > 0 && s == 'S') {
          res = tryScanShips(enemyBoard);
        }
        if (res != null) {
          out.println(res);
        } else if (res == null && s == 'M') {
          counter[0]--;
        } else if (res == null && s == 'S') {
          counter[1]--;
        }
      } catch (IllegalArgumentException illegalArg) {
        out.println(illegalArg);
      }
    }

  }


  /**
   * this tries to move one ship in my own board to another
   * 
   * @return null if succeed, an error string otherwise.
   */
  public String tryMoveShip() throws IOException {
    String prompt1 = "Player " + this.name + " which ship would you like to move ?";
    String prompt2 = "Player " + this.name + " where would you like to move your ship ?";
    int types = 2;

    Ship<Character> toAdd = null;
    HashMap<Coordinate, Character> masksToAdd = null;
    // remove ship
    Coordinate where = readCoordinate(prompt1);
    Ship<Character> toRemove = theBoard.findShip(where);
    HashMap<Coordinate, Character> masksToRemove = theBoard.getMasks(toRemove.getCoordinates());
    theBoard.removeShip(toRemove);
    // try to Add the new ship, if fails, add the original ship back
    try {
      String shipName = toRemove.getName();
      if (shipName == "Carrier" || shipName == "Battleship") {
        types = 4;
      }
      Function<Placement, Ship<Character>> createFn = shipCreationFns.get(shipName);
      Placement p = readPlacement(prompt2, types);
      toAdd = createFn.apply(p);
      toAdd.copyInfoFrom(toRemove);
      // theBoard.addMaskToEnemyBoard(toAdd.getCoordinates());
      masksToAdd = theBoard.getMasks(toAdd.getCoordinates());
      String res = theBoard.tryAddShip(toAdd);
      if (res != null) {
        throw new IllegalArgumentException(res);
      }
    } catch (IllegalArgumentException illegalArg) {
      theBoard.tryAddShip(toRemove);
      return illegalArg + " Go back to menu again.\n";
    }

    theBoard.addMaskToEnemyBoard(masksToAdd);
    theBoard.addMaskToEnemyBoard(masksToRemove);
    return null;
  }

  /**
   * Try to use sonar to scan an area in enemy's board
   * 
   * @param enemyBoard is the enemy's board
   * @return null if succeed, a error string otherwise
   */

  public String tryScanShips(Board<Character> enemyBoard) throws IOException {
    String prompt = "Player " + this.name + " Please enter a coordinate as the center of the sonar scanner";
    Coordinate where = null;
    try {
      where = readCoordinate(prompt);
    } catch (IllegalArgumentException illegalArg) {
      return illegalArg + " Go back to menu again.\n";
    }

    HashMap<String, Integer> map = enemyBoard.scanWithSonarAt(where);
    String scanResult = "Submarines occupy " + map.get("Submarine").toString() + " squares\n" + "Destroyers occupy "
        + map.get("Destroyer").toString() + " squares\n" + "Battleships occupy " + map.get("Battleship").toString()
        + " squares\n" + "Carriers occupy " + map.get("Carrier").toString() + " squares\n";
    out.println(scanResult);
    return null;
  }

  /**
   * Try to fire a ship on enemyBoard
   * 
   * @param enemyBoard is the enemy's board
   * @return null if succeed, a error string otherwise
   */
  public String tryFireAShip(Board<Character> enemyBoard) throws IOException {
    String prompt = "Player " + this.name + " where do you want to fire at?";
    Coordinate where = null;
    try {
      where = readCoordinate(prompt);
    } catch (IllegalArgumentException illegalArg) {
      return illegalArg + " Go back to menu again.\n";
    }
    Ship<Character> ship = enemyBoard.fireAt(where);
    if (ship == null) {
      out.println("You missed!\n");
    } else {
      out.println("You hit a " + ship.getName() + "!\n");
    }

    return null;
  }

}
