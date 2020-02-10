package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import static org.mockito.Mockito.*;

import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NavigationTest {

    private Robot testRobot;
    private RobotController rc;
    private Navigation testNav;

    @Before
    public void init() {
        rc = mock(RobotController.class);
        testRobot = new Robot(rc);
        testNav = new Navigation(rc);
    }

    @Test
    public void testTryMove() throws GameActionException {
        for (Direction dir : Util.directions) {
            assertEquals(testNav.tryMove(dir), true);
        }
    }
}
