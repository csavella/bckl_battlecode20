package SmackDat2;

import battlecode.common.*;
import static SmackDat2.Util.*;

public class Landscaper extends Unit {


    public Landscaper(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        //MapLocation HQLocation = getHQLocation();
        runLandscaper();
    }

    void runLandscaper() throws GameActionException {
        MapLocation HQLocation = getHQLocation();

        if(rc.isReady()) {
            if (!rc.getLocation().isAdjacentTo(HQLocation)) {
                //navigateTo or some version of it needs to be implemented using a pathfinding algorithm eventually,
                //probably Djikstras or A*, Im just sticking a bandaid here for now
                //navigateTo(HQLocation, rc.getLocation());
                Direction t = rc.getLocation().directionTo(HQLocation);
                System.out.println("hi");
                rc.move(rc.getLocation().directionTo(HQLocation));
            }
            else {
                //Figure out which of the 8 squares around the HQ it's on, so it knows where to take dirt from
                //and where to place dirt at and then move to
                MapLocation currentLocation = rc.getLocation();
                Direction directionFromHQ = null;

                for (Direction d : directions) {
                    if (currentLocation.equals(HQLocation.add(d))) {
                        //TEST
                        System.out.println("Landscaper is currently " + d + " of HQ.");
                        directionFromHQ = d;
                        break;
                    }
                }

                Direction nextDirection;

                switch(directionFromHQ){
                    case NORTH:
                    case NORTHEAST:
                        nextDirection = Direction.WEST;
                        break;
                    case NORTHWEST:
                    case WEST:
                        nextDirection = Direction.SOUTH;
                        break;
                    case SOUTHWEST:
                    case SOUTH:
                        nextDirection = Direction.EAST;
                        break;
                    case SOUTHEAST:
                    case EAST:
                        nextDirection = Direction.NORTH;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + directionFromHQ);
                }

                System.out.println(directionFromHQ.toString());
                System.out.println(nextDirection.toString());

                //If it doesn't have any dirt, pick up the dirt from a non-wall area
                if (rc.getDirtCarrying() == 0) {
                    switch (directionFromHQ) {
                        case NORTH:
                            rc.digDirt(Direction.NORTH);
                        case NORTHWEST:
                            rc.digDirt(Direction.NORTH);
                        case WEST:
                            rc.digDirt(Direction.WEST);
                        case SOUTHWEST:
                            rc.digDirt(Direction.WEST);
                        case SOUTH:
                            rc.digDirt(Direction.SOUTH);
                        case SOUTHEAST:
                            rc.digDirt(Direction.SOUTH);
                        case EAST:
                            rc.digDirt(Direction.EAST);
                        case NORTHEAST:
                            rc.digDirt(Direction.EAST);
                    }
                }

                //If the elevation below me is 1 higher than the next, move
                else if(rc.senseElevation(rc.getLocation()) > rc.senseElevation(rc.getLocation().add(nextDirection))){
                    rc.move(nextDirection);
                }

                //Only thing left is to dump dirt beneath my feet
                else{
                    rc.depositDirt(Direction.CENTER);
                }
            }
        }
    }

    MapLocation getHQLocation() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction t : rc.getBlock(i)){
                int[] message = t.getMessage();

                //If the message uses our secret key and the message[1] field has 0 (HQLocation designator)
                if(message[0] == secretTeamKey && message[1] == 0){
                    //TEST: System.out.println("I got a message");
                    return new MapLocation(message[2], message[3]);
                }
            }
        }

        return new MapLocation(1, 1);
    }

    boolean tryDig() throws GameActionException {
        Direction dir;
        if (hqLoc == null) {
            dir = Util.randomDirection();
        } else {
            dir = hqLoc.directionTo(rc.getLocation());
        }
        if (rc.canDigDirt(dir)) {
            rc.digDirt(dir);
            //rc.setIndicatorDot(rc.getLocation().add(dir), 255, 0, 0);
            return true;
        }
        return false;
    }


}