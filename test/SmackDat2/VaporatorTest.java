package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VaporatorTest {
    private Vaporator vap;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        vap = new Vaporator(rc);
        vap.comms = mock(Communications.class);
    }

    @Test
    public void takeTurnTrue() throws GameActionException{
        when(vap.comms.vaporatorExists()).thenReturn(true);
        vap.takeTurn();
    }

    @Test
    public void takeTurnFalse() throws GameActionException{
        when(vap.comms.vaporatorExists()).thenReturn(false);
        vap.takeTurn();
    }
}