import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * See it. Say it. Sorted.
 */
public abstract class BasicSorter implements Sorter {
    private Comparator<Package> comp;

    public List<Package> sort(List<Package> l, boolean isDescending) {
        LinkedList<Package> ans = new LinkedList<>();
        if(l != null) {
            ans.addAll(l);
            Collections.sort(ans, this.comp);
            if (isDescending) {
                Collections.reverse(ans);
            }
        }
        return ans;
    }

    public List<Package> sort(List<Package> l) {
        return this.sort(l, false);
    }

    /**
     * set which comparator to use.
     * @param p package comparator.
     */
    protected void setComparator(Comparator<Package> p) {
        this.comp = p;
    }
}
