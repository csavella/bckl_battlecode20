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

    public Robot testRobot;
    public RobotController rc;
    public Navigation testNav;

    @Before
    public void init() {
        rc = mock(RobotController.class);
        testRobot = new Robot(rc);
        testNav = new Navigation(rc);

    }

    @Test
    public void testTryMoveTrue() throws GameActionException {
        //when(rc.isReady()).thenReturn(true);
        //when(rc.isReady() && rc.canMove(Direction.NORTH) && !rc.senseFlooding(rc.getLocation().add(Direction.NORTH))).thenReturn(true);
        //System.out.println(rc.senseFlooding(rc.getLocation().add(Direction.NORTH)));
        System.out.println(rc.canMove((Direction.NORTH)));
        //assertEquals(true, testNav.tryMove(Util.randomDirection()));

        //for (Direction dir : Util.directions) {
           // when(rc.isReady() && rc.canMove(dir) && !rc.senseFlooding(rc.getLocation().add(dir))).thenReturn(true);
            //assertEquals(true, testNav.tryMove(dir));
        //}
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
        MapLocation ml = new MapLocation(5,5);
        //Direction dir;
        //Direction[] toTry = {dir, dir.rotateLeft(), dir.rotateRight(), dir.rotateLeft().rotateLeft(), dir.rotateRight().rotateRight()};
        for (Direction dir: Util.directions){
            when(testNav.tryMove(dir)).thenReturn(false);
           // System.out.println(testNav.tryMove((dir)));
            assertEquals(false,testNav.goTo(dir));
        }
       // testNav.goTo(ml);

    }
    /*@Test
    public void testgoToTrue() throws GameActionException{
        for (Direction dir: Util.directions){
            Direction[] toTry = {dir, dir.rotateLeft(), dir.rotateRight(), dir.rotateLeft().rotateLeft(), dir.rotateRight().rotateRight()};
            //when(testNav.tryMove(d)).thenReturn(true);
            assertEquals(true,testNav.goTo(toTry[1]));
        }
    } */
}
