/**
 * Location class.
 */
public class Location {
    private final Bin b;
    private final Point backBottomLeft;
    private final Orientation orientation;

    /**
     * Constructor
     * @param b bin.
     * @param p point.
     * @param o orientation.
     */
    public Location(Bin b, Point p, Orientation o) {
        this.b = b;
        this.backBottomLeft = p;
        this.orientation = o;
    }

    /**
     * get the orientation.
     * @return Orientation.
     */
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * get the bottom left back point of the the box.
     * @return Point.
     */
    public Point getBackBottomLeftPoint() {
        return this.backBottomLeft;
    }

    /**
     * get the bin.
     * @return Bin.
     */
    public Bin getBin() {
        return this.b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location loc = (Location) obj;
        return this.backBottomLeft.equals(loc.getBackBottomLeftPoint()) &&
                this.b.equals(loc.getBin()) && this.orientation.equals(loc.getOrientation());
    }
}
