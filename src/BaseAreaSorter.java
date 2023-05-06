import java.util.Comparator;

public class BaseAreaSorter extends BasicSorter {

    public BaseAreaSorter() {
        this.setComparator(new BaseAreaComparator());
    }

    private static class BaseAreaComparator implements Comparator<Package> {
        @Override
        public int compare(Package o1, Package o2) {
            return Integer.compare(o1.getBaseArea(), o2.getBaseArea());
        }
    }
}
