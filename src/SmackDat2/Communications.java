package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Transaction;

import java.util.ArrayList;

public class Communications {
    RobotController rc;

    // state related only to communications should go here

    // all messages from our team should start with this so we can tell them apart
    static final int secretTeamKey = 729384;
    // the second entry in every message tells us what kind of message it is. e.g. 0 means it contains the HQ location
    static final String[] messageType = {
        "HQ loc",
        "design school created",
        "soup location",
    };

    public Communications(RobotController r) {
        rc = r;
    }

    public void sendHqLoc(MapLocation loc) throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 0; // 0 Designates to HQ Location
        message[2] = loc.x; // x coord of HQ
        message[3] = loc.y; // y coord of HQ
        if (rc.canSubmitTransaction(message, 3))
            rc.submitTransaction(message, 3);
    }
    //Scan the whole blockchain from round 1 for our HQ message announcing the HQ location
    //Return 1,1 MapLocation if for some reason we never broadcast our location
    public MapLocation getHqLocFromBlockchain() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction tx : rc.getBlock(i)) {
                int[] mess = tx.getMessage();
                if(mess[0] == secretTeamKey && mess[1] == 0){
                    System.out.println("found the HQ!");
                    return new MapLocation(mess[2], mess[3]);
                }
            }
        }
        return new MapLocation(1,1);
    }

    public boolean broadcastedCreation = false;
    public void broadcastDesignSchoolCreation(MapLocation loc) throws GameActionException {
        if(broadcastedCreation) return; // don't re-broadcast

        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1;
        message[2] = loc.x; // x coord of HQ
        message[3] = loc.y; // y coord of HQ
        if (rc.canSubmitTransaction(message, 3)) {
            rc.submitTransaction(message, 3);
            broadcastedCreation = true;
        }
    }

    // check the latest block for unit creation messages
    public int getNewDesignSchoolCount() throws GameActionException {
        int count = 0;
        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
            int[] mess = tx.getMessage();
            if(mess[0] == secretTeamKey && mess[1] == 1){
                System.out.println("heard about a cool new school");
                count += 1;
            }
        }
        return count;
    }

    public void broadcastSoupLocation(MapLocation loc ) throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 2;
        message[2] = loc.x; // x coord of HQ
        message[3] = loc.y; // y coord of HQ
        if (rc.canSubmitTransaction(message, 3)) {
            rc.submitTransaction(message, 3);
            System.out.println("new soup!" + loc);
        }
    }

    public void updateSoupLocations(ArrayList<MapLocation> soupLocations) throws GameActionException {
        for(Transaction tx : rc.getBlock(rc.getRoundNum() - 1)) {
            int[] mess = tx.getMessage();
            if(mess[0] == secretTeamKey && mess[1] == 2){
                // TODO: don't add duplicate locations
                System.out.println("heard about a tasty new soup location");
                soupLocations.add(new MapLocation(mess[2], mess[3]));
            }
        }
    }

    public void sendRefineryLocation(MapLocation loc) throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 3; //0 Designates it is Refinery Location
        message[2] = loc.x;
        message[3] = loc.y;

        if (rc.canSubmitTransaction(message, 2)) {
            rc.submitTransaction(message, 2);

            System.out.println("I'm transmitting!");
        }
    }

    // Receives locations
    public int [][] getRefineryLocations() throws GameActionException {
        // Create array of positions of refineries (at most 10)
        int[][] positions = new int[10][];

        // Dynamically allocate space
        for (int i = 0; i < 10; i++) {
            positions[i] = new int[2];
            positions[i][0] = 0;
            positions[i][1] = 0;
        }

        for (int i = rc.getRoundNum(); i > rc.getRoundNum() - 26; i-- ) {

            int index = 0;

            for (Transaction t : rc.getBlock(i)) {
                int[] message = t.getMessage();

                //If the message uses our secret key and the message[1] field has 0 (HQLocation designator)
                if (message[0] == secretTeamKey && message[1] == 2) {
                    //TEST: System.out.println("I got a message");
                    positions[index][0] = message[2];
                    positions[index][1] = message[3];
                    index += 1;
                }
            }
        }

        return positions;
    }


    //Checks the blockchain for messages with the fulfillment center int (77)
    public boolean fulfillmentCenterExists() throws GameActionException {
        for (int i = 0; i < rc.getRoundNum(); i++){
            for(Transaction t : rc.getBlock(i)){
                int[] message = t.getMessage();
                if(message[0] == secretTeamKey && message[1] == 77) return true;
            }
        }
        return false;
    }

    //Fulfillment center creation ID is 77 on the blockchain
    //index 2, 3 is the x,y of the fulfillment center
    public void broadcastFulfillmentCenterExists() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 77;
        message[2] = rc.getLocation().x;
        message[3] = rc.getLocation().y;
    }
}
