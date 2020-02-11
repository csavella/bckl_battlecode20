package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.Team;
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
        when(rc.getTeam()).thenReturn(Team.A);
        shoot.takeTurn();
    }
}