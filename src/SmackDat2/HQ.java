package SmackDat2;

import battlecode.common.*;

public class HQ extends Shooter {
    static int numMiners = 0;

    public HQ(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        if(turnCount == 1) {
            comms.sendHqLoc(rc.getLocation());
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

        // will only actually happen if we haven't already broadcast the creation
        if(rc.getRoundNum() % 25 == 0){
            comms.broadcastStats(comms.secretTeamKey,7,numMiners,rc.getLocation().x,rc.getLocation().y,comms.soupAmount);
        }

        if(comms.receiveCount(comms.secretTeamKey)[4] < comms.buildOrder[4]) {
            for (Direction dir : Util.directions)
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                }
        }
    }
}