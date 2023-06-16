import java.util.LinkedList;
import java.util.List;

/**
 * Packer Configuration.
 */
public class PackerConfiguration {
    private final Placer placer;
    private final Sorter sorter;
    private final List<Bin> bins;
    private final List<Package> packages;

    /**
     * Constructor
     * @param placer placer heuristic
     * @param sorter sorter
     * @param bins bins
     * @param packages packages
     */
    public PackerConfiguration(Placer placer, Sorter sorter, List<Bin> bins, List<Package> packages) {
        this.placer = placer;
        this.sorter = sorter;
        this.packages = packages;
        this.bins = this.copyBins(bins);
    }

    /**
     * Constructor
     * @param placer placer
     * @param sorter sorter
     * @param ppd problem definition.
     */
    public PackerConfiguration(Placer placer, Sorter sorter, PackingProblemDefinition ppd) {
        this(placer, sorter, ppd.getBins(), ppd.getPackages());
    }

    /**
     * get the placer heuristic
     * @return placer
     */
    public Placer getPlacer() {
        return this.placer;
    }

    /**
     * get the sorter
     * @return sorter
     */
    public Sorter getSorter() {
        return this.sorter;
    }

    /**
     * get the bins
     * @return list of bins
     */
    public List<Bin> getBins() {
        return this.bins;
    }

    /**
     * get the packages.
     * @return list of packages.
     */
    public List<Package> getPackages() {
        return this.packages;
    }

    /**
     * copy the bins.
     * @param bins bins
     * @return list of bins (copy of bins)
     */
    private List<Bin> copyBins(List<Bin> bins) {
        LinkedList<Bin> copy = new LinkedList<>();
        for(Bin b1 : bins) {
            copy.add(new Bin(b1));
        }
        return copy;
    }
}
