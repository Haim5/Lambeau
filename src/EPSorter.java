import java.util.*;

public class EPSorter {

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

    private void mergeAll(List<EP> dest, Map<Integer, Set<EP>> source) {
        ArrayList<Integer> keys = new ArrayList<>(source.keySet());
        Collections.sort(keys);
        for(int k : keys) {
            ArrayList<EP> l = new ArrayList<>(source.get(k));
            Collections.sort(l, new XComparator());
            dest.addAll(l);
        }
    }

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

    public List<EP> sort(Set<EP> s) {
        return this.sort(new ArrayList<>(s));
    }

    private static class XComparator implements Comparator<EP> {
        @Override
        public int compare(EP e1, EP e2) {
            return Integer.compare(e1.getPoint().getX(), e2.getPoint().getX());
        }
    }

    private abstract static class Getter {
        public abstract int getValue(EP ep);
    }

    private static class Z extends Getter {
        public int getValue(EP ep) {
            return ep.getPoint().getZ();
        }
    }

    private static class Y extends Getter {
        public int getValue(EP ep) {
            return ep.getPoint().getY();
        }
    }
}
