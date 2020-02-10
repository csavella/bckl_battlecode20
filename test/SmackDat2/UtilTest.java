package SmackDat2;

import battlecode.common.Direction;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest extends RobotTest{

    @Test
    public void randomDirection() {
        int north,northwest, west,southwest,south,southeast,east,northeast;
        north =northwest= west=southwest=south=southeast=east=northeast=0;
        for(int i=0;i<800;i++) {
            Direction d = Util.randomDirection();
            switch (d) {
                case NORTH:
                    north += 1;
                    break;
                case NORTHEAST:
                    northwest += 1;
                    break;
                case EAST:
                    east += 1;
                    break;
                case SOUTHEAST:
                    southeast += 1;
                    break;
                case SOUTH:
                    south += 1;
                    break;
                case SOUTHWEST:
                    southwest += 1;
                    break;
                case WEST:
                    west += 1;
                    break;
                case NORTHWEST:
                    northeast += 1;
                    break;
            }
        }

        boolean is_random= false ;
        /*System.out.println(north+"\n"+ west+"\n"+ northeast+"\n"+ northwest
                +"\n"+ south +"\n" + southeast + "\n" +southwest + "\n" + east); */
        int[] ar = {north,northwest, west,southwest,south,southeast,east,northeast};
        for (int num:ar) {
            if(num>75 && num<125)
                is_random=true;
        }
        assertTrue(is_random);
    }
}
