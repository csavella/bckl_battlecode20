package SmackDat2;

import battlecode.common.*;

public class DeliveryDrone extends Unit {


    public DeliveryDrone(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        //MapLocation HQLocation = getHQLocation();
        runDeliveryDrone();

    }

    public void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();
        MapLocation hqLocation = comms.getHqLocFromBlockchain();

        if (rc.isCurrentlyHoldingUnit()) {
           lookForWater(rc.getLocation());
        }

        else if (rc.isReady()) {

            RobotInfo[] robots = rc.senseNearbyRobots();
            if(robots != null) {
                for (RobotInfo r : robots) {
                    if (r.team == enemy || r.type == RobotType.COW) {
                        if (rc.getLocation().isAdjacentTo(r.getLocation())) {
                            if (rc.canPickUpUnit(r.getID())) {
                                rc.pickUpUnit(r.getID());
                                break;
                            }
                        }
                    }
                }
            }

            //move to the enemy direction
            if(robots != null) {
                for (RobotInfo r : robots) {
                    if (r.team == enemy || r.type == RobotType.COW) {
                        if (rc.canMove(rc.getLocation().directionTo(r.getLocation())))
                            rc.move(rc.getLocation().directionTo(r.getLocation()));
                        break;
                    }
                }
            }

            //if too close to HQ, move away
            if (rc.getLocation().distanceSquaredTo(hqLocation) < 9) {
                if (rc.canMove(rc.getLocation().directionTo(hqLocation).opposite()))
                    rc.move(rc.getLocation().directionTo(hqLocation).opposite());
            }

            else {
                if(rc.getLocation().distanceSquaredTo(hqLocation) > 25) {
                    if (rc.canMove(rc.getLocation().directionTo(hqLocation))) {
                        rc.move(rc.getLocation().directionTo(hqLocation));
                        return;
                    }
                }

            }
        }
    }

     public void lookForWater(MapLocation loc) throws GameActionException {
        for(Direction dir : Direction.allDirections()) {
            if (rc.senseFlooding(rc.getLocation().add(dir))) {
                comms.broadcastWaterLocation(rc.getLocation().add(dir));
                rc.dropUnit(dir);
            }
        }

        if(comms.waterLocationKnown()) {
            runToWater();
            return;
        }

        MapLocation start = loc.add(Direction.WEST).add(Direction.WEST).add(Direction.WEST)
                .add(Direction.SOUTH).add(Direction.SOUTH).add(Direction.SOUTH);

        for (int i = 0; i < 6; i++) {
            start.add(Direction.NORTH);
            if (rc.senseFlooding(start)) {
                if(rc.canMove(rc.getLocation().directionTo(start)))
                    rc.move(rc.getLocation().directionTo(start));
                break;
            }
        }

        for (int i = 0; i < 6; i++) {
            start.add(Direction.EAST);
            if (rc.senseFlooding(start)) {
                if(rc.canMove(rc.getLocation().directionTo(start)))
                    rc.move(rc.getLocation().directionTo(start));
                break;
            }
        }

        for (int i = 0; i < 6; i++) {
            start.add(Direction.SOUTH);
            if (rc.senseFlooding(start)) {
                if(rc.canMove(rc.getLocation().directionTo(start)))
                    rc.move(rc.getLocation().directionTo(start));
                break;
            }
        }

        for (int i = 0; i < 6; i++) {
            start.add(Direction.WEST);
            if (rc.senseFlooding(start)) {
                if(rc.canMove(rc.getLocation().directionTo(start)))
                    rc.move(rc.getLocation().directionTo(start));
                break;
            }
        }

        MapLocation hqLocation = comms.getHqLocFromBlockchain();
         if(rc.canMove(rc.getLocation().directionTo(hqLocation).opposite()))
             rc.move(rc.getLocation().directionTo(hqLocation).opposite());
    }

    public void runToWater() throws GameActionException{
        if(rc.canMove(rc.getLocation().directionTo(comms.getWaterLocation())))
            rc.move(rc.getLocation().directionTo(comms.getWaterLocation()));

        else if(rc.canMove(Direction.NORTH))
            rc.move(Direction.NORTH);

        else if(rc.canMove(Direction.SOUTH))
            rc.move(Direction.SOUTH);
    }
}