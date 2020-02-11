package SmackDat2;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BuildingTest {

    private Building testBuilding;
    private RobotController rc;

    @Before
    public void setUp() throws Exception {
        rc = mock(RobotController.class);
        testBuilding = new Building(rc);
    }

    @Test
    public void takeTurn() throws GameActionException {
        testBuilding.takeTurn();
    }
}