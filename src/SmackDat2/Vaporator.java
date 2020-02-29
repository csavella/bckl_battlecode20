package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Vaporator extends Building {
    public Vaporator(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        runVaporator();
    }

    public void runVaporator() throws GameActionException{
        // will only actually happen if we haven't already broadcast the creation
        if(rc.getRoundNum() % 50 == 27){
            comms.broadcastStats(comms.secretTeamKey,1,0,rc.getLocation().x,rc.getLocation().y,comms.soupAmount);
        }

    }
}
