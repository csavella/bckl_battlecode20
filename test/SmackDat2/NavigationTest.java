package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import static org.mockito.Mockito.*;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NavigationTest {

    public RobotController rc;
    public Navigation testNav;

    @Before
    public void init() {
        rc = mock(RobotController.class);
        testNav = new Navigation(rc);
    }

    @Test
    public void testTryMoveTrue() throws GameActionException {
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : Util.directions)
            when(rc.canMove(dir)).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(1,1));
        when(rc.senseFlooding(any())).thenReturn(false);
        for(Direction dir : Util.directions)
            assertEquals(true, testNav.tryMove(dir));
    }

    @Test
    public void testTryMoveFalse() throws GameActionException {
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions){
            assertEquals(false, testNav.tryMove(dir));
        }
    }
    @Test
    public void testgoToFalse() throws GameActionException{
        //Direction dir;
        //Direction[] toTry = {dir, dir.rotateLeft(), dir.rotateRight(), dir.rotateLeft().rotateLeft(), dir.rotateRight().rotateRight()};
        for (Direction dir: Util.directions){
            when(testNav.tryMove(dir)).thenReturn(false);
           // System.out.println(testNav.tryMove((dir)));
            assertEquals(false,testNav.goTo(dir));
        }
       // testNav.goTo(ml);

    }

}
