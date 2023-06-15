/**
 * Clustered height-area sorter
 */
public class ClusteredHeightAreaSorter extends ClusteredSorter {
    public ClusteredHeightAreaSorter(Bin bin, int lambda) {
        super(bin.getHeight(), new BaseAreaSorter(), lambda);
    }
    @Override
    protected int getValue(Package p) {
        return p.getHeight();
    }
}
