package SmackDat2;

import battlecode.common.*;

import static SmackDat.RobotPlayer.getHQLocation;

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
        {
            Team enemy = rc.getTeam().opponent();
            MapLocation loc = rc.getLocation();

            if (rc.isCurrentlyHoldingUnit()) {
                for (Direction dir : Util.directions) {
                    if (rc.senseFlooding(rc.getLocation().add(dir)))
                        rc.dropUnit(dir);
                    break;
                }
                lookForWater(loc);

            } else if (rc.isReady()) {
                RobotInfo[] robots = rc.senseNearbyRobots(30, enemy);

                if (robots.length == 0) {
                    returnHome();
                } else {
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
        public void lookForWater (MapLocation loc) throws GameActionException {
            MapLocation start = loc.add(Direction.WEST).add(Direction.WEST).add(Direction.WEST)
                    .add(Direction.SOUTH).add(Direction.SOUTH).add(Direction.SOUTH);

            for (int i = 0; i < 6; i++) {
                start.add(Direction.NORTH);
                if (rc.senseFlooding(start)) {
                    rc.move(rc.getLocation().directionTo(start));
                    break;
                }
            }

            for (int i = 0; i < 6; i++) {
                start.add(Direction.EAST);
                if (rc.senseFlooding(start)) {
                    rc.move(rc.getLocation().directionTo(start));
                    break;
                }
            }

            for (int i = 0; i < 6; i++) {
                start.add(Direction.SOUTH);
                if (rc.senseFlooding(start)) {
                    rc.move(rc.getLocation().directionTo(start));
                    break;
                }
            }

            for (int i = 0; i < 6; i++) {
                start.add(Direction.WEST);
                if (rc.senseFlooding(start)) {
                    rc.move(rc.getLocation().directionTo(start));
                    break;
                }
            }

        }

        public void runAroundMap (MapLocation loc) throws GameActionException {
            if (loc.x < 6) {
                if (loc.y > 35) {
                    rc.move(Direction.EAST);
                    rc.move(Direction.SOUTHEAST);
                } else if (loc.y < 6) {
                    rc.move(Direction.NORTHEAST);
                    rc.move(Direction.EAST);
                } else {
                    rc.move(Direction.EAST);
                }
            } else if (loc.x > 35) {
                if (loc.y > 35) {
                    rc.move(Direction.WEST);
                    rc.move(Direction.SOUTHWEST);
                } else if (loc.y < 6) {
                    rc.move(Direction.NORTHWEST);
                    rc.move(Direction.WEST);
                } else {
                    rc.move(Direction.WEST);
                }
            } else {
                rc.move(Util.randomDirection());
            }
        }

        public void returnHome () throws GameActionException {

            if (rc.canMove(rc.getLocation().directionTo(getHQLocation()))) {
                rc.move(rc.getLocation().directionTo(getHQLocation()));
            }

        }

}