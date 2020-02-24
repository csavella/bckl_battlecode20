package SmackDat2;

import battlecode.common.*;
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
        when(rc.getLocation()).thenReturn(new MapLocation(2, 2));
        ddTest.takeTurn();
    }
    /*public void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();

        if (rc.isCurrentlyHoldingUnit()) {
            nav.tryMove(Util.randomDirection());
        } else if (rc.isReady()) {
            RobotInfo[] robots = rc.senseNearbyRobots();
            if (robots.length == 0) {
                MapLocation m = getHQLocation();
                rc.move(rc.getLocation().directionTo(m).opposite());
            }
            for (RobotInfo r : robots) {
                if (r.team == enemy) {
                    if (rc.getLocation().isAdjacentTo(r.getLocation())) {
                        if (rc.canPickUpUnit(r.getID())) {
                            rc.pickUpUnit(r.getID());
                            break;
                        }
                    }
                }
            }
            //move to the enemy direction
            for (RobotInfo r : robots) {
                if (r.team == enemy) {
                    rc.move(rc.getLocation().directionTo(r.getLocation()));
                }
            }
        }
    }*/
    @Test
    public void takeTurnDoNothing() throws GameActionException {
        RobotInfo[] ri = new RobotInfo[]{new RobotInfo(12, Team.B, RobotType.HQ, 0, false, 0, 0, 0, new MapLocation(5, 5))};
        when(rc.senseNearbyRobots()).thenReturn(ri);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.isCurrentlyHoldingUnit()).thenReturn(false);
        when(rc.isReady()).thenReturn(true);
        when(rc.senseNearbyRobots()).thenReturn(ri);
        //when(rc.getLocation().isAdjacentTo(any())).thenReturn(true);
        //assertEquals(ri.length,0);

        ddTest.takeTurn();
    }

    @Test
    public void runDeliveryDrone() throws GameActionException{
    }
}