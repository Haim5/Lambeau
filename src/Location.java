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
}
