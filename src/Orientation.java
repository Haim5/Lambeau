import java.util.Map;

public class Orientation {
    public static byte W = 0;
    public static byte H = 1;
    public static byte D = 2;

    private int width;
    private int height;
    private int depth;

    public Orientation(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
    }

    public int getW() {
        return this.width;
    }

    public int getH() {
        return this.height;
    }

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
