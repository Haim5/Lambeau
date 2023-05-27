import java.util.List;
import java.util.Map;
import java.util.Set;

public class SolutionValidityCheck {

    public boolean check(Solution solution) {
        List<Bin> bins = solution.getBins();
        for (Bin b : bins) {
            boolean[][][] arr = new boolean[b.getWidth()][b.getDepth()][b.getHeight()];
            Set<Location> loc = b.getLocations().keySet();
            for(Location l : loc) {
                Orientation o = l.getOrientation();
                Point p = l.getBackBottomLeftPoint();
                if(p.getX() + o.getW() > b.getWidth() || p.getY() + o.getD() > b.getDepth() || p.getZ() + o.getH() > b.getHeight()) {
                    return false;
                }
                for (int x = p.getX(); x < o.getW(); x++) {
                    for(int y = p.getY(); y < o.getD(); y++) {
                        for(int z = p.getZ(); z < o.getH(); z++) {
                            if(arr[x][y][z]) {
                                return false;
                            }
                            arr[x][y][z] = true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
