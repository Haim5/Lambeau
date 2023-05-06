import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * See it. Say it. Sorted.
 */
public abstract class BasicSorter implements Sorter {
    private Comparator<Package> comp;

    public List<Package> sort(List<Package> l, boolean isDescending) {
        Collections.sort(l, this.comp);
        if (isDescending) {
            Collections.reverse(l);
        }
        return l;
    }

    public List<Package> sort(List<Package> l) {
        return this.sort(l, false);
    }

    protected void setComparator(Comparator<Package> p) {
        this.comp = p;
    }
}
