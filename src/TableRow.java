import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * TableRow interface
 */
public interface TableRow {

    /**
     * get the values at the given column
     * @param column the col number
     * @return Object
     */
    Object getValueAt(int column);

    /**
     * append data to xml document
     * @param doc the document
     * @param e the element to append
     */
    void appendXml(Document doc, Element e);
}