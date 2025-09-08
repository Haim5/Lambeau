import java.util.*;

/**
 * Bin class, represents a shipping container.
 */
public class Bin {

    private final int width;
    private final int height;
    private final int depth;
    private final String name;
    private int leftVolume;

    private final HashMap<Location, Package> locations = new HashMap<>();
    private final HashMap<Point, EP> epMap = new HashMap<>();

    /**
     * Constructor, no name.
     * @param w width
     * @param h height
     * @param d depth
     */
    public Bin(int w, int h, int d) {
        this("", w, h, d);
    }

    /**
     * Constructor.
     * @param name name
     * @param w width
     * @param h height
     * @param d depth
     */
    public Bin(String name, int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
        // init extreme point map.
        Point p1 = new Point();
        this.epMap.put(p1, new EP(p1, this.width, this.depth, this.height));
        this.leftVolume = w * h * d;
        this.name = name;
    }

    /**
     * Copy constructor
     * @param b bin to copy.
     */
    public Bin(Bin b) {
        this(b.getName(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    /**
     * get the bin's name
     * @return string
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the possible ep that package o can be placed at.
     * @param p package
     * @return list of EP
     */
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

    /**
     * put the package at the EP in Orientation O
     * @param p package
     * @param where EP - where to put the package
     * @param how Orientation - how to put the package.
     */
    public void put(Package p, EP where, Orientation how) {
        this.leftVolume -= p.getVolume();
        // remove the ep from the map
        this.epMap.remove(where.getPoint());
        Set<Point> pts = this.epMap.keySet();
        // update the other extreme points
        for (Point p1 : pts) {
            this.epMap.get(p1).update(how, where);
        }
        // add new extreme points.
        Set<EP> adds = where.getNextEP(how);
        Location loc = new Location(this, where.getPoint(), how);
        this.locations.put(loc, p);
        for(EP ep1 : adds) {
            Point k = ep1.getPoint();
            this.epMap.put(k, ep1);
        }
    }

    /**
     * get the base area of the bin.
     * @return int
     */
    public int getBaseArea() {
        return this.depth * this.width;
    }

    /**
     * get the bin's height.
     * @return int
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * get the bin's width.
     * @return int
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * get the bin's volume.
     * @return int
     */
    public int getVolume() {
        return this.height * this.depth * this.width;
    }

    /**
     * get the bin's left volume.
     * @return int
     */
    public int getLeftVolume() {
        return this.leftVolume;
    }

    /**
     * get the bin's depth.
     * @return int
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * can the package somehow fit in the bin.
     * @param p package ee try to fit in.
     * @return if it can fit - true, else - false.
     */
    public boolean canItFit(Package p) {
        // check if there is an orientation in which the package can fit in the bin.
        Set<Orientation> orientations = p.getOrientations();
        for(Orientation o : orientations) {
            if (o.getD() <= this.depth && o.getH() <= this.height && o.getW() <= this.width) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        List<Location> locs = new ArrayList<>(this.locations.keySet());
        LocationSorter ls = new LocationSorter();
        locs = ls.sort(locs);
        StringBuilder sb = new StringBuilder();
        if(!this.name.equals("")) {
            sb.append(this.name);
            sb.append(" ");
        }
        sb.append("(W:");
        sb.append(this.width);
        sb.append(" ,D:");
        sb.append(this.depth);
        sb.append(" ,H:");
        sb.append(this.height);
        sb.append(")\n\n");
        for(Location k : locs) {
            sb.append(this.locations.get(k));
            sb.append(" Or: ");
            sb.append(k.getOrientation());
            sb.append(" @ ");
            sb.append(k.getBackBottomLeftPoint());
            sb.append("\n");
        }
        sb.append("\nVolume left: ");
        sb.append(this.leftVolume);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    /**
     * get the packages locations inside the bin.
     * @return map of Location:Package.
     */
    public Map<Location, Package> getLocations() {
        return this.locations;
    }
}
