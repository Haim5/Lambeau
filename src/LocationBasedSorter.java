import java.util.*;

public class LocationBasedSorter implements Sorter {
    private Sorter sorter;

    public LocationBasedSorter(Sorter sorter) {
        if(!(sorter instanceof LocationBasedSorter)) {
            this.sorter = sorter;
        } else {
            this.sorter = new DefaultSorter();
        }
    }

    public LocationBasedSorter() {
        this.sorter = new DefaultSorter();
    }

    @Override
    public List<Package> sort(List<Package> l) {
        return this.sort(l, true);
    }

    @Override
    public List<Package> sort(List<Package> l, boolean isDescending) {
        Map<Integer, List<Package>> m = new HashMap<>();
        for(Package p : l) {
            int index = p.getDeliveryGroup();
            if (!m.containsKey(index)) {
                m.put(index, new LinkedList<>());
            }
            m.get(index).add(p);
        }
        ArrayList<Integer> indexes = new ArrayList<>(m.keySet());
        Collections.sort(indexes);
        if (isDescending) {
            Collections.reverse(indexes);
        }
        LinkedList<Package> ans = new LinkedList<>();
        for (int i : indexes) {
            ans.addAll(sorter.sort(m.get(i)));
        }
        return ans;
    }
}
