package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static SmackDat2.Communications.secretTeamKey;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommunicationsTest {

    private Communications comms;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        comms = new Communications(rc);
    }

    //can send
    @Test
    public void sendHqLoc1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 0; // 0 Designates to HQ Location
        message[2] = 1; // x coord of HQ
        message[3] = 1; // y coord of HQ
        when(rc.canSubmitTransaction(message,3)).thenReturn(true);
        comms.sendHqLoc(new MapLocation(1,1));
    }

    //can't send
    @Test
    public void sendHqLoc2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 0; // 0 Designates to HQ Location
        message[2] = 1; // x coord of HQ
        message[3] = 1; // y coord of HQ
        when(rc.canSubmitTransaction(message,3)).thenReturn(false);
        comms.sendHqLoc(new MapLocation(1,1));
    }

    @Test
    public void getHqLocFromBlockchain() throws GameActionException {
        when(rc.getRoundNum()).thenReturn(1);
        assertEquals(comms.getHqLocFromBlockchain(), new MapLocation(1,1));
    }

    //can broadcast
    @Test
    public void broadcastDesignSchoolCreation1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1;
        message[2] = 0; // x coord of HQ
        message[3] = 1; // y coord of HQ
        when(rc.canSubmitTransaction(message, 3)).thenReturn(true);
        comms.broadcastDesignSchoolCreation(new MapLocation(0,1));
        assertEquals(true, comms.broadcastedCreation);
    }

    //can't broadcast
    @Test
    public void broadcastDesignSchoolCreation2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1;
        message[2] = 0; // x coord of HQ
        message[3] = 1; // y coord of HQ
        when(rc.canSubmitTransaction(message, 3)).thenReturn(false);
        comms.broadcastDesignSchoolCreation(new MapLocation(0,1));
        assertEquals(false, comms.broadcastedCreation);
    }

    //can broadcast
    @Test
    public void broadcastDesignSchoolExists1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1877;

        when(rc.canSubmitTransaction(message, 2)).thenReturn(true);
        comms.broadcastDesignSchoolExists();
    }

    //can't broadcast
    @Test
    public void broadcastDesignSchoolExists2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1877;

        when(rc.canSubmitTransaction(message, 2)).thenReturn(false);
        comms.broadcastDesignSchoolExists();
    }
    //can broadcast
    @Test
    public void broadcastSoupLocation1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 2;
        message[2] = 1; // x coord of HQ
        message[3] = 1; // y coord of HQ
        when(rc.canSubmitTransaction(message, 3)).thenReturn(true);
        comms.broadcastSoupLocation(new MapLocation(1,1));
    }

    //can't broadcast
    @Test
    public void broadcastSoupLocation2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 2;
        message[2] = 1; // x coord of HQ
        message[3] = 1; // y coord of HQ
        when(rc.canSubmitTransaction(message, 3)).thenReturn(false);
        comms.broadcastSoupLocation(new MapLocation(1,1));
    }

    @Test
    public void updateSoupLocations() {
    }

    //can send
    @Test
    public void sendRefineryLocation1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 2; //0 Designates it is Refinery Location
        message[2] = 1;
        message[3] = 1;

        when(rc.canSubmitTransaction(message, 2)).thenReturn(true);
        comms.sendRefineryLocation(new MapLocation(1,1));
    }

    //can't send
    @Test
    public void sendRefineryLocation2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 2; //0 Designates it is Refinery Location
        message[2] = 1;
        message[3] = 1;

        when(rc.canSubmitTransaction(message, 2)).thenReturn(false);
        comms.sendRefineryLocation(new MapLocation(1,1));
    }

    @Test
    public void getRefineryLocations() {
    }

    //can broadcast
    @Test
    public void broadcastVaporatorExists1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 9645;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(true);
        comms.broadcastVaporatorExists();
    }

    //can't broadcast
    @Test
    public void broadcastVaporatorExists2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 9645;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(false);
        comms.broadcastVaporatorExists();
    }

    //can broadcast
    @Test
    public void broadcastNetgunMade1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 645;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(true);
        comms.broadcastNetgunMade();
    }

    //can't broadcast
    @Test
    public void broadcastNetgunMade2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 645;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(false);
        comms.broadcastNetgunMade();
    }

    @Test
    public void broadcastFulfillmentCenterExists() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 77;
        when(rc.getLocation()).thenReturn(new MapLocation(1,1));
        when(rc.getLocation()).thenReturn(new MapLocation(1,1));
        doNothing().when(rc).submitTransaction(message, 2);
        comms.broadcastFulfillmentCenterExists();
    }

    @Test
    public void broadcastDeliveryDrone() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 88;
        doNothing().when(rc).submitTransaction(message, 2);
        comms.broadcastDeliveryDrone();
    }

    @Test
    public void broadcastLandscaperExists() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 199;
        doNothing().when(rc).submitTransaction(message, 2);
        comms.broadcastLandscaperExists();
    }

    //don't go in for loop at all
    @Test
    public void landscaperExistsFalse1() throws GameActionException {
        when(rc.getRoundNum()).thenReturn(1);
        assertEquals(0,comms.landscaperExists());
    }

    @Test
    public void landscaperExistsTrue() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 199;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(1, comms.landscaperExists());
    }

    //go in for loop but drop out
    @Test
    public void landscaperExistsFalse2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 2;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(0, comms.landscaperExists());
    }

    //found other team's transaction block
    @Test
    public void landscaperExistsFalse3() throws GameActionException {
        int[] message = new int[7];
        message[0] = 0;
        message[1] = 199;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(0, comms.landscaperExists());
    }

    //increment drone count
    @Test
    public void deliveryDroneCount1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 88;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(1, comms.deliveryDroneCount());
    }

    //don't increment drone count
    @Test
    public void deliveryDroneCount0() throws GameActionException {
        int[] message = new int[7];
        message[0] = 0;
        message[1] = 88;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(0, comms.deliveryDroneCount());
    }

    //don't increment drone count
    @Test
    public void deliveryDroneCount0_diffMsg() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 89;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(0, comms.deliveryDroneCount());
    }

    @Test
    public void fulfillmentCenterExistsTrue() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 77;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(true, comms.fulfillmentCenterExists());
    }

    //uses fulfillment center code 77
    @Test
    public void fulfillmentCenterExistsFalse1() throws GameActionException {
        int[] message = new int[7];
        message[0] = 0;
        message[1] = 77;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false, comms.fulfillmentCenterExists());
    }

    //does not use fulfillment center code
    @Test
    public void fulfillmentCenterExistsFalse2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 78;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false, comms.fulfillmentCenterExists());
    }

    @Test
    public void designSchoolExistsTrue() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1877;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(true,comms.designSchoolExists());
    }

    //des not use design code key 1877
    @Test
    public void designSchoolExistsFalse1() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 187;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false,comms.designSchoolExists());
    }

    //not our team key
    @Test
    public void designSchoolExistsFalse2() throws GameActionException {
        int[] message = new int[7];
        message[0] = secretTeamKey+1;
        message[1] = 1877;
        message[2] = 1;
        message[3] = 1;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(3);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false,comms.designSchoolExists());
    }

    @Test
    public void broadcastWaterLocationTrue() throws GameActionException {
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 10009;
        message[2] = 2;
        message[3] = 2;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(true);
        comms.broadcastWaterLocation(new MapLocation(2,2));
    }

    @Test
    public void broadcastWaterLocationFalse() throws GameActionException {
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 10009;
        message[2] = 2;
        message[3] = 2;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(false);
        comms.broadcastWaterLocation(new MapLocation(2,2));
    }

    @Test
    public void broadcastRefineryExistsTrue() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 8897;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(true);
        comms.broadcastRefineryExists();
    }

    @Test
    public void broadcastRefineryExistsFalse() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 8897;
        when(rc.canSubmitTransaction(message, 2)).thenReturn(false);
        comms.broadcastRefineryExists();
    }

    @Test
    public void refineryExistsFalse() throws GameActionException{
        when(rc.getRoundNum()).thenReturn(1);
        assertEquals(false, comms.refineryExists());
    }

    @Test
    public void refineryExistsTrue() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 8897;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};

        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(true, comms.refineryExists());
    }

    //wrong team key
    @Test
    public void refineryExistsFalse1() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey+1;
        message[1] = 8897;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};

        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false, comms.refineryExists());
    }

    //not refinery code
    @Test
    public void refineryExistsFalse2() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 889;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};

        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false, comms.refineryExists());
    }

    @Test
    public void refineryExistsNull() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 8897;
        //Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = null;

        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false,comms.refineryExists());
    }

    @Test
    public void waterLocationKnownTrue() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 10009;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(true,comms.waterLocationKnown());
    }

    //no transactions
    @Test
    public void waterLocationKnownFalse1() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 10009;
        Transaction[] txs = null;
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false,comms.waterLocationKnown());
    }

    //not water location key
    @Test
    public void waterLocationKnownFalse2() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey;
        message[1] = 1000;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false, comms.waterLocationKnown());
    }

    //not team key
    @Test
    public void waterLocationKnownFalse3() throws GameActionException{
        int [] message = new int[7];
        message[0] = secretTeamKey+1;
        message[1] = 10009;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(txs);
        assertEquals(false,comms.waterLocationKnown());
    }

    //only first round
    @Test
    public void waterLocationKnownFalse() throws GameActionException{
        when(rc.getRoundNum()).thenReturn(1);
        assertEquals(false,comms.waterLocationKnown());
    }

    @Test
    public void guessBlockchainNoLoop() throws GameActionException {
        when(rc.getRoundNum()).thenReturn(1);
        comms.guessBlockchain();
    }

    @Test
    public void guessBlockchainLoop() throws GameActionException {
        int [] message = new int[7];
        message[0] = secretTeamKey+1;
        message[1] = 10009;
        Transaction tx = new Transaction(1,message,2);
        Transaction[] txs = {tx};
        when(rc.getRoundNum()).thenReturn(5);
        when(rc.getBlock(1)).thenReturn(txs);
        comms.guessBlockchain();
    }
}