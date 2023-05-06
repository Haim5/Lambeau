import java.util.Collections;
import java.util.List;

public class DefaultSorter implements Sorter {
    @Override
    public List<Package> sort(List<Package> l) {
        return l;
    }

    @Override
    public List<Package> sort(List<Package> l, boolean isDescending) {
        Collections.reverse(l);
        return l;
    }
}
