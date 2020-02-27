package SmackDat2;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import com.google.inject.matcher.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MinerTest {

    private Miner m;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        m = new Miner(rc);
        m.nav = mock(Navigation.class);
        m.comms = mock(Communications.class);
    }

    @Test
    public void tryMineFalse() throws GameActionException {
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions)
            assertEquals(m.tryMine(dir), false);
    }

    @Test
    public void tryMineTrue() throws GameActionException {
        when(rc.isReady()).thenReturn(true);
        when(rc.canMineSoup(any())).thenReturn(true);
        for (Direction dir : Util.directions)
            assertEquals(m.tryMine(dir), true);
    }

    @Test
    public void tryRefineFalse() throws GameActionException{
        when(rc.isReady()).thenReturn(false);
        for (Direction dir : Util.directions)
            assertEquals(m.tryRefine(dir), false);
    }

    @Test
    public void tryRefineTrue() throws GameActionException {
        when(rc.isReady()).thenReturn(true);
        when(rc.canDepositSoup(any())).thenReturn(true);
        for (Direction dir : Util.directions)
            assertEquals(m.tryRefine(dir), true);
    }

    @Test
    public void checkIfSoupGoneEmpty() throws GameActionException{
        m.checkIfSoupGone();
    }

    @Test
    public void checkIfSoupGone() throws GameActionException{
        MapLocation ml = new MapLocation(1,1);
        m.soupLocations.add(ml);
        when(rc.canSenseLocation(any())).thenReturn(true);
        when(rc.senseSoup(any())).thenReturn(0);
        m.checkIfSoupGone();
    }

    @Test
    public void findSoup1() throws GameActionException {
        m.hqLoc = new MapLocation(1,1);
        m.turnCount = 11;
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        when(rc.getSoupCarrying()).thenReturn(100);
        when(rc.getLocation()).thenReturn(new MapLocation(2,2));
        for (Direction dir : Util.directions) {
            when(m.nav.goTo(dir)).thenReturn(true);
        }
        m.findSoup(10);
    }

    @Test
    public void findSoup2() throws GameActionException {
        m.hqLoc = new MapLocation(1,1);
        m.turnCount = 11;
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        when(rc.getSoupCarrying()).thenReturn(100);
        when(rc.getLocation()).thenReturn(new MapLocation(2,2));
        for (Direction dir : Util.directions) {
            when(m.nav.goTo(dir)).thenReturn(false);
        }
        m.findSoup(10);
    }

    @Test
    public void findSoup3() throws GameActionException {
        MapLocation firstSoup = new MapLocation(3,3);
        m.hqLoc = new MapLocation(1,1);
        m.turnCount = 11;
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        when(rc.getSoupCarrying()).thenReturn(99);
        m.soupLocations.add(firstSoup);
        for (Direction dir : Util.directions) {
            when(m.nav.goTo(dir)).thenReturn(false);
        }
        m.findSoup(10);
    }

    @Test
    public void findSoup4() throws GameActionException {
        m.hqLoc = new MapLocation(1,1);
        m.turnCount = 11;
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        when(rc.getSoupCarrying()).thenReturn(99);
        for (Direction dir : Util.directions) {
            when(m.nav.goTo(dir)).thenReturn(true);
        }
        m.findSoup(10);
    }

    @Test
    public void findSoup5() throws GameActionException {
        m.hqLoc = new MapLocation(1,1);
        m.turnCount = 11;
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        when(rc.getSoupCarrying()).thenReturn(99);
        for (Direction dir : Util.directions) {
            when(m.nav.goTo(dir)).thenReturn(false);
        }
        m.findSoup(10);
    }

    @Test
    public void findSoup6() throws GameActionException {
        m.turnCount = 20;
        m.hqLoc = new MapLocation(1,1);
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        m.findSoup(10);
    }

    @Test
    public void findSoup7() throws GameActionException {
        m.turnCount = 20;
        m.hqLoc = new MapLocation(1,8);
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        m.findSoup(10);
    }

    @Test
    public void findSoup8() throws GameActionException {
        m.turnCount = 20;
        m.hqLoc = new MapLocation(8,8);
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        m.findSoup(10);
    }

    @Test
    public void findSoup9() throws GameActionException {
        m.turnCount = 20;
        m.hqLoc = new MapLocation(8,2);
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        m.findSoup(10);
    }

    @Test
    public void findSoup10() throws GameActionException {
        m.turnCount = 20;
        m.hqLoc = new MapLocation(10,10);
        when(rc.getMapWidth()).thenReturn(30);
        when(rc.getMapHeight()).thenReturn(30);
        m.findSoup(10);
    }

    //hqX < mapX / 3 == true && hqY > mapY * 2 / 3 == false
    @Test
    public void findSoup11() throws GameActionException {
        m.turnCount = 20;
        m.hqLoc = new MapLocation(9,21);
        when(rc.getMapWidth()).thenReturn(30);
        when(rc.getMapHeight()).thenReturn(30);
        m.findSoup(10);
    }

    @Test
    public void takeTurn1() throws GameActionException {
        m.hqLoc = new MapLocation(1,1);
        when(m.comms.getNewDesignSchoolCount()).thenReturn(1);
        when(rc.getLocation()).thenReturn(new MapLocation(2,2));
        m.takeTurn();
    }
}