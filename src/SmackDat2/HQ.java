package SmackDat2;

import battlecode.common.*;

public class HQ extends Shooter {
    static int numMiners = 0;

    public HQ(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        if(rc.getRoundNum() < 5 && rc.getRoundNum() > 1) {
            comms.spamBlockChain();
            for (int i = 0; i < 7; i++) {
                System.out.println(comms.guessBlockchainArray[i]);
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

//        // will only actually happen if we haven't already broadcast the creation
//        if(rc.getRoundNum() % 50 == 16 && rc.getRoundNum()>24){
////            comms.broadcastStats(comms.secretTeamKey,7,numMiners,rc.getLocation().x,rc.getLocation().y,comms.soupAmount);
//
//            int [] commsArray;
//            commsArray = comms.receiveCount(comms.secretTeamKey);
//
//            System.out.println("This is round " + rc.getRoundNum());
//            for (int i = 0; i < 10; i++) {
//                System.out.println(commsArray[i]);
//            }
//        }

//        if(numMiners < 5) {
//            for (Direction dir : Util.directions)
//                if(tryBuild(RobotType.MINER, dir)){
//                    numMiners++;
//                }
//        }
    }
}