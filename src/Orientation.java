/**
 * Orientation class.
 */
public class Orientation {
    private final int width;
    private final int height;
    private final int depth;

    /**
     * Constructor.
     * @param w width
     * @param h height
     * @param d depth
     */
    public Orientation(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
    }

    /**
     * get the width
     * @return width
     */
    public int getW() {
        return this.width;
    }

    /**
     * get the height
     * @return height
     */
    public int getH() {
        return this.height;
    }

    /**
     * get the depth
     * @return depth
     */
    public int getD() {
        return this.depth;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Orientation)) {
            return false;
        }
        Orientation p = (Orientation)obj;
        return p.getW() == this.width && p.getH() == this.height && p.getD() == this.depth;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("W:");
        sb.append(this.width);
        sb.append(" D:");
        sb.append(this.depth);
        sb.append(" H:");
        sb.append(this.height);
        return sb.toString();
    }
}
