import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Container table row class - visual component in the gui used to represent the containers.
 */
public class ContainerTableRow implements TableRow {
    public final static ArrayList<Class> CLASSES = new ArrayList<>(Arrays.asList(String.class, Double.class,
            Double.class, Double.class, Double.class));
    public final static ArrayList<String> NAMES = new ArrayList<>(Arrays.asList("Name", "Width", "Depth",
            "Height", "Quantity"));
    private final String name;
    private final double depth;
    private final double width;
    private final double height;
    private final double quantity;

    /**
     * Constructor.
     * @param name bin name
     * @param width bin width
     * @param depth bin depth
     * @param height bin height
     * @param q quantity of bins of this type.
     */
    public ContainerTableRow(String name, double width, double depth, double height, double q) {
        this.name = name;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.quantity = q;
    }

    /**
     * get the name
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
     * get the quantity field.
     * @return double
     */
    public double getQuantity() {
        return quantity;
    }

    @Override
    public Object getValueAt(int column) {
        return switch (column) {
            case 0 -> this.getName();
            case 1 -> this.getWidth();
            case 2 -> this.getDepth();
            case 3 -> this.getHeight();
            case 4 -> this.getQuantity();
            default -> null;
        };
    }

    @Override
    public void appendXml(Document doc, Element e) {
        Element container = doc.createElement("CONTAINER");
        container.setAttribute("name", this.name);
        container.setAttribute("width", String.format("%.2f", this.width));
        container.setAttribute("depth", String.format("%.2f", this.depth));
        container.setAttribute("height", String.format("%.2f", this.height));
        container.setAttribute("quantity", String.format("%.2f", this.quantity));
        if(this.quantity == Double.POSITIVE_INFINITY) {
            container.setAttribute("isModel", "true");
        } else {
            container.setAttribute("isModel", "false");
        }
        e.appendChild(container);
    }

    /**
     * convert a row to list a Bin.
     * @return lost of bins.
     */
    public List<Bin> toBin() {
        List<Bin> bins = new ArrayList<>();
        if (this.quantity == Double.POSITIVE_INFINITY) {
            bins.add(new Bin(this.name, (int)this.width, (int)this.height, (int)this.depth));
        } else {
            for (int i = 0; i < this.quantity; i++) {
                bins.add(new Bin(this.name, (int)this.width, (int)this.height, (int)this.depth));
            }
        }
        return bins;
    }
}
