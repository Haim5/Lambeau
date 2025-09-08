import java.util.Comparator;

/**
 * sort packages by base area.
 */
public class BaseAreaSorter extends BasicSorter {

    /**
     * Constructor
     */
    public BaseAreaSorter() {
        super(new BaseAreaComparator());
    }

    /**
     * base area comparator
     */
    private static class BaseAreaComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            return Integer.compare(o1.getBaseArea(), o2.getBaseArea());
        }
    }
}
