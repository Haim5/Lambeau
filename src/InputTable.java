import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Input table - used to represent Tables in the GUI.
 */
public class InputTable extends JFrame {

    private final JScrollPane scroller;
    private JTable table;
    private LinkedList<TableRow> rows;
    private final ArrayList<String> names;
    private final ArrayList<Class> cls;
    private final int cols;

    /**
     * Constructor.
     * @param title table title.
     * @param n list of feature names.
     * @param c classes of feature values.
     */
    public InputTable(String title, ArrayList<String> n, ArrayList<Class> c) {
        this.rows = new LinkedList<>();
        this.cls = c;
        this.names = n;
        this.cols = n.size();
        this.scroller = initialize(title);
    }

    /**
     * get scroller.
     * @return JScrollPane.
     */
    public JScrollPane getScroller() {
        return this.scroller;
    }

    /**
     * get the table rows.
     * @return list of table rows.
     */
    public LinkedList<TableRow> getRows() {
        return this.rows;
    }

    /**
     * initialize a JScrollPane
     * @param title table title.
     * @return JScrollPane
     */
    private JScrollPane initialize(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        // Create a linked list of TableRow objects
        rows = new LinkedList<>();

        // Create a custom TableModel using the linked list
        TableModel model = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return rows.size();
            }

            @Override
            public int getColumnCount() {
                return cols; // Number of columns
            }

            @Override
            public Object getValueAt(int row, int column) {
                TableRow rowData = rows.get(row);
                return rowData.getValueAt(column);
            }

            @Override
            public String getColumnName(int column) {
                return names.get(column);
            }

            @Override
            public Class getColumnClass(int column) {
                return cls.get(column);
            }
        };

        // Create the JTable with the custom TableModel
        table = new JTable(model);
        return new JScrollPane(table);
    }

    /**
     * add a new row.
     * @param tr table row to add.
     */
    public void add(TableRow tr) {
        this.rows.add(tr);
        this.table.revalidate();
        this.table.repaint();
    }

    /**
     * remove a row.
     */
    public void remove() {
        int r = this.table.getSelectedRow();
        if (r >= 0 && r < this.rows.size()) {
            this.rows.remove(r);
            this.table.revalidate();
            this.table.repaint();
        }
    }

    /**
     * remove all the rows.
     */
    public void removeAllRows() {
        this.rows.clear();
        this.table.revalidate();
        this.table.repaint();
    }

    /**
     * convert table to xml document.
     * @param doc the document.
     * @param name name.
     * @return xml Node.
     */
    public Node toXml(Document doc, String name) {
        Element e = doc.createElement(name);
        for (TableRow tr : this.rows) {
            tr.appendXml(doc, e);
        }
        return e;
    }
}
