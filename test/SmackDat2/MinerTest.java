package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MinerTest {

    private Miner m;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        m = new Miner(rc);
    }

    @Test
    public void tryMineFalse() throws GameActionException {
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions)
            assertEquals(m.tryMine(dir), false);
    }

    @Test
    public void tryMineTrue() throws GameActionException {
        when(rc.isReady()).thenReturn(true);
        when(rc.canMineSoup(any())).thenReturn(true);
        for (Direction dir : Util.directions)
            assertEquals(m.tryMine(dir), true);
    }

    @Test
    public void tryRefineFalse() throws GameActionException{
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions)
            assertEquals(m.tryRefine(dir), false);
    }

    @Test
    public void tryRefineTrue() throws GameActionException {
        when(rc.isReady()).thenReturn(true);
        when(rc.canDepositSoup(any())).thenReturn(true);
        for (Direction dir : Util.directions)
            assertEquals(m.tryRefine(dir), true);
    }

    @Test
    public void checkIfSoupGoneEmpty() throws GameActionException{
        m.checkIfSoupGone();
    }

    @Test
    public void checkIfSoupGone() throws GameActionException{
        MapLocation ml = new MapLocation(1,1);
        m.soupLocations.add(ml);
        when(rc.canSenseLocation(any())).thenReturn(true);
        when(rc.senseSoup(any())).thenReturn(0);
        m.checkIfSoupGone();
    }
}