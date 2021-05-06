package edu.duke.xs75.battleship;

import java.io.IOException;

public interface Player {

  /**
   * This mainly handles all phase a player needs
   */
  public void doPlacementPhase() throws IOException;

  /**
   * This enables player to play one turn: select a coordinate to fire at and
   * display the result to the player after the attacking.
   * 
   * @param enemyBoard  is enemy board
   * @param enemyView   is the enemy boardview
   * @param myHeader    is the header for the user
   * @param enemyHeader is the header for the enemy
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException;

  /**
   * declare the player is winner if he/she wins the game
   */
  public void declareWinner();

  /**
   * Get the myownboard
    * @return theboard of the player
   */
  public Board<Character> getBoard();

  /**
   * get the textview of thr board
      * @return view of the player
   */
  public BoardTextView getView();

  /**
   * get player's name
   * @return the name of the player
   */
  public String getName();

    /**
   * This check if a play er lose the game or not.
   * 
   * @return true if a player is lost, false otherwise.
   */
  public boolean isLost();

}












