package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RefineryTest {
    private Refinery ref;
    private RobotController rc;
    private Communications comms;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        ref = new Refinery(rc);
        ref.comms = mock(Communications.class);
    }

    @Test
    public void takeTurn() {
    }

    @Test
    public void runRefinery() throws GameActionException {
        ref.runRefinery();
    }
}