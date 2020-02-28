package SmackDat2;

import battlecode.common.*;


public class Refinery extends Building {

    public Refinery(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // will only actually happen if we haven't already broadcasted the creation
        //comms.broadcastFulfillmentCenterCreation(rc.getLocation());

        runRefinery();
    }

    public void runRefinery() throws GameActionException {
        //System.out.println("Pollution: " + rc.sensePollution(rc.getLocation()));
        if(!comms.refineryExists())
            comms.broadcastRefineryExists();

        // Every 25 rounds broadcast location
        else if (rc.getRoundNum() % 25 == 0) {
            comms.sendRefineryLocation(rc.getLocation());
        }
    }
}
