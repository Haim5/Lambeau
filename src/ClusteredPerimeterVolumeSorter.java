public class ClusteredPerimeterVolumeSorter extends ClusteredSorter {
    public ClusteredPerimeterVolumeSorter(Bin bin, int lambda) {
        super(bin.getHeight() + bin.getDepth() + bin.getWidth(), new VolumeSorter(), lambda);
    }
    @Override
    protected int getValue(Package p) {
        return p.getWidth() + p.getDepth() + p.getHeight();
    }
}
