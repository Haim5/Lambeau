import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Package table row - used for representing a table row of the package table.
 */
public class PackageTableRow implements TableRow {
    public final static ArrayList<Class> CLASSES = new ArrayList<>(Arrays.asList(String.class, Long.class, Double.class,
            Double.class, Double.class, Boolean.class, Double.class));
    public final static ArrayList<String> NAMES = new ArrayList<>(Arrays.asList("Name", "ID", "Width", "Depth",
            "Height", "This Side Up", "Delivery Group"));
    private final boolean canFlip;
    private final double group;
    private final String name;
    private final long id;
    private final double depth;
    private final double width;
    private final double height;

    /**
     * Constructor.
     * @param name package name
     * @param id package id.
     * @param width package width.
     * @param depth package depth.
     * @param height package height.
     * @param canFlip can we flip the package.
     * @param group package delivery group.
     */
    public PackageTableRow(String name, long id, double width, double depth, double height, boolean canFlip, double group) {
        this.name = name;
        this.id = id;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.canFlip = canFlip;
        this.group = group;
    }

    /**
     * get the mame field.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * get the height field.
     * @return double
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * get the width field.
     * @return double
     */
    public double getWidth() {
        return width;
    }

    /**
     * get the depth field.
     * @return double
     */
    public double getDepth() {
        return depth;
    }

    /**
     * get the delivery group field.
     * @return double
     */
    public double getGroup() {
        return group;
    }

    /**
     * get the ID field.
     * @return long
     */
    public long getId() {
        return this.id;
    }

    /**
     * can we flip the package.
     * @return obj
     */
    public Object getFlip() {
        return this.canFlip;
    }

    @Override
    public Object getValueAt(int column) {
        return switch (column) {
            case 0 -> this.getName();
            case 1 -> this.getId();
            case 2 -> this.getWidth();
            case 3 -> this.getDepth();
            case 4 -> this.getHeight();
            case 5 -> this.getFlip();
            case 6 -> this.getGroup();
            default -> null;
        };
    }

    @Override
    public void appendXml(Document doc, Element e) {
        Element p2 = doc.createElement("PACKAGE");
        p2.setAttribute("id", String.format("%d", this.id));
        p2.setIdAttribute("id", true);
        p2.setAttribute("name", this.name);
        p2.setAttribute("width", String.format("%.2f", this.width));
        p2.setAttribute("depth", String.format("%.2f", this.depth));
        p2.setAttribute("height", String.format("%.2f", this.height));
        p2.setAttribute("group", String.format("%.2f", this.group));
        if(this.canFlip) {
            p2.setAttribute("canFlip", "true");
        } else {
            p2.setAttribute("canFlip", "false");
        }
        e.appendChild(p2);
    }


    /**
     * convert a row to a package.
     * @return Package.
     */
    public Package toPack() {
        return new Package((int)this.width,
                (int)this.height,
                (int)this.depth,
                !this.canFlip,
                this.name,
                this.id,
                (int)this.group);
    }


}
