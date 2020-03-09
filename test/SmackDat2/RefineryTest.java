package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RefineryTest {
    private Refinery ref;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        ref = new Refinery(rc);
        ref.comms = mock(Communications.class);
    }

    @Test
    public void takeTurn() throws GameActionException{
        ref.takeTurn();
    }

    @Test
    public void runRefineryTurn50() throws GameActionException {
        when(rc.getRoundNum()).thenReturn(50);
        ref.runRefinery();
    }

    @Test
    public void runRefineryTurn49() throws GameActionException {
        when(rc.getRoundNum()).thenReturn(49);
        ref.runRefinery();
    }

    @Test
    public void runRefineryExists() throws GameActionException {
        when(ref.comms.refineryExists()).thenReturn(true);
        ref.runRefinery();
    }
}