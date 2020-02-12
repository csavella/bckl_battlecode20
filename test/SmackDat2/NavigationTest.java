package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import static org.mockito.Mockito.*;

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
        when(!rc.isReady()).thenReturn(false);

        for (Direction dir : Util.directions) {
            when(rc.isReady() && rc.canMove(dir) && !rc.senseFlooding(rc.getLocation().add(dir))).thenReturn(true);
        }
    }

    @Test
    public void testTryMoveFalse() throws GameActionException {
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions){
            assertEquals(false, testNav.tryMove(dir));
        }
    }
}
