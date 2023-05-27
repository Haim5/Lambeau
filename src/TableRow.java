import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

public interface TableRow {

    public Object getValueAt(int column);

    void appendXml(Document doc, Element e);
}