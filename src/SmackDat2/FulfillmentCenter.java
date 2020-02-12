package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class FulfillmentCenter extends Building {
    public FulfillmentCenter(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // will only actually happen if we haven't already broadcast the creation
        if(!comms.fulfillmentCenterExists()){
            comms.broadcastFulfillmentCenterExists();
        }

        for (Direction dir : Util.directions) {
            if(comms.deliveryDroneCount() < 3 && tryBuild(RobotType.DELIVERY_DRONE, dir)) {
                comms.broadcastDeliveryDrone();
                System.out.println("made a delivery drone");
            }
        }
    }}
