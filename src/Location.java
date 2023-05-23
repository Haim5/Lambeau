public class Location {
    private Bin b;
    private Point backBottomLeft;
    private Orientation orientation;

    public Location(Bin b, Point p, Orientation o) {
        this.b = b;
        this.backBottomLeft = p;
        this.orientation = o;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public Point getBackBottomLeftPoint() {
        return this.backBottomLeft;
    }

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
