package edu.duke.xs75.battleship;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {
  private ComputerPlayer createComputerPlayer(){
    Board<Character> b1 = new BattleShipBoard<Character>('X', 10, 20);
    V1V2ShipFactory factory = new V1V2ShipFactory();
    ComputerPlayer p = new ComputerPlayer("A", b1, System.out, factory);
    return p;
  }
  
  @Test
  public void test_genCoord() {
    ComputerPlayer player = createComputerPlayer();
    Coordinate c = player.generateACoord();
    assertEquals(true, c!= null);
  }
  @Test
  public void test_readPlacement()throws IOException{
     ComputerPlayer player = createComputerPlayer();
   
   
       Placement p = player.readPlacement(4);
       assertEquals('U', p.getOrientation());
       Placement p2 = player.readPlacement(2);
       assertEquals('H', p2.getOrientation());
  }

  @Test
  public void test_readActions()throws IOException{
    int times = 10;
    while(times > 0){
      times--;
      ComputerPlayer player = createComputerPlayer();
      char c = player.readActions();
      assertEquals(true, c == 'M'||c=='S' || c == 'F');
    }
  }
}
