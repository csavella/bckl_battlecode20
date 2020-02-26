package SmackDat2;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.Random;

import static SmackDat2.Util.*;

public class Miner extends Unit {

    int numDesignSchools = 0;
    int numberOfFulfillmentCenters=0;
    int numberOfRefineries=0;

    ArrayList<MapLocation> soupLocations = new ArrayList<MapLocation>();

    public Miner(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        int switchMoveLogicTurnCount = 10;
        super.takeTurn();

        numDesignSchools += comms.getNewDesignSchoolCount();
        //numberOfFulfillmentCenters += comms.getNewBuildingCount();

        comms.updateSoupLocations(soupLocations);
        checkIfSoupGone();

        //Code for miners to shoot enemy drones
        Team myTeamColor = rc.getTeam();

        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for (RobotInfo r : nearbyRobots) {
            // TEST: System.out.println("Robot nearby");
            if (r.team != myTeamColor && rc.canShootUnit(r.ID))
                rc.shootUnit(r.ID);

        }

        for (Direction dir : Util.directions)
            if (tryMine(dir)) {
                System.out.println("I mined soup! " + rc.getSoupCarrying());
                MapLocation soupLoc = rc.getLocation().add(dir);
                if (!soupLocations.contains(soupLoc)) {
                    comms.broadcastSoupLocation(soupLoc);
                }
            }
        // mine first, then when full, deposit
        for (Direction dir : Util.directions)
            if (tryRefine(dir))
                System.out.println("I refined soup! " + rc.getTeamSoup());

        findHQ();
        if (!rc.getLocation().isWithinDistanceSquared(hqLoc,9)) {
            /*
            if (numDesignSchools < 1) {
                if (tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                    System.out.println("created a design school");
            }
            */

            if(!comms.designSchoolExists()){
                for (Direction dir : directions) {
                    if (tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                        break;
                    }
                }

            }

            if (!comms.fulfillmentCenterExists()) {
                for (Direction dir : directions) {
                    if (tryBuild(RobotType.FULFILLMENT_CENTER, dir)) {
                        System.out.println("Fulfillment center created!");
                    }
                }
            }

            if (numberOfRefineries < 1) {
                if (tryBuild(RobotType.REFINERY, Util.randomDirection())) {
                    System.out.println("created a refinery");
                    numberOfRefineries++;
                }
            }

            if(!comms.vaporatorExists())
                tryBuild(RobotType.VAPORATOR, Util.randomDirection());
        }

        if (rc.getRoundNum() > 300 && rc.getTeamSoup() > 255 && !comms.netGunHasBeenMade()) {
            comms.broadcastNetgunMade();
            rc.buildRobot(RobotType.NET_GUN, Direction.CENTER);
        }

        findSoup(switchMoveLogicTurnCount);
    }



    /* sense soup and move towards it. If already carrying a bunch of soup, bring it back to the HQ.
           on every 40th turn, move in a general direction. Goal is for miners to spread out to find
           soup on map
    */
    void findSoup(int switchMoveLogicTurnCount) throws GameActionException {
        int mapX = rc.getMapWidth();
        int mapY = rc.getMapHeight();
        int hqX = hqLoc.x;
        int hqY = hqLoc.y;
        int mapCoordinates = 0;
        int randomNum;
        Direction[] directionList;
        Direction minerDirection;
        Direction backtoHQ;
        Random random;

        if (turnCount % switchMoveLogicTurnCount != 0) {
            if (rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
                backtoHQ = rc.getLocation().directionTo(hqLoc);
                if (nav.goTo(backtoHQ))
                    System.out.println("going back to HQ");
            } else if (soupLocations.size() > 0) {
                nav.goTo(soupLocations.get(0));
            } else if (nav.goTo(Util.randomDirection())) {
                System.out.println("I moved randomly!");
            }
        }
        else {
            if (hqX < mapX / 3 && hqY < mapY / 3 ) {
                mapCoordinates = 1;
                directionList = directions1;
            } else if (hqX < mapX / 3 && hqY > mapY * 2 / 3 ) {
                mapCoordinates = 2;
                directionList = directions2;
            } else if (hqX > mapX * 2 / 3 && hqY > mapY * 2 / 3 ) {
                mapCoordinates = 3;
                directionList = directions3;
            } else if (hqX > mapX * 2 / 3 && hqY < mapY / 3 ) {
                mapCoordinates = 4;
                directionList = directions4;
            } else {
                directionList = directions;
            }

            random = new Random();
            if (mapCoordinates == 0) {
                randomNum = random.nextInt(8);
            } else {
                randomNum = random.nextInt(3);
            }

            minerDirection = directionList[randomNum];
            System.out.println("moving " + minerDirection);
            nav.goTo(minerDirection);
        }
    }

    /**
     * Attempts to mine soup in a given direction.
     *
     * @param dir The intended direction of mining
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryMine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canMineSoup(dir)) {
            rc.mineSoup(dir);
            return true;
        } else return false;
    }

    /**
     * Attempts to refine soup in a given direction.
     *
     * @param dir The intended direction of refining
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryRefine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canDepositSoup(dir)) {
            rc.depositSoup(dir, rc.getSoupCarrying());
            return true;
        } else return false;
    }

    void checkIfSoupGone() throws GameActionException {
        if (soupLocations.size() > 0) {
            MapLocation targetSoupLoc = soupLocations.get(0);
            if (rc.canSenseLocation(targetSoupLoc)
                    && rc.senseSoup(targetSoupLoc) == 0) {
                soupLocations.remove(0);
            }
        }
    }
}