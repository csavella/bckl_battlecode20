package SmackDat2;

import battlecode.common.*;

import static SmackDat2.Communications.secretTeamKey;
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
        MapLocation HQLocation = comms.getHqLocFromBlockchain();

        if(rc.isReady()) {
            if (!rc.getLocation().isAdjacentTo(HQLocation)) {

                Direction t = rc.getLocation().directionTo(HQLocation);
                System.out.println("hi");
                if (rc.canMove(rc.getLocation().directionTo(HQLocation)))
                    rc.move(rc.getLocation().directionTo(HQLocation));

                else if (rc.canMove(Direction.NORTH))
                    rc.move(Direction.NORTH);

                else if (rc.canMove(Direction.SOUTH))
                    rc.move(Direction.SOUTH);

                else if (rc.canMove(Direction.SOUTHEAST))
                    rc.move(Direction.SOUTHEAST);

                else if (rc.canMove(Direction.NORTHEAST))
                    rc.move(Direction.NORTHEAST);

                else if (rc.canMove(Direction.NORTHWEST))
                    rc.move(Direction.NORTHWEST);

                else if (rc.canMove(Direction.SOUTHWEST))
                    rc.move(Direction.SOUTHWEST);
            }

            else if(rc.getRoundNum() > 150) {
                //Figure out which of the 8 squares around the HQ it's on, so it knows where to take dirt from
                //and where to place dirt at and then move to
                MapLocation currentLocation = rc.getLocation();
                Direction directionFromHQ = null;

                for (Direction d : directions) {
                    if (currentLocation.equals(HQLocation.add(d))) {
                        directionFromHQ = d;
                        break;
                    }
                }

                Direction nextDirection = getNextDirection(directionFromHQ);

                System.out.println(directionFromHQ.toString());
                System.out.println(nextDirection.toString());

                //If it doesn't have any dirt, pick up the dirt from a non-wall area
                if (rc.getDirtCarrying() == 0) {
                    switch (directionFromHQ) {
                        case NORTH:
                            if(rc.canDigDirt(Direction.NORTH))
                                rc.digDirt(Direction.NORTH);
                        case NORTHWEST:
                            if(rc.canDigDirt(Direction.NORTH))
                                 rc.digDirt(Direction.NORTH);
                        case WEST:
                            if(rc.canDigDirt(Direction.WEST))
                                 rc.digDirt(Direction.WEST);
                        case SOUTHWEST:
                            if(rc.canDigDirt(Direction.WEST))
                                 rc.digDirt(Direction.WEST);
                        case SOUTH:
                            if(rc.canDigDirt(Direction.SOUTH))
                                rc.digDirt(Direction.SOUTH);
                        case SOUTHEAST:
                            if(rc.canDigDirt(Direction.SOUTH))
                                rc.digDirt(Direction.SOUTH);
                        case EAST:
                            if(rc.canDigDirt(Direction.EAST))
                                rc.digDirt(Direction.EAST);
                        case NORTHEAST:
                            if(rc.canDigDirt(Direction.EAST))
                                rc.digDirt(Direction.EAST);
                    }
                }

                //If the elevation below me is 1 higher than the next, move
                else if(rc.senseElevation(rc.getLocation()) > rc.senseElevation(rc.getLocation().add(nextDirection))){
                    if(rc.canMove(nextDirection))
                        rc.move(nextDirection);
                }

                //Only thing left is to dump dirt beneath my feet
                else{
                    if(rc.canDepositDirt(Direction.CENTER))
                        rc.depositDirt(Direction.CENTER);
                }
            }
        }
    }

    protected Direction getNextDirection(Direction directionFromHQ) {
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
        return nextDirection;
    }

    /*MapLocation getHQLocation() throws GameActionException {
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
    } */

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