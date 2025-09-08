import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Packer class.
 */
public class Packer {
    private final List<Bin> bins;
    private final List<Package> cannotPlace = new LinkedList<>();
    private List<Package> packages;
    private final Sorter sorter;
    private Bin binModel = null;
    private final Placer placer;
    private Solution solution;


    /**
     * Constructor
     * @param config packer configuration.
     */
    public Packer(PackerConfiguration config) {
        this.placer = config.getPlacer();
        this.sorter = config.getSorter();
        this.bins = config.getBins();
        this.packages = config.getPackages();

        List<ContainerTableRow> containerRows = config.getContainerTableRows();
        // A bin model is used if there is exactly one type of container, and its quantity is infinite.
        if (containerRows != null && containerRows.size() == 1 && containerRows.get(0).getQuantity() == Double.POSITIVE_INFINITY) {
            this.binModel = new Bin(this.bins.get(0));
        }
    }


    /**
     * pack the packages inside the bins.
     */
    public void pack() {
        this.packages = this.sorter.sort(this.packages);
        for(Package p : this.packages) {
            this.place(p);
        }
        this.solution = new Solution(this.bins, this.cannotPlace);
    }

    /**
     * place a package inside the bin.
     * @param p package to pack.
     */
    private void place(Package p) {
        // check if it can fit in a bin.
        if(this.binModel != null && !this.binModel.canItFit(p)) {
            this.cannotPlace.add(p);
        } else {
            // check if the placer can find a place for the package
            if(!this.placer.put(this.bins, p)) {
                if (this.binModel != null) {
                    // if we use a bin model - make a new bin of the same type.
                    Bin b1 = new Bin(this.binModel);
                    ArrayList<Bin> added = new ArrayList<>();
                    added.add(b1);
                    this.placer.put(added, p);
                    this.bins.add(b1);
                } else {
                    this.cannotPlace.add(p);
                }
            }
        }

    }

    /**
     * get the packing solution.
     * @return Solution
     */
    public Solution getSolution() {
        return this.solution;
    }
}
