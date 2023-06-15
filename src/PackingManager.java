import java.util.*;

/**
 * PackingManager class - used to generate a packing solution.
 */
public class PackingManager {
    // optimal lambda values
    private static final List<Integer> OPT1 = new ArrayList<>(Arrays.asList(
            21, 22, 23, 24, 50, 51, 52, 53, 54, 55, 56, 57));
    private static final List<Integer> OPT2 = new ArrayList<>(Arrays.asList(
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60));

    private final List<Bin> b;
    private final List<Package> l;
    private final int constraint;
    private int vol = 0;

    /**
     * Constructor
     * @param b bins
     * @param p packages
     * @param constraint constraint level.
     */
    public PackingManager(List<Bin> b, List<Package> p, int constraint) {
        this.b = b;
        this.l = p;
        this.constraint = constraint;
        // calculate combined bins volume.
        for (Bin bin : b) {
            this.vol += bin.getVolume();
        }
    }

    /**
     * find a solution to the packing problem.
     * @return Solution.
     */
    public Solution findSolution() {
        int volume = 0;
        // calculate the combined packages volumes.
        for(Package pack : l) {
            volume += pack.getVolume();
        }
        // invalid input
        if(volume <= 0) {
            return null;
        }
        // calculate the minimal number of bins needed.
        int m = 1;
        if (this.vol > 0) {
            m = (int) Math.ceil((double) volume / this.vol);
        }
        // set the first solution as best.
        Solution best = this.find(new BFD(new RSMC()),
                new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.b.get(0)), 5), this.constraint),
                this.copyBins(this.b),
                l);
        // check if optimal.
        if(best.isOptimal(m)) {
            return best;
        }
        // iterate through the optimal lambda values and try to solve for each sorting and placing methods.
        for (int i = 5; i <= 15; i++) {
            // best fit decreasing with clustered area height sort
            Solution t = this.find(new BFD(new RSMC()),
                    new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l);
            // check if optimal
            if (t.isOptimal(m)) {
                return t;
            }
            // check if better than current best solution.
            if (t.isBetter(best)) {
                best = t;
            }
        }
        for (int i : OPT1) {
            // best fit decreasing with clustered height area sort
            Solution t = (this.find(new BFD(new RSMC()),
                    new ClusteredDeliverySorter(new ClusteredHeightAreaSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l));
            // check if optimal
            if (t.isOptimal(m)) {
                return t;
            }
            // check if better than current best solution.
            if (t.isBetter(best)) {
                best = t;
            }
        }
        for (int i : OPT2) {
            // first fit decreasing with clustered height area sort
            Solution t = (this.find(new FFD(),
                    new ClusteredDeliverySorter(new ClusteredHeightAreaSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l));
            // check if optimal
            if (t.isOptimal(m)) {
                return t;
            }
            // check if better than current best solution.
            if (t.isBetter(best)) {
                best = t;
            }
        }

        for (int i = 5; i <= 25; i++) {
            // first fit decreasing with clustered area height sort
            Solution t = (this.find(new FFD(),
                    new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l));
            // check if optimal
            if (t.isOptimal(m)) {
                return t;
            }
            // check if better than current best solution.
            if (t.isBetter(best)) {
                best = t;
            }
        }
        return best;
    }

    /**
     * copy a list of bins.
     * @param bins list of bins
     * @return list of bins.
     */
    private List<Bin> copyBins(List<Bin> bins) {
        LinkedList<Bin> copy = new LinkedList<>();
        for(Bin b1 : bins) {
            copy.add(new Bin(b1));
        }
        return copy;
    }

    /**
     * find a solution with the given sorting and placing methods.
     * @param placer a placing heuristic
     * @param sorter package sorter
     * @param b bins
     * @param packages packages
     * @return Solution.
     */
    private Solution find(Placer placer, Sorter sorter, List<Bin> b, List<Package> packages) {
        Packer ar = new Packer(placer, sorter, b, packages);
        ar.pack();
        return ar.getSolution();
    }

}
