package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VaporatorTest {
    private Vaporator vap;
    private RobotController rc;
    private Communications coms;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        vap = new Vaporator(rc);
        coms = mock(Communications.class);
    }

    @Test
    public void takeTurn() throws GameActionException{
        when(coms.vaporatorExists()).thenReturn(true);
        vap.takeTurn();
    }

}