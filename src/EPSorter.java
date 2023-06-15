import java.util.*;

/**
 * Sort Extreme Points.
 */
public class EPSorter {

    /**
     * Sort a list of Extreme Points.
     * @param l list of EP.
     * @return sorted list of EP
     */
    public List<EP> sort(List<EP> l) {
        LinkedList<EP> ans = new LinkedList<>();
        Map<Integer, Set<EP>> m = this.subSort(l, new Z());
        ArrayList<Integer> keys = new ArrayList<>(m.keySet());
        Collections.sort(keys);
        for(int index : keys) {
            Map<Integer, Set<EP>> sub = this.subSort(new ArrayList<>(m.get(index)), new Y());
            this.mergeAll(ans, sub);
        }
        return ans;
    }

    /**
     * Merge all maps to a list.
     * @param dest destination list.
     * @param source the map to merge from.
     */
    private void mergeAll(List<EP> dest, Map<Integer, Set<EP>> source) {
        ArrayList<Integer> keys = new ArrayList<>(source.keySet());
        Collections.sort(keys);
        for(int k : keys) {
            ArrayList<EP> l = new ArrayList<>(source.get(k));
            Collections.sort(l, new XComparator());
            dest.addAll(l);
        }
    }

    /**
     * sort sub list of EP.
     * @param l the list
     * @param g the feature to sort by.
     * @return Map int:set of EP.
     */
    private Map<Integer, Set<EP>> subSort(List<EP> l, Getter g) {
        Map<Integer, Set<EP>> m = new HashMap<>();
        for (EP ep : l) {
            int i = g.getValue(ep);
            if(!m.containsKey(i)) {
                m.put(i, new HashSet<>());
            }
            m.get(i).add(ep);
        }
        return m;
    }

    /**
     * sort a set to a list.
     * @param s set of EP
     * @return list of EP.
     */
    public List<EP> sort(Set<EP> s) {
        return this.sort(new ArrayList<>(s));
    }

    /**
     * XComparator - compare by X values.
     */
    private static class XComparator implements Comparator<EP> {
        @Override
        public int compare(EP e1, EP e2) {
            return Integer.compare(e1.getPoint().getX(), e2.getPoint().getX());
        }
    }

    /**
     * Getter class - get a feature.
     */
    private abstract static class Getter {
        public abstract int getValue(EP ep);
    }

    /**
     * get Z feature.
     */
    private static class Z extends Getter {
        public int getValue(EP ep) {
            return ep.getPoint().getZ();
        }
    }

    /**
     * get Y feature.
     */
    private static class Y extends Getter {
        public int getValue(EP ep) {
            return ep.getPoint().getY();
        }
    }
}
