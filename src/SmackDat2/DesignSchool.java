package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class DesignSchool extends Building {
    public DesignSchool(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        
        // will only actually happen if we haven't already broadcasted the creation
        comms.broadcastDesignSchoolCreation(rc.getLocation());

        if(!comms.designSchoolExists())
            comms.broadcastDesignSchoolExists();

        for (Direction dir : Util.directions) {
            if(!comms.landscaperExists() && tryBuild(RobotType.LANDSCAPER, dir)) {
                comms.broadcastLandscaperExists();
                System.out.println("made a landscaper");
            }
        }
    }
}
