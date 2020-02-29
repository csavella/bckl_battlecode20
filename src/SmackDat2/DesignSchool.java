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

        int landscapers = 0;

        // will only actually happen if we haven't already broadcast the creation
        if(rc.getRoundNum() % 50 == 1){
            System.out.println("Broadcasting" + landscapers);
            comms.broadcastStats(comms.secretTeamKey,2,landscapers,rc.getLocation().x,rc.getLocation().y,comms.soupAmount);
        }

        for (Direction dir : Util.directions) {
            if(tryBuild(RobotType.LANDSCAPER, dir)) {
                comms.broadcastLandscaperExists();
                System.out.println("made a landscaper" + landscapers);
                landscapers++;
            }
        }
    }
}
