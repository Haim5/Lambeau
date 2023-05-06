import java.util.Comparator;

public class HeightSorter extends BasicSorter {
    public HeightSorter() {
        this.setComparator(new HeightComparator());
    }

    private static class HeightComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            return Integer.compare(o1.getHeight(), o2.getHeight());
        }
    }
}
