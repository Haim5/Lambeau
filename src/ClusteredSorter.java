import java.util.*;

/**
 * Clustered sorter.
 */
public abstract class ClusteredSorter implements Sorter {

    private final Sorter s;
    private final int lambda;
    private final int binSize;
    private final Map<Integer, List<Package>> clusters = new HashMap<>();
    private final Set<Integer> indexes = new HashSet<>();

    /**
     * Constructor.
     * @param binSize the size of the bin.
     * @param s1 package sorter
     * @param lambda lambda value - set the clusters range.
     */
    public ClusteredSorter(int binSize, Sorter s1, int lambda) {
        this.s = s1;
        this.lambda = lambda;
        this.binSize = binSize;
    }

    /**
     * calculate the cluster index.
     * @param val value used to assign cluster.
     * @return int - index
     */
    protected int getIndex(int val) {
        if (this.lambda == 0) {
            return 0;
        }
        return (int)(Math.ceil((double)(100 * val) / (double) (this.lambda * this.binSize)));
    }

    /**
     * put the package in a cluster.
     * @param p package.
     * @param val values used to assign a cluster.
     */
    private void putInCluster(Package p, int val) {
        // get cluster index.
        int index = this.getIndex(val);
        // add package to cluster.
        if(!this.clusters.containsKey(index)) {
            this.clusters.put(index, new LinkedList<>());
        }
        this.clusters.get(index).add(p);
        this.indexes.add(index);
    }

    /**
     * add every item in the list to a cluster.
     * @param l list of packages.
     */
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

    /**
     * Sort the list, decide ascending/descending.
     * @param l list to sort.
     * @param isDescending1 should the first sort be descending.
     * @param isDescending2 should the second sort be descending.
     * @return
     */
    protected List<Package> sort(List<Package> l, boolean isDescending1, boolean isDescending2) {
        this.clusters.clear();
        // add items to clusters.
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
