import java.util.Comparator;

/**
 * sort packages by height.
 */
public class HeightSorter extends BasicSorter {
    /**
     * Constructor.
     */
    public HeightSorter() {
        this.setComparator(new HeightComparator());
    }

    /**
     * Height Comparator.
     */
    private static class HeightComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            return Integer.compare(o1.getHeight(), o2.getHeight());
        }
    }
}
