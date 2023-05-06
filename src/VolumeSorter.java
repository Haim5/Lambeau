import java.util.Comparator;


public class VolumeSorter extends BasicSorter {
    public VolumeSorter() {
        this.setComparator(new VolumeComparator());
    }


    private static class VolumeComparator implements Comparator<Package> {

        @Override
        public int compare(Package o1, Package o2) {
            return Integer.compare(o1.getVolume(), o2.getVolume());
        }
    }
}
