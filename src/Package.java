import java.util.HashSet;
import java.util.Set;

/**
 * package class.
 */
public class Package {
    // used for generating id.
    private static long ID = 0;

    private final long id;
    private final String name;
    private final int width;
    private final int height;
    private final int depth;
    private int deliveryGroup = 0;
    private final Set<Orientation> orientations;

    /**
     * final
     * @param w width
     * @param h height
     * @param d depth
     * @param canFlip can we flip the package.
     * @param name package name.
     */
    public Package(int w, int h, int d, boolean canFlip, String name) {
        this.id = ID++;
        this.width = w;
        this.height = h;
        this.depth = d;
        this.name = name;
        this.orientations = new HashSet<>();
        this.setOrientations(canFlip);
    }

    /**
     * Constructor
     * @param w width.
     * @param h height.
     * @param d depth.
     * @param canFlip can we flip the package.
     * @param name package name.
     * @param id package id.
     */
    public Package(int w, int h, int d, boolean canFlip, String name, long id) {
        this.id = id;
        this.width = w;
        this.height = h;
        this.depth = d;
        this.name = name;
        this.orientations = new HashSet<>();
        this.setOrientations(canFlip);
    }

    /**
     * Constructor
     * @param w width.
     * @param h height.
     * @param d depth.
     * @param canFlip can we flip the package.
     * @param name package name.
     * @param id package id.
     * @param deliveryGroup package delivery group
     */
    public Package(int w, int h, int d, boolean canFlip, String name, long id, int deliveryGroup) {
        this.id = id;
        this.width = w;
        this.height = h;
        this.depth = d;
        this.name = name;
        this.orientations = new HashSet<>();
        this.setOrientations(canFlip);
        this.deliveryGroup = deliveryGroup;
    }

    public Package(int w, int h, int d, String name) {
        this(w, h, d, true, name);
    }

    public Package(int w, int h, int d) {
        this(w, h, d,true,"");
    }

    public Package(int w, int h, int d, boolean b) {
        this(w, h, d, b, "");
    }

    private void setOrientations(boolean flip) {
        this.orientations.add(new Orientation(this.width, this.height, this.depth));
        this.orientations.add(new Orientation(this.depth, this.height, this.width));
        if (flip) {
            this.orientations.add(new Orientation(this.height, this.width, this.depth));
            this.orientations.add(new Orientation(this.height, this.depth, this.width));
            this.orientations.add(new Orientation(this.width, this.depth, this.height));
            this.orientations.add(new Orientation(this.depth, this.width, this.height));
        }
    }

    public int getVolume() {
        return this.width * this.height * this.depth;
    }

    public int getHeight() {
        return this.height;
    }

    public int getBaseArea() {
        return this.depth * this.width;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Package)) {
            return false;
        }
        Package p = (Package)obj;
        return p.getId() == this.getId();
    }

    public Set<Orientation> getOrientations() {
        return this.orientations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Package: ");
        sb.append(this.name);
        sb.append(" ID: ");
        sb.append(this.id);
        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public int getDeliveryGroup() {
        return this.deliveryGroup;
    }
}
