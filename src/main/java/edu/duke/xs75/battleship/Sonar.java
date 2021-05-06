package edu.duke.xs75.battleship;
import java.util.HashSet;
import java.lang.Math;
import java.util.HashMap;

/**
 * This class handles how to scan ships in the sonar range
*/
public class Sonar<T> {
  protected HashSet<Coordinate> sonarMap;

  final int w;
  final int h;

  /** 
   * This construct the sonar
   * @param width is the width of the board
   * @param height is the height of the board    
*/
  public Sonar(int width, int height){
    w = width;
    h = height;
    sonarMap = new HashSet<Coordinate>();
  }
  /**
   * This creates a sonar map based on a center coordinate.
   * @param where is the center cooridnate
   * @param w is the width of the board
   * @param h is the height of the board
*/
  public void createSonarMap(Coordinate where){
    sonarMap.clear();
    int [] list_len = {0,1,2,3,2,1,0};
    int r0 = where.getRow();
    int c0 = where.getColumn();
    for(int r = Math.max(r0-3,0); r <= Math.min(r0+3,h-1); r++){
      int index = r - (r0 -3);
      int l = list_len[index];
      for( int c = Math.max(c0-l,0); c<= Math.min(c0+l, w-1); c++){
        sonarMap.add(new Coordinate(r,c));
      }
    }
  }

  /** 
   * This scans the ship to see if it is within the range of sonar
   * @param ship is the ship to be scaned
   * @return num is the number of squares of the ship in the sonar's range   
*/
  public int scanShip(Ship<T> ship){
    int num = 0;
    for(Coordinate c : ship.getCoordinates()){
      if(sonarMap.contains(c)){
        num++;
      }
    }
    return num;
  }
}








