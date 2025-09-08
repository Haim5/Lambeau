import java.util.Comparator;

/**
 * Sort Packages by volume
 */
public class VolumeSorter extends BasicSorter {
    public VolumeSorter() {
        super(new VolumeComparator());
    }

    /**
     * Volume comparator
     */
    private static class VolumeComparator implements Comparator<Package> {

        @Override
        public int compare(Package o1, Package o2) {
            return Integer.compare(o1.getVolume(), o2.getVolume());
        }
    }
}
