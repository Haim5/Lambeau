import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InputTable extends JFrame {

    private final JScrollPane scroller;
    private JTable table;
    private LinkedList<TableRow> rows;
    private ArrayList<String> names;
    private ArrayList<Class> cls;
    private int cols;

    public InputTable(String title, ArrayList<String> n, ArrayList<Class> c) {
        this.rows = new LinkedList<>();
        this.cls = c;
        this.names = n;
        this.cols = n.size();
        this.scroller = initialize(title);
    }

    public JScrollPane getScroller() {
        return this.scroller;
    }

    public LinkedList<TableRow> getRows() {
        return this.rows;
    }

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
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    public void add(TableRow tr) {
        this.rows.add(tr);
        this.table.revalidate();
        this.table.repaint();
    }

    public void remove() {
        int r = this.table.getSelectedRow();
        if (r >= 0 && r < this.rows.size()) {
            this.rows.remove(r);
            this.table.revalidate();
            this.table.repaint();
        }
    }

    public void removeAllRows() {
        this.rows.clear();
        this.table.revalidate();
        this.table.repaint();
    }

    public Node toXml(Document doc, String name) {
        Element e = doc.createElement(name);
        for (TableRow tr : this.rows) {
            tr.appendXml(doc, e);
        }
        return e;
    }
}
