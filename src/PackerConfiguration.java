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
    private List<ContainerTableRow> containerTableRows; // Can be null

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
        this.containerTableRows = null;
    }

    /**
     * Constructor
     * @param placer placer
     * @param sorter sorter
     * @param ppd problem definition.
     */
    public PackerConfiguration(Placer placer, Sorter sorter, PackingProblemDefinition ppd) {
        this(placer, sorter, ppd.getBins(), ppd.getPackages());
        if (ppd.getContainerTableRows() != null) {
            this.containerTableRows = new LinkedList<>(ppd.getContainerTableRows());
        }
    }

    /**
     * get the container table rows.
     * @return list of container table rows.
     */
    public List<ContainerTableRow> getContainerTableRows() {
        return this.containerTableRows;
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
