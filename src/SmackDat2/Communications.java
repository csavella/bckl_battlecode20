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
    static final int secretTeamKey = 724;
    // the second entry in every message tells us what kind of message it is. e.g. 0 means it contains the HQ location
    static final String[] messageType = {
        "HQ loc",
        "design school created",
        "soup location",
    };

    public int [] guessBlockchainArray = {-1,-1,-1,-1,-1,-1,-1};
    public int soupAmount = 2;

    public int [] buildOrder = new int[] {1,1,1,1,4,4,4,1,0,0};

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

    public void guessBlockchain() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum()-1; i++){
            for(Transaction tx : rc.getBlock(i)) {
                int[] mess = tx.getMessage();
                    if(mess[0] != secretTeamKey){
                        for (int j = 0; j < 7; j++) {
                            guessBlockchainArray[j] = mess[j];
                        }
                        return;
                    }
            }
        }
    }

    public void spamBlockChain() throws GameActionException {
        boolean previousRound = false;

        if (guessBlockchainArray[0] == -1) {
            guessBlockchain();
        }

        for(Transaction tx : rc.getBlock(rc.getRoundNum()-1)) {
            int[] mess = tx.getMessage();
            if(mess[0] != secretTeamKey){
                previousRound = true;
                break;
            }
        }

        if (guessBlockchainArray[0] != -1 && rc.getTeamSoup() > 50 && previousRound) {
            for (int i = 0; i < 7; i++) {
                int[] mess = new int[7];
                for (int j = 0; j < 7; j++) {
                    if (i == j) {
                        mess[j] = 64;
                    } else {
                        mess[j] = guessBlockchainArray[j];
                    }
                }
                rc.submitTransaction(mess, soupAmount - 1);
            }
        }
    }


    //code for vaporator is 9645
    public boolean vaporatorExists() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction tx : rc.getBlock(i)) {
                int[] mess = tx.getMessage();
                if(mess[0] == secretTeamKey && mess[1] == 9645)
                    return true;
            }
        }

        return false;
    }

    //code for vaporator is 9645
    public void broadcastVaporatorExists() throws GameActionException{
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 9645;

        if (rc.canSubmitTransaction(message, 2)) {
            rc.submitTransaction(message, 2);
        }
    }

    public boolean broadcastedCreation = false;
    public void broadcastDesignSchoolCreation(MapLocation loc) throws GameActionException {
        if(broadcastedCreation) return; // don't re-broadcast

        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 21;
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
            if(mess[0] == secretTeamKey && mess[1] == 21){
                System.out.println("heard about a cool new school");
                count += 1;
            }
        }
        return count;
    }

    //code number is 645
    boolean netGunHasBeenMade() throws GameActionException {
        for (int i = 300; i < rc.getRoundNum(); i++) {
            for (Transaction t : rc.getBlock(i)) {
                int[] message = t.getMessage();
                if (message[0] == secretTeamKey && message[1] == 645) return true;
            }
        }

        return false;
    }

    //code number is 645
    void broadcastNetgunMade() throws GameActionException{
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 645;
        if (rc.canSubmitTransaction(message, 2))
            rc.submitTransaction(message, 2);

    }

    public void broadcastSoupLocation(MapLocation loc ) throws GameActionException {

        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 20;
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
            if(mess[0] == secretTeamKey && mess[1] == 20){
                // TODO: don't add duplicate locations
                System.out.println("heard about a tasty new soup location");
                soupLocations.add(new MapLocation(mess[2], mess[3]));
            }
        }
    }


    //Checks the blockchain for messages with the fulfillment center int (77)
    public boolean fulfillmentCenterExists() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
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
        rc.submitTransaction(message, 2);
    }

    public void broadcastDeliveryDrone() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 88;
        rc.submitTransaction(message, 2);
    }

    public int deliveryDroneCount() throws GameActionException {
        int droneCount = 0;

        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction t : rc.getBlock(i)){
                int[] message = t.getMessage();
                if(message[0] == secretTeamKey && message[1] == 88) droneCount+=1;
            }
        }

        return droneCount;
    }
    public void broadcastLandscaperExists() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 199;
        rc.submitTransaction(message, 2);
    }

    public boolean landscaperExists() throws GameActionException {
        for (int i = 1; i < rc.getRoundNum(); i++){
            for(Transaction t : rc.getBlock(i)){
                int[] message = t.getMessage();
                if(message[0] == secretTeamKey && message[1] == 199) return true;
            }
        }

        return false;
    }

    public void broadcastStats(int secretTeamKey, int unitCode, int unitsProduced, int locX, int locY, int soup) throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = unitCode;
        message[2] = unitsProduced;
        message[3] = locX;
        message[4] = locY;

        if (rc.canSubmitTransaction(message, soup)) {
            rc.submitTransaction(message, soup);
        }
    }

    public int[] receiveCount(int secretTeamKey) throws GameActionException {
        int[] counts = new int[10];
        for (int i = 0; i < 10; i++) {
            counts[i] = 0;
        }
        if(rc.getRoundNum()>25) {


            for (int i = rc.getRoundNum() - 1; i > rc.getRoundNum() - 51; i--) {
                for (Transaction t : rc.getBlock(i)) {
                    int[] message = t.getMessage();

                    if (message[0] == secretTeamKey && message[1] < 10) {
                        int unitCode = message[1];
                        if (unitCode == 7) {
                            // It's an HQ making miners
                            counts[unitCode] += 1;
                            counts[4] += message[2];
                        } else if (unitCode == 2) {
                            // It's a design center making landscapers
                            counts[unitCode] += 1;
                            counts[5] += message[2];
                        } else if (unitCode == 3) {
                            // It's a fulfillment center making drones
                            counts[unitCode] += 1;
                            counts[6] += message[2];
                        } else {
                            counts[unitCode] += 1;
                        }

                    }
                }
            }
        }
        return counts;
    }


    public int[][] receiveLocation(int secretTeamKey, int unitCode) throws GameActionException {
        // Create array of positions of refineries (at most 10)
        int[][] positions = new int[10][];

        // Dynamically allocate space
        for (int i = 0; i < 10; i++) {
            positions[i] = new int[2];
            positions[i][0] = 0;
            positions[i][1] = 0;
        }

        for (int i = rc.getRoundNum()-1; i > rc.getRoundNum() - 51; i-- ) {

            int index = 0;

            for (Transaction t : rc.getBlock(i)) {
                int[] message = t.getMessage();

                if (message[0] == secretTeamKey && message[1] == unitCode) {
                    positions[index][0] = message[3];
                    positions[index][1] = message[4];
                    index++;
                }
                if (index == 10) {
                    return positions;
                }
            }
        }

        return positions;
    }


}
