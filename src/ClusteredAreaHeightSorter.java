/**
 * Clustered area-height sorter
 */
public class ClusteredAreaHeightSorter extends ClusteredSorter {
    public ClusteredAreaHeightSorter(Bin bin, int lambda) {
        super(bin.getBaseArea(), new HeightSorter(), lambda);
    }

    @Override
    protected int getValue(Package p) {
        return p.getBaseArea();
    }
}
