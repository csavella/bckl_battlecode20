package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.Team;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeliveryDroneTest {

    private DeliveryDrone ddTest;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        ddTest = new DeliveryDrone(rc);
    }

    @Test
    public void takeTurnHoldingUnit() throws GameActionException {
        RobotInfo[] ri = {mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(ri);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.isCurrentlyHoldingUnit()).thenReturn(true);
        ddTest.takeTurn();
    }

    @Test
    public void takeTurnDoNothing() throws GameActionException {
        RobotInfo[] ri = {mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(ri);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(false);
        ddTest.takeTurn();
    }

    @Test
    public void runDeliveryDrone() throws GameActionException{
    }
}