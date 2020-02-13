package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HQTest {

    private HQ hqtest;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        hqtest = new HQ(rc);
        hqtest.comms = mock(Communications.class);
    }

    @Test
    public void takeTurn() throws GameActionException {
        when(rc.getTeam()).thenReturn(Team.A);
        hqtest.takeTurn();
    }
}