package SmackDat2;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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

    @Test
    public void sendHqLoc() {
    }

    /*
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


    @Test
    public void getHqLocFromBlockchain() throws GameActionException {
        Transaction[] tx = {mock(Transaction.class)};
        when(rc.getRoundNum()).thenReturn(5);
        when(rc.getBlock(any())).thenReturn(tx);
    }
*/
    @Test
    public void getHqLocFromBlockchain11() throws GameActionException {
        when(rc.getRoundNum()).thenReturn(1);
        assertEquals(comms.getHqLocFromBlockchain(), new MapLocation(1,1));
    }


    @Test
    public void broadcastDesignSchoolCreation() throws GameActionException {
//        comms.broadcastedCreation = true;
        comms.broadcastDesignSchoolCreation(new MapLocation(0,1));
    }

//    @Test
//    public void getNewDesignSchoolCount() throws GameActionException {
//        when(rc.getBlock(any())).thenReturn(new Transaction [1]);
//        assertEquals(0,comms.getNewDesignSchoolCount());
//    }

    @Test
    public void broadcastSoupLocation() throws GameActionException {
//        when(rc.canSubmitTransaction(any(), any())).thenReturn(false);
        comms.broadcastSoupLocation(new MapLocation(0,1));
    }

    @Test
    public void updateSoupLocations() {
    }

    @Test
    public void sendRefineryLocation() {
    }

    @Test
    public void getRefineryLocations() {
    }

    @Test
    public void fulfillmentCenterExists() {
    }
}