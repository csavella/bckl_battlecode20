package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FulfillmentCenterTest {

    private FulfillmentCenter fc;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        fc = new FulfillmentCenter(rc);
        fc.comms = mock(Communications.class);
    }

    @Test
    public void takeTurn() throws GameActionException {
        fc.takeTurn();
    }

    @Test
    public void takeTurnExists() throws GameActionException {
        when(fc.comms.fulfillmentCenterExists()).thenReturn(true);
        fc.takeTurn();
    }

    @Test
    public void takeTurnRound101() throws GameActionException {
        when(fc.comms.fulfillmentCenterExists()).thenReturn(true);
        when(rc.getRoundNum()).thenReturn(101);
        fc.takeTurn();
    }

}