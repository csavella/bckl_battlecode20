package SmackDat2;

import battlecode.common.*;

import static SmackDat.RobotPlayer.getHQLocation;

public class DeliveryDroneDef extends Unit {


    public DeliveryDroneDef(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        //MapLocation HQLocation = getHQLocation();
        runDeliveryDrone();

    }

    public void runDeliveryDrone() throws GameActionException {
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
    }
}