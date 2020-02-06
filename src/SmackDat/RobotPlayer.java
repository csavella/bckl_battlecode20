package SmackDat;
import battlecode.common.*;

import java.util.Random;

// Hello, testing if we can merge
// Test 2

public strictfp class RobotPlayer {

    static int numberOfMiners = 0;
    static int numberOfDesignSchools = 0;
    static int numberOfLandscapers = 0;
    static int numberOfFulfillmentCenters = 0;
    static int numberOfRefineries = 0;
    static MapLocation HQLocation = null;
    static Direction minerDirection = null;

    static final int secretTeamKey = 729384;


    static RobotController rc;

    static Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST
    };

    /* these directions will be used to move the miners away from the HQ
       in hopes to find soup in different areas of the map */
    static Direction[] directions1 = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST
    };
    static Direction[] directions2 = {
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH
    };
    static Direction[] directions3 = {
            Direction.WEST,
            Direction.SOUTHWEST,
            Direction.SOUTH
    };
    static Direction[] directions4 = {
            Direction.WEST,
            Direction.NORTHWEST,
            Direction.NORTH
    };

    static RobotType[] spawnedByMiner = {RobotType.REFINERY, RobotType.VAPORATOR, RobotType.DESIGN_SCHOOL,
            RobotType.FULFILLMENT_CENTER, RobotType.NET_GUN};

    static int turnCount;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        RobotPlayer.rc = rc;

        turnCount = 0;

        System.out.println("I'm a " + rc.getType() + " and I just got created!");
        while (true) {
            turnCount += 1;
            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                // Here, we've separated the controls into a different method for each RobotType.
                // You can add the missing ones or rewrite this into your own control structure.
                //System.out.println("I'm a " + rc.getType() + "! Location " + rc.getLocation());
                switch (rc.getType()) {
                    case HQ:
                        runHQ();
                        break;
                    case MINER:
                        runMiner();
                        break;
                    case REFINERY:
                        runRefinery();
                        break;
                    case VAPORATOR:
                        runVaporator();
                        break;
                    case DESIGN_SCHOOL:
                        runDesignSchool();
                        break;
                    case FULFILLMENT_CENTER:
                        runFulfillmentCenter();
                        break;
                    case LANDSCAPER:
                        runLandscaper();
                        break;
                    case DELIVERY_DRONE:
                        runDeliveryDrone();
                        break;
                    case NET_GUN:
                        runNetGun();
                        break;
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }

    static void runHQ() throws GameActionException {

        //At the start of the game send the HQ Location out onto the blockchain
        if (rc.getRoundNum() < 5)
            sendHQLocation(rc.getLocation());


        for (Direction dir : directions) {
            if (numberOfMiners > 1) {
                break;
            } else {
                if (tryBuild(RobotType.MINER, dir))
                    numberOfMiners++;
                break;
            }
        }
        //Sense all robots near the HQ, if there is a drone from the enemy team in the radius,
        //Shoot it down
        Team myTeamColor = rc.getTeam();
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for (RobotInfo r : nearbyRobots) {
            // TEST: System.out.println("Robot nearby");
            if (r.team != myTeamColor && rc.canShootUnit(r.ID))
                rc.shootUnit(r.ID);

        }

    }

    public static void sendHQLocation(MapLocation loc) throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 0; //0 Designates it is HQ Location
        message[2] = loc.x;
        message[3] = loc.y;

        if (rc.canSubmitTransaction(message, 2))
            rc.submitTransaction(message, 2);

    }

    //Scan the whole blockchain from round 1 for our HQ message announcing the HQ location
    //Return 1,1 MapLocation if for some reason we never broadcast our location
    public static MapLocation getHQLocation() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++) {
            for (Transaction t : rc.getBlock(i)) {
                int[] message = t.getMessage();

                //If the message uses our secret key and the message[1] field has 0 (HQLocation designator)
                if (message[0] == secretTeamKey && message[1] == 0) {
                    //TEST: System.out.println("I got a message");
                    return new MapLocation(message[2], message[3]);
                }
            }
        }

        return new MapLocation(1, 1);
    }

    static void runMiner() throws GameActionException {
        int switchMoveLogicTurnCount = 10;
        if (HQLocation == null) {
            RobotInfo[] robots = rc.senseNearbyRobots();
            for (RobotInfo robot : robots) {
                if(robot.type == RobotType.HQ) {
                    HQLocation = robot.location;
                }
            }
        }
        tryBlockchain();

        for (Direction dir : directions)
            if (tryRefine(dir))
                System.out.println("I refined soup! " + rc.getTeamSoup());
        for (Direction dir : directions)
            if (tryMine(dir))
                System.out.println("I mined soup! " + rc.getSoupCarrying());

        /* sense soup and move towards it. If already carrying a bunch of soup, bring it back to the HQ.
           on every 40th turn, move in a general direction. Goal is for miners to spread out to find
           soup on map
        */
        if (turnCount % switchMoveLogicTurnCount != 0) {
            if (rc.getSoupCarrying() == 100) {
                Direction backtoHQ = rc.getLocation().directionTo(HQLocation);
                if (tryMove(backtoHQ))
                    System.out.println("going back to HQ");
            } else {
                MapLocation[] soups = rc.senseNearbySoup();
                for (MapLocation soup : soups) {
                    System.out.println("moving towards soup");
                    moveTo(soup);
                }
            }
        }
        else {
            int mapX = rc.getMapWidth();
            int mapY = rc.getMapHeight();
            int hqX = getHQLocation().x;
            int hqY = getHQLocation().y;
            int mapCoordinates = 0;
            Direction[] directionList;
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
            int randomNum;
            Random random = new Random();
            if (mapCoordinates == 0) {
                randomNum = random.nextInt(8);
            } else {
                randomNum = random.nextInt(3);
            }
            minerDirection = directionList[randomNum];
            System.out.println("moving " + minerDirection);
            moveTo(minerDirection);
        }

        //Currently will only make a max of 1 Design Schools
        {
            if (numberOfDesignSchools < 1) {
                for (Direction dir : directions)
                    if (tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                        numberOfDesignSchools++;
                        break;
                    }
            }

            if (numberOfFulfillmentCenters < 1) {
                for (Direction d : directions) {
                    if (tryBuild(RobotType.FULFILLMENT_CENTER, d)) {
                        numberOfFulfillmentCenters++;
                        break;
                    }
                }
            }

            if (numberOfRefineries < 1) {
                for (Direction d : directions) {
                    if (tryBuild(RobotType.REFINERY, d)) {
                        numberOfRefineries++;
                        break;
                    }
                }
            }
        }
    }

    static boolean moveTo(Direction direction) throws GameActionException {
        Direction[] all = {direction, direction.rotateLeft(), direction.rotateRight()};
        for (Direction d : all) {
            if (tryMove(d))
                return true;
        }
        return false;
    }
    static boolean moveTo(MapLocation destination) throws GameActionException {
        return moveTo(rc.getLocation().directionTo(destination));
    }

    static void runRefinery() throws GameActionException {
        System.out.println("Pollution: " + rc.sensePollution(rc.getLocation()));
    }

    static void runVaporator() throws GameActionException {

    }

    //Currently will only make a max of 1 landscapers
    static void runDesignSchool() throws GameActionException {
        if (numberOfLandscapers > 0)
            return;

        for (Direction dir : directions) {

            if (tryBuild(RobotType.LANDSCAPER, dir)) {
                numberOfLandscapers++;
                break;

            }
        }

    }

    static void runFulfillmentCenter() throws GameActionException {
        for (Direction dir : directions)
            tryBuild(RobotType.DELIVERY_DRONE, dir);
    }

    static void runLandscaper() throws GameActionException {
        MapLocation HQLocation = getHQLocation();

        if (rc.isReady()) {
            if (!rc.getLocation().isAdjacentTo(HQLocation)) {
                //navigateTo or some version of it needs to be implemented using a pathfinding algorithm eventually,
                //probably Djikstras or A*, Im just sticking a bandaid here for now
                //navigateTo(HQLocation, rc.getLocation());
                Direction t = rc.getLocation().directionTo(HQLocation);
                System.out.println("hi");
                rc.move(rc.getLocation().directionTo(HQLocation));
            } else {
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

                switch (directionFromHQ) {
                    case NORTH:
                        nextDirection = Direction.WEST;
                        break;
                    case NORTHWEST:
                        nextDirection = Direction.SOUTH;
                        break;
                    case WEST:
                        nextDirection = Direction.SOUTH;
                        break;
                    case SOUTHWEST:
                        nextDirection = Direction.EAST;
                        break;
                    case SOUTH:
                        nextDirection = Direction.EAST;
                        break;
                    case SOUTHEAST:
                        nextDirection = Direction.NORTH;
                        break;
                    case EAST:
                        nextDirection = Direction.NORTH;
                        break;
                    case NORTHEAST:
                        nextDirection = Direction.WEST;
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
                else if (rc.senseElevation(rc.getLocation()) > rc.senseElevation(rc.getLocation().add(nextDirection))) {
                    rc.move(nextDirection);
                }

                //Only thing left is to dump dirt beneath my feet
                else {
                    rc.depositDirt(Direction.CENTER);
                }
            }
        }
    }


    //Tries to move closer to the HQLocation, throws GameActionException
    //This should never throw an error
    static void navigateTo(MapLocation HQLocation, MapLocation robotLocation) {
        try {
            rc.move(robotLocation.directionTo(HQLocation));
        } catch (GameActionException e) {
            System.out.println("Landscaper tried to move using navigateTo and failed.\n" + e.getMessage());
        }

    }

    static void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();

        if (rc.isCurrentlyHoldingUnit()) {
            tryMove(randomDirection());
        }
        else if (rc.isReady()) {
            RobotInfo[] robots = rc.senseNearbyRobots();
            if(robots.length==0){
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

        //For now I just want them getting away from the Fulfillment center
       /* tryMove(randomDirection());
        if (!rc.isCurrentlyHoldingUnit()) {
            // See if there are any enemy robots within capturing range
            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, enemy);
            if (robots.length > 0) {
                // Pick up a first robot within range
                rc.pickUpUnit(robots[0].getID());
                System.out.println("I picked up " + robots[0].getID() + "!");
            }
        } else {
            // No close robots, so search for robots within sight radius
            tryMove(randomDirection());
        } */
    }

    static void runNetGun() throws GameActionException {

    }

    /**
     * Returns a random Direction.
     *
     * @return a random Direction
     */
    static Direction randomDirection() {
        return directions[(int) (Math.random() * directions.length)];
    }

    /**
     * Returns a random RobotType spawned by miners.
     *
     * @return a random RobotType
     */
    static RobotType randomSpawnedByMiner() {
        return spawnedByMiner[(int) (Math.random() * spawnedByMiner.length)];
    }

    static boolean tryMove() throws GameActionException {
        for (Direction dir : directions)
            if (tryMove(dir))
                return true;
        return false;
        // MapLocation loc = rc.getLocation();
        // if (loc.x < 10 && loc.x < loc.y)
        //     return tryMove(Direction.EAST);
        // else if (loc.x < 10)
        //     return tryMove(Direction.SOUTH);
        // else if (loc.x > loc.y)
        //     return tryMove(Direction.WEST);
        // else
        //     return tryMove(Direction.NORTH);
    }

    /**
     * Attempts to move in a given direction.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        // System.out.println("I am trying to move " + dir + "; " + rc.isReady() + " " + rc.getCooldownTurns() + " " + rc.canMove(dir));
        if (rc.isReady() && rc.canMove(dir)) {
            rc.move(dir);
            return true;
        } else return false;
    }

    /**
     * Attempts to build a given robot in a given direction.
     *
     * @param type The type of the robot to build
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryBuild(RobotType type, Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canBuildRobot(type, dir)) {
            rc.buildRobot(type, dir);
            return true;
        } else return false;
    }

    /**
     * Attempts to mine soup in a given direction.
     *
     * @param dir The intended direction of mining
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMine(Direction dir) throws GameActionException {
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
    static boolean tryRefine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canDepositSoup(dir)) {
            rc.depositSoup(dir, rc.getSoupCarrying());
            return true;
        } else return false;
    }


    static void tryBlockchain() throws GameActionException {
        if (turnCount < 3) {
            int[] message = new int[7];
            for (int i = 0; i < 7; i++) {
                message[i] = 123;
            }
            if (rc.canSubmitTransaction(message, 10))
                rc.submitTransaction(message, 10);
        }
        // System.out.println(rc.getRoundMessages(turnCount-1));
    }
}
