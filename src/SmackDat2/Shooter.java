package SmackDat2;
import battlecode.common.*;

public class Shooter extends Building {

    public Shooter(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // shoot nearby enemies
        Team enemy = rc.getTeam().opponent();
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        if(nearbyRobots == null)
            return;
        for (RobotInfo r : nearbyRobots) {
            if (r.type == RobotType.DELIVERY_DRONE && rc.canShootUnit(r.ID)){
                    rc.shootUnit(r.ID);
                    break;
            }
        }
    }
}