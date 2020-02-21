package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnitTest {

    private Unit testUnit;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        testUnit = new Unit(rc);
    }

    @Test
    public void takeTurn() throws GameActionException {
        when(rc.senseNearbyRobots()).thenReturn(new RobotInfo[]{new RobotInfo(101, Team.A,RobotType.HQ,0,false,0,0,0,new MapLocation(1,1))});
        testUnit.takeTurn();
    }

    @Test
    public void findHQ() throws GameActionException {
        MapLocation ml = new MapLocation(1,1);
        testUnit.hqLoc = ml;
        testUnit.findHQ();
    }
}