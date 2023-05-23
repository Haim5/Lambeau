import java.util.*;

public abstract class ClusteredSorter implements Sorter {

    private final Sorter s;
    private final int lambda;
    private final int binSize;
    private final Map<Integer, List<Package>> clusters = new HashMap<>();
    private final Set<Integer> indexes = new HashSet<>();
    public ClusteredSorter(int binSize, Sorter s1, int lambda) {
        this.s = s1;
        this.lambda = lambda;
        this.binSize = binSize;
    }

    protected int getIndex(int val) {
        if (this.lambda == 0) {
            return 0;
        }
        return (int)(Math.ceil((double)(100 * val) / (double) (this.lambda * this.binSize)));
    }

    private void putInCluster(Package p, int val) {
        int index = this.getIndex(val);
        if(!this.clusters.containsKey(index)) {
            this.clusters.put(index, new LinkedList<>());
        }
        this.clusters.get(index).add(p);
        this.indexes.add(index);
    }

    private void addAll(List<Package> l) {
        for(Package p : l) {
            this.putInCluster(p, this.getValue(p));
        }
    }

    public List<Package> sort(List<Package> l) {
        return this.sort(l, true, true);
    }

    public List<Package> sort(List<Package> l, boolean isDescending) {
        return this.sort(l, isDescending, isDescending);
    }

    protected List<Package> sort(List<Package> l, boolean isDescending1, boolean isDescending2) {
        this.clusters.clear();
        this.addAll(l);
        List<Integer> indexList = new ArrayList<>(this.indexes);
        if(isDescending1) {
            Collections.sort(indexList, Collections.reverseOrder());
        } else {
            Collections.sort(indexList);
        }
        LinkedList<Package> ans = new LinkedList<>();
        for (int index : indexList) {
            ans.addAll(this.s.sort(this.clusters.get(index), isDescending2));
        }
        return ans;
    }

    protected abstract int getValue(Package p);
}
