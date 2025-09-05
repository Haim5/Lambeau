/**
 * Clustered delivery sorter - clustered sorting by the delivery group of the package.
 */
public class ClusteredDeliverySorter extends ClusteredSorter {
    private final int lambda;

    /**
     * Constructor
     * @param cs a clustered sorter
     * @param lambda constraint level.
     */
    public ClusteredDeliverySorter(ClusteredSorter cs, int lambda) {
        super(1, cs, lambda);
        this.lambda = lambda;
    }

    @Override
    protected int getValue(Package p) {
        return p.getDeliveryGroup();
    }

    @Override
    protected int getIndex(int val) {
        if (this.lambda == 0) {
            return 0;
        }
        if (this.lambda >= 10) {
            return val;
        }
        double groupSize = 10.0 / this.lambda;
        return (int) (val / groupSize);
    }
}
