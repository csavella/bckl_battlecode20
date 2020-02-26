package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class FulfillmentCenter extends Building {
    public FulfillmentCenter(RobotController r) {
        super(r);
    }

    public int dronesProduced = 0;

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // will only actually happen if we haven't already broadcast the creation
        if(rc.getRoundNum() % 25 == 2){
            comms.broadcastStats(comms.secretTeamKey,3,dronesProduced,rc.getLocation().x,rc.getLocation().y,comms.soupAmount);
        }

        for (Direction dir : Util.directions) {
            if(comms.receiveCount(comms.secretTeamKey)[6] < comms.buildOrder[6] && tryBuild(RobotType.DELIVERY_DRONE, dir)) {
                comms.broadcastDeliveryDrone();
                dronesProduced++;
                System.out.println("made a delivery drone");
            }
        }
    }
}
