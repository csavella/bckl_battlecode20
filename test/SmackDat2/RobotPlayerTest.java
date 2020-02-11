package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RobotPlayerTest {

    private RobotPlayer rp;
    private RobotController rc;
    private Communications comms;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        rp = new RobotPlayer();
        comms = mock(Communications.class);
    }

    @Test
    public void run() throws GameActionException {
        //MapLocation ml = new MapLocation(1,1);
        //when(rc.getType()).thenReturn(RobotType.MINER);
        //doNothing().when(comms).sendHqLoc(ml);
        //rp.run(rc);
    }
}