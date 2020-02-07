package SmackDat2;

import battlecode.common.Direction;

// This is a file to accumulate all the random helper functions
// which don't interact with the game, but are common enough to be used in multiple places.
// For example, lots of logic involving MapLocations and Directions is common and ubiquitous.
public class Util {
    static Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST
    };

    /* these directions will be used to move the miners away from the HQ
       in hopes to find soup in different areas of the map */
    static Direction[] directions1 = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST
    };
    static Direction[] directions2 = {
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH
    };
    static Direction[] directions3 = {
            Direction.WEST,
            Direction.SOUTHWEST,
            Direction.SOUTH
    };
    static Direction[] directions4 = {
            Direction.WEST,
            Direction.NORTHWEST,
            Direction.NORTH
    };

    //static final int secretTeamKey = 729384;
    /**
     * Returns a random Direction.
     *
     * @return a random Direction
     */
    static Direction randomDirection() {
        return directions[(int) (Math.random() * directions.length)];
    }
}
