import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PackageTableRow implements TableRow {
    public final static ArrayList<Class> CLASSES = new ArrayList<>(Arrays.asList(String.class, Long.class, Double.class,
            Double.class, Double.class, Boolean.class, Double.class));
    public final static ArrayList<String> NAMES = new ArrayList<>(Arrays.asList("Name", "ID", "Width", "Depth",
            "Height", "This Side Up", "Delivery Group"));
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
