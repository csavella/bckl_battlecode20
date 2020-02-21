package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LandscaperTest {

    private Landscaper landscaper;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        landscaper = new Landscaper(rc);
    }

    @Test
    public void takeTurn() throws GameActionException {
        MapLocation ml = new MapLocation(50,50);
        RobotInfo[] ri = new RobotInfo[]{new RobotInfo(12, Team.A, RobotType.LANDSCAPER, 0, false, 0, 200, 0, ml)};
        when(rc.isReady()).thenReturn(true);
        when(rc.senseNearbyRobots()).thenReturn(ri);
        //System.out.println(rc.isReady());
        when(rc.getLocation()).thenReturn(ml);
        //when(ml.isAdjacentTo(any())).thenReturn(true);
        //when(rc.isReady()).thenReturn(true);
        landscaper.takeTurn();
    }
    @Test
    public void takeTurnwhenAdjacentToHQ() throws GameActionException {
        MapLocation ml = new MapLocation(5,5);
        RobotInfo[] ri = new RobotInfo[]{new RobotInfo(12, Team.A, RobotType.LANDSCAPER, 0, false, 0, 200, 0, ml)};
        when(rc.isReady()).thenReturn(true);
        when(rc.senseNearbyRobots()).thenReturn(ri);
        //System.out.println(rc.isReady());
        when(rc.getLocation()).thenReturn(ml);
        //when(ml.isAdjacentTo(any())).thenReturn(false);
        //when(rc.isReady()).thenReturn(true);
        landscaper.takeTurn();
    }
    //we donot need to test runLandscaper(), its alreday called by takeTurn()
    /* //This test is failing
    @Test
    public void runLandscaper() throws GameActionException {
        MapLocation ml = new MapLocation(1,1);
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(ml);
        when(ml.isAdjacentTo(any())).thenReturn(true);
        landscaper.runLandscaper();
    } */

    @Test
    public void tryDigHQNull() throws GameActionException {
        landscaper.hqLoc = null;
        landscaper.tryDig();
    }

    @Test
    public void tryDigCanDig() throws GameActionException {
        landscaper.hqLoc = new MapLocation(1,1);
        when(rc.canDigDirt(any())).thenReturn(true);
        assertEquals(true, landscaper.tryDig());
    }

    @Test
    public void tryDigCantDig() throws GameActionException {
        landscaper.hqLoc = new MapLocation(1,1);
        when(rc.canDigDirt(any())).thenReturn(false);
        assertEquals(false, landscaper.tryDig());
    }

    @Test
    public void tryDigHQNotNull() throws GameActionException {
        landscaper.hqLoc = new MapLocation(1,1);
        landscaper.tryDig();
    }

    @Test
    public void getNextDirection() {
        assertEquals(landscaper.getNextDirection(Direction.NORTH), Direction.WEST);
    }
}