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

    @Test
    public void runLandscaperNorth() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.isReady()).thenReturn(true);
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.NORTH)).thenReturn(true);
        landscaper.runLandscaper();
    }

    @Test
    public void runLandscaperSouth() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.isReady()).thenReturn(true);
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.SOUTH)).thenReturn(true);
        landscaper.runLandscaper();
    }

    @Test
    public void runLandscaperSouthEast() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.isReady()).thenReturn(true);
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.SOUTHEAST)).thenReturn(true);
        landscaper.runLandscaper();
    }

    @Test
    public void runLandscaperNorthEast() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.isReady()).thenReturn(true);
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.NORTHEAST)).thenReturn(true);
        landscaper.runLandscaper();
    }

    @Test
    public void runLandscaperSouthWest() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.isReady()).thenReturn(true);
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.SOUTHWEST)).thenReturn(true);
        landscaper.runLandscaper();
    }

    @Test
    public void runLandscaperNorthWest() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.isReady()).thenReturn(true);
        when(rc.canMove(any())).thenReturn(false);
        when(rc.canMove(Direction.NORTHWEST)).thenReturn(true);
        landscaper.runLandscaper();
    }

    @Test
    public void runLandscaperNotReadyRound151() throws GameActionException {
        when(landscaper.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(1,2));
        when(rc.isReady()).thenReturn(true);
        when(rc.getRoundNum()).thenReturn(151);
        landscaper.runLandscaper();
    }
}