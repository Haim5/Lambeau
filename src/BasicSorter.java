import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * See it. Say it. Sorted.
 */
public class BasicSorter implements Sorter {
    private final Comparator<Package> comp;

    /**
     * Constructor.
     * @param p package comparator.
     */
    public BasicSorter(Comparator<Package> p) {
        this.comp = p;
    }

    public List<Package> sort(List<Package> l, boolean isDescending) {
        if (l == null) {
            return new LinkedList<>();
        }
        List<Package> ans = new LinkedList<>(l);
        if (isDescending) {
            ans.sort(this.comp.reversed());
        } else {
            ans.sort(this.comp);
        }
        return ans;
    }

    public List<Package> sort(List<Package> l) {
        return this.sort(l, true); // default set to true
    }
}
