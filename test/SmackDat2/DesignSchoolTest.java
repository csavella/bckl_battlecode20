package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DesignSchoolTest {

    private DesignSchool dsTest;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        RobotController rc = mock(RobotController.class);
        dsTest = new DesignSchool(rc);
    }

    @Test
    public void takeTurn() throws GameActionException {
        dsTest.comms.broadcastedCreation = true;
        dsTest.takeTurn();
    }
}