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
        landscaper.comms = mock(Communications.class);
    }
/*
    @Test
    public void takeTurn() throws GameActionException {
        MapLocation ml = new MapLocation(50,50);
        RobotInfo[] ri = new RobotInfo[]{new RobotInfo(12, Team.A, RobotType.LANDSCAPER, 0, false, 0, 200, 0, ml)};
        when(rc.isReady()).thenReturn(true);
        when(rc.senseNearbyRobots()).thenReturn(ri);
        when(rc.getLocation()).thenReturn(ml);
        landscaper.takeTurn();
    }
    @Test
    public void takeTurnwhenAdjacentToHQ() throws GameActionException {
        MapLocation ml = new MapLocation(5,5);
        RobotInfo[] ri = new RobotInfo[]{new RobotInfo(12, Team.A, RobotType.LANDSCAPER, 0, false, 0, 200, 0, ml)};
        when(rc.isReady()).thenReturn(true);
        when(rc.senseNearbyRobots()).thenReturn(ri);
        when(rc.getLocation()).thenReturn(ml);
        landscaper.takeTurn();
    }
*/

    @Test
    public void runLandscaperNotReady() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isReady()).thenReturn(false);
        landscaper.runLandscaper();
    }

    //ready and can move
    @Test
    public void runLandscaperReady1() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.canMove(any())).thenReturn(true);
        landscaper.runLandscaper();
    }

    //ready and can't move
    @Test
    public void runLandscaperReady2() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.canMove(any())).thenReturn(false);
        landscaper.runLandscaper();
    }

    //rc.canMove(Direction.EAST) == true
    @Test
    public void runLandscaperReady3() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.EAST)).thenReturn(true);
        landscaper.runLandscaper();
    }

    //else == true
    @Test
    public void runLandscaperReady4() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.EAST)).thenReturn(false);
        when(rc.canMove(Direction.WEST)).thenReturn(true);
        landscaper.runLandscaper();
    }

    //adjacent to hq
    @Test
    public void runLandscaperReady5() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1, 1));
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(1, 2));
        landscaper.runLandscaper();
    }

    //rc.getDirtCarrying() == 0
    @Test
    public void runLandscaperReady6() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1, 1));
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(1, 2));
        when(rc.getDirtCarrying()).thenReturn(0);
        when(rc.canDigDirt(any())).thenReturn(true);
        landscaper.runLandscaper();
    }

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
        assertEquals(landscaper.getNextDirection(Direction.WEST), Direction.SOUTH);
        assertEquals(landscaper.getNextDirection(Direction.SOUTH), Direction.EAST);
        assertEquals(landscaper.getNextDirection(Direction.EAST), Direction.NORTH);

    }
}