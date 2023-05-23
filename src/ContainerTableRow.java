import java.util.ArrayList;
import java.util.List;

public class ContainerTableRow implements TableRow {
    private static final int COL = 5;
    private String name;
    private double depth;
    private double width;
    private double height;
    private double quantity;

    public ContainerTableRow(String name, double width, double depth, double height, double q) {
        this.name = name;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.quantity = q;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() {
        return depth;
    }

    public double getQuantity() {
        return quantity;
    }

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


    public static ArrayList<String> NAMES() {
        ArrayList<String> cols = new ArrayList<>();
        cols.add("Name");
        cols.add("Width");
        cols.add("Depth");
        cols.add("Height");
        cols.add("Quantity");
        return cols;
    }


    public static ArrayList<Class> CLASSES() {
        ArrayList<Class> cls = new ArrayList<>();
        cls.add(String.class);
        cls.add(Double.class);
        cls.add(Double.class);
        cls.add(Double.class);
        cls.add(Double.class);
        return cls;
    }

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
