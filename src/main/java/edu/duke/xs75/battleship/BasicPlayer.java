package edu.duke.xs75.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
import java.util.Random;

/**
 * This is the computer player class
 */
public abstract class BasicPlayer implements Player {
  protected final Board<Character> theBoard;
  protected final BoardTextView view;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  protected final String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

  /**
   * This constructs a textPlayer
   */
  public BasicPlayer(String name, Board<Character> b, PrintStream out, AbstractShipFactory<Character> shipFactory) {
    this.theBoard = b;
    this.view = new BoardTextView(b);
    this.out = out;
    this.shipFactory = shipFactory;
    this.name = name;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    setupShipCreationList();
  }
  /**
   * Get the myownboard
   * 
   * @return theboard of the player
   */
  @Override
  public Board<Character> getBoard() {
    return this.theBoard;
  }

  /**
   * Get the textview of myownboard
   * 
   * @return view of the player
   */
  @Override
  public BoardTextView getView() {
    return this.view;
  }

  /**
   * Get the name of player
   * 
   * @return the name of the player
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * This initializes the Hash map from shipName to creationFn can creat this ship
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
   * This initlizes the shipsToPlace list
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  /**
   * This check if a play er lose the game or not.
   * 
   * @return true if a player is lost, false otherwise.
   */
  @Override
  public boolean isLost() {
    return theBoard.isAllSunk();
  }

  /**
   * declare the player is winner if he/she wins the game
   */
  @Override
  public void declareWinner() {
    out.println("Player " + this.name + " you are the winner!");
  }


}
