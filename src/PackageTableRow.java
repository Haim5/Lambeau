import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PackageTableRow implements TableRow {
    private static final int COL = 7;
    private final boolean canFlip;
    private final double group;
    private String name;
    private long id;
    private double depth;
    private double width;
    private double height;

    public PackageTableRow(String name, long id, double width, double depth, double height, boolean canFlip, double group) {
        this.name = name;
        this.id = id;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.canFlip = canFlip;
        this.group = group;
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

    public double getGroup() {
        return group;
    }

    public long getId() {
        return this.id;
    }

    public Object getFlip() {
        return this.canFlip;
    }



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


    public static ArrayList<String> NAMES() {
        ArrayList<String> cols = new ArrayList<>();
        cols.add("Name");
        cols.add("ID");
        cols.add("Width");
        cols.add("Depth");
        cols.add("Height");
        cols.add("This Side Up");
        cols.add("Delivery Group");
        return cols;
    }


    public static ArrayList<Class> CLASSES() {
        ArrayList<Class> cls = new ArrayList<>();
        cls.add(String.class);
        cls.add(Long.class);
        cls.add(Double.class);
        cls.add(Double.class);
        cls.add(Double.class);
        cls.add(Boolean.class);
        cls.add(Double.class);
        return cls;
    }

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
