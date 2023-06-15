/**
 * Point class -used to represent a 3D point.
 */
public class Point {
    private final int x;
    private final int y;
    private final int z;

    /**
     * Constructor.
     * @param x value of x
     * @param y value of y
     * @param z value of z
     */
    public Point(int x, int y, int z) {
        this.x= x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor with default values.
     */
    public Point() {
        this(0, 0, 0);
    }

    /**
     * get the value of X
     * @return int
     */
    public int getX() {
        return this.x;
    }

    /**
     * get the value of Z
     * @return int
     */
    public int getZ() {
        return this.z;
    }

    /**
     * get the value of Y
     * @return int
     */
    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point p = (Point)obj;
        return p.getX() == this.x && p.getY() == this.y && p.getZ() == this.z;
    }

    @Override
    public String toString() {
        return "(" +
                this.x +
                "," +
                this.y +
                "," +
                this.z +
                ")";
    }
}
