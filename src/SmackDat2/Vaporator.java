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
        if(!comms.vaporatorExists())
            comms.broadcastVaporatorExists();

    }
}
