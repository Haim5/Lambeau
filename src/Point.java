public class Point {
    private int x;
    private int y;
    private int z;

    public Point(int x, int y, int z) {
        this.x= x;
        this.y = y;
        this.z = z;
    }

    public Point() {
        this(0, 0, 0);
    }

    public int getX() {
        return this.x;
    }
    public int getZ() {
        return this.z;
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.x);
        sb.append(",");
        sb.append(this.y);
        sb.append(",");
        sb.append(this.z);
        sb.append(")");
        return sb.toString();
    }
}
