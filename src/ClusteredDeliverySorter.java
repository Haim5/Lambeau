import java.util.*;

public class ClusteredDeliverySorter extends ClusteredSorter {
    private final int lambda;
    private final ClusteredSorter sorter;
    public ClusteredDeliverySorter(ClusteredSorter cs, int lambda) {
        super(1, cs, lambda);
        this.sorter = cs;
        this.lambda = lambda;
    }

    @Override
    protected int getValue(Package p) {
        return p.getDeliveryGroup();
    }

    public List<Package> sort(List<Package> l) {
        List<Package> ans;
        if (this.lambda == 0) {
            return this.sorter.sort(l);
        }
        return super.sort(l);
    }

    @Override
    protected int getIndex(int val) {
        if (this.lambda == 0) {
            return 0;
        }
        return (int)(Math.ceil((double)(this.lambda * val) / 10.0));
    }
}
