package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import static org.mockito.Mockito.*;

import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    private Robot testRobot;
    private RobotController rc;

    /* mock the robot controller and
       create object for class that we're testing
     */
    @Before
    public void init() {
        rc = mock(RobotController.class);
        testRobot = new Robot(rc);
    }

    @Test
    public void takeTurn() throws GameActionException {
        testRobot.takeTurn();
        assertEquals(1, testRobot.turnCount);
    }

    /* two tests for tryBuild method
       make sure both conditional statements execute properly
     */
    @Test
    public void tryBuildFalse() throws GameActionException {
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions)
            assertEquals(testRobot.tryBuild(rc.getType(), dir), false);
    }
    @Test
    public void tryBuildTrue() throws GameActionException {
        when(rc.isReady()).thenReturn(true);
        when(rc.canBuildRobot(any(), any())).thenReturn(true);
        for (Direction dir : Util.directions)
            assertEquals(testRobot.tryBuild(rc.getType(), dir), true);
    }
}