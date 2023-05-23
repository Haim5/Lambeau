import java.util.*;

public class LocationSorter {

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

    private void mergeAll(List<Location> dest, Map<Integer, Set<Location>> source) {
        ArrayList<Integer> keys = new ArrayList<>(source.keySet());
        Collections.sort(keys);
        for(int k : keys) {
            ArrayList<Location> l = new ArrayList<>(source.get(k));
            Collections.sort(l, new XComparator());
            dest.addAll(l);
        }
    }

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

    public List<Location> sort(Set<Location> s) {
        return this.sort(new ArrayList<>(s));
    }

    private static class XComparator implements Comparator<Location> {
        @Override
        public int compare(Location loc1, Location loc2) {
            return Integer.compare(loc1.getBackBottomLeftPoint().getX(), loc2.getBackBottomLeftPoint().getX());
        }
    }

    private abstract static class Getter {
        public abstract int getValue(Location loc);
    }

    private static class Z extends Getter {
        public int getValue(Location loc) {
            return loc.getBackBottomLeftPoint().getZ();
        }
    }

    private static class Y extends Getter {
        public int getValue(Location loc) {
            return loc.getBackBottomLeftPoint().getY();
        }
    }
}
