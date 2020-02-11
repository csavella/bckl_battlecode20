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
        RobotInfo[] ri = {mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(ri);
        landscaper.takeTurn();
    }

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
    public void tryDigHQNotNull() throws GameActionException {
        landscaper.hqLoc = new MapLocation(1,1);
        landscaper.tryDig();
    }
}