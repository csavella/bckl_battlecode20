package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommunicationsTest {

    private Robot testRobot;
    private RobotController rc;
    private Navigation testNav;
    public Transaction testTrans;
    public  MapLocation map;
    public Communications comm;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        int[] intArray = new int[]{ 729384,0,3,4,0,0,0};
        testTrans = new Transaction(2,intArray,472);
        Transaction testArray [] = {testTrans};
        map = mock(MapLocation.class);
        when(map.x).thenReturn(1);
        when(map.y).thenReturn(2);
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getBlock(1)).thenReturn(testArray);
    }

    @Test
    public void testgetHqLocFromBlockchain() throws GameActionException {
        MapLocation a;
        a = comm.getHqLocFromBlockchain();
        assertTrue(a.equals(new MapLocation(3,4)));
    }
}