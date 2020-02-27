package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShooterTest {

    private Shooter shoot;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        shoot = new Shooter(rc);
    }

    @Test
    public void takeTurn() throws GameActionException {
        RobotInfo[] ri = new RobotInfo[]{mock(RobotInfo.class)};
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, Team.B)).thenReturn(ri);
        shoot.takeTurn();
    }
}