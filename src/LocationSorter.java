import java.util.*;

/**
 * Sort locations.
 */
public class LocationSorter {

    /**
     * sort a list of locations.
     * @param l list to sort.
     * @return sorted list of locations.
     */
    public List<Location> sort(List<Location> l) {
        LinkedList<Location> ans = new LinkedList<>();
        Map<Integer, Set<Location>> m = this.subSort(l, new Z());
        ArrayList<Integer> keys = new ArrayList<>(m.keySet());
        Collections.sort(keys);
        for(int index : keys) {
            Map<Integer, Set<Location>> sub = this.subSort(new ArrayList<>(m.get(index)), new Y());
            this.mergeAll(ans, sub);
        }
        return ans;
    }

    /**
     * merge all sub sorts.
     * @param dest where to merge into.
     * @param source where to merge from.
     */
    private void mergeAll(List<Location> dest, Map<Integer, Set<Location>> source) {
        ArrayList<Integer> keys = new ArrayList<>(source.keySet());
        Collections.sort(keys);
        for(int k : keys) {
            ArrayList<Location> l = new ArrayList<>(source.get(k));
            Collections.sort(l, new XComparator());
            dest.addAll(l);
        }
    }

    /**
     * sort a sub list.
     * @param l the sub list.
     * @param g the feature to sort by.
     * @return map.
     */
    private Map<Integer, Set<Location>> subSort(List<Location> l, Getter g) {
        Map<Integer, Set<Location>> m = new HashMap<>();
        for (Location loc : l) {
            int i = g.getValue(loc);
            if(!m.containsKey(i)) {
                m.put(i, new HashSet<>());
            }
            m.get(i).add(loc);
        }
        return m;
    }

    /**
     * sort a set of locations to a list.
     * @param s set of locations.
     * @return sorted list of locations.
     */
    public List<Location> sort(Set<Location> s) {
        return this.sort(new ArrayList<>(s));
    }

    /**
     * Comparator by X
     */
    private static class XComparator implements Comparator<Location> {
        @Override
        public int compare(Location loc1, Location loc2) {
            return Integer.compare(loc1.getBackBottomLeftPoint().getX(), loc2.getBackBottomLeftPoint().getX());
        }
    }

    /**
     * get a feature.
     */
    private abstract static class Getter {
        /**
         * get the value.
         * @param loc location
         * @return int.
         */
        public abstract int getValue(Location loc);
    }

    /**
     * Z getter.
     */
    private static class Z extends Getter {
        public int getValue(Location loc) {
            return loc.getBackBottomLeftPoint().getZ();
        }
    }

    /**
     * Y getter.
     */
    private static class Y extends Getter {
        public int getValue(Location loc) {
            return loc.getBackBottomLeftPoint().getY();
        }
    }
}
