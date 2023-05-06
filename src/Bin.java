import java.util.*;

public class Bin {

    private final int width;
    private final int height;
    private final int depth;

    private int leftVolume;

    private final HashMap<Package, Location> locations = new HashMap<>();
    private final HashMap<Point, EP> epMap = new HashMap<>();

    public Bin(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
        Point p1 = new Point();
        this.epMap.put(p1, new EP(p1, this.width, this.depth, this.height));
        this.leftVolume = w * h * d;
    }

    public Bin(Bin b) {
        this(b.getWidth(), b.getHeight(), b.getDepth());
    }

    public void clear() {
        this.locations.clear();
        this.epMap.clear();
        this.leftVolume = this.width * this.height * this.depth;
    }

    public List<EP> getPossibleEP(Package p) {
        LinkedList<EP> possible = new LinkedList<>();
        Set<Point> pts = this.epMap.keySet();
        for(Point p1 : pts) {
            EP ep1 = this.epMap.get(p1);
            if(ep1.canPut(p)) {
                possible.add(ep1);
            }
        }
        return possible;
    }

    public void put(Package p, EP where, Orientation how) {
        this.leftVolume -= p.getVolume();
        putHelp(p, where, how);
    }

    private void putHelp(Package p, EP where, Orientation how) {
        this.epMap.remove(where.getPoint());
        Set<Point> pts = this.epMap.keySet();
        for (Point p1 : pts) {
            this.epMap.get(p1).update(how, where);
        }
        Set<EP> adds = where.getNextEP(how);
        Location loc = new Location(this, where.getPoint(), how);
        this.locations.put(p, loc);
        for(EP ep1 : adds) {
            Point k = ep1.getPoint();
            this.epMap.put(k, ep1);
        }
    }

    public int getBaseArea() {
        return this.depth * this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getVolume() {
        return this.height * this.depth * this.width;
    }

    public int getLeftVolume() {
        return this.leftVolume;
    }

    public int getDepth() {
        return this.depth;
    }

    public boolean canItFit(Package p) {
        Set<Orientation> orientations = p.getOrientations();
        for(Orientation o : orientations) {
            if (o.getD() <= this.depth && o.getH() <= this.height && o.getW() <= this.width) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        Set<Package> keys = this.locations.keySet();
        StringBuilder sb = new StringBuilder();
        for(Package k : keys) {
            sb.append(k);
            sb.append(" Or: ");
            sb.append(this.locations.get(k).getOrientation());
            sb.append(" @ ");
            sb.append(this.locations.get(k).getBackBottomLeftPoint());
            sb.append("\n");
        }
        sb.append("Volume left: ");
        sb.append(this.leftVolume);
        sb.append("\n");
        return sb.toString();
    }



    public Map<Point, Orientation> getLocations() {
        Map<Point, Orientation> ans = new HashMap<>();
        Set<Package> packs = this.locations.keySet();
        for (Package p : packs) {
            Location loc = this.locations.get(p);
            Orientation o = loc.getOrientation();
            Point point = loc.getBackBottomLeftPoint();
            ans.put(point, o);
        }
        return ans;
    }
}
