package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeliveryDroneTest {

    private DeliveryDrone ddTest;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        ddTest = new DeliveryDrone(rc);
        ddTest.comms = mock(Communications.class);
    }

    @Test
    public void takeTurnHoldingUnit() throws GameActionException {
        RobotInfo[] ri = {mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(ri);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.isCurrentlyHoldingUnit()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(2, 2));
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(4,4));
        ddTest.takeTurn();
    }

    @Test
    public void runDeliveryDroneNotReady() throws GameActionException{
        when(rc.getTeam()).thenReturn(Team.A);
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(false);
        ddTest.runDeliveryDrone();
    }

    @Test
    public void runDeliveryDroneReady1() throws GameActionException{
        when(rc.getTeam()).thenReturn(Team.A);
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(2,2));
        ddTest.runDeliveryDrone();
    }

    @Test
    public void runDeliveryDroneReady2() throws GameActionException{
        when(rc.getTeam()).thenReturn(Team.A);
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(2,2));
        for (Direction dir : Util.directions)
            when(rc.canMove(dir)).thenReturn(true);
        ddTest.runDeliveryDrone();
    }

    @Test
    public void runDeliveryDroneReady3() throws GameActionException{
        when(rc.getTeam()).thenReturn(Team.A);
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        ddTest.runDeliveryDrone();
    }

    //if(rc.getLocation().distanceSquaredTo(hqLocation) > 30) {  == true
    //if (rc.canMove(rc.getLocation().directionTo(hqLocation))) {  == true
    //rc.move(rc.getLocation().directionTo(hqLocation));

    @Test
    public void runDeliveryDroneReady4() throws GameActionException{
        when(rc.getTeam()).thenReturn(Team.A);
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(1,1));
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.getLocation()).thenReturn(new MapLocation(40,40));
        when(rc.canMove(any())).thenReturn(true);
        doNothing().when(rc).move(any());
        ddTest.runDeliveryDrone();
    }

    @Test
    public void lookForWater() throws GameActionException {
        when(rc.senseFlooding(any())).thenReturn(true);
        when(rc.getLocation()).thenReturn(new MapLocation(2,2));
        when(ddTest.comms.getHqLocFromBlockchain()).thenReturn(new MapLocation(3,3));
        ddTest.lookForWater(new MapLocation(1,1));
    }
}