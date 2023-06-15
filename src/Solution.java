import java.util.List;

/**
 * Solution class - representing a packing order.
 */
public class Solution {

    // list of bins
    private final List<Bin> bins;
    // list of packages that cannot be packed.
    private final List<Package> cannotPlace;

    /**
     * Constructor.
     * @param bins list of bins.
     * @param cannotPlace list of packages that cannot be packed.
     */
    public Solution(List<Bin> bins, List<Package> cannotPlace) {
        this.bins = bins;
        this.cannotPlace = cannotPlace;
    }

    /**
     * check if this solution is better.
     * @param other another solution
     * @return true if this solution is better. ow - false.
     */
    public boolean isBetter(Solution other) {
        if(this.unpacked() == other.unpacked()) {
            // number of unpacked is tied. decide by the number of bins used.
            return this.numOfBins() < other.numOfBins();
        }
        // the better solution is the solution with less unpacked packages.
        return this.unpacked() < other.unpacked();
    }

    /**
     * is this solution an optimal solution.
     * @param min minimal number of bins needed for a valid solution.
     * @return true if all packages are packed and the the number of bins is minimal.
     */
    public boolean isOptimal(int min) {
        return (this.unpacked() == 0 && this.numOfBins() == min);
    }

    /**
     * get the unpacked packages
     * @return list of packages
     */
    public List<Package> getUnpacked() {
        return this.cannotPlace;
    }

    /**
     * get the bins.
     * @return list of bins
     */
    public List<Bin> getBins() {
        return this.bins;
    }

    /**
     * get the number of bins in the solution.
     * @return int
     */
    public int numOfBins() {
        return this.bins.size();
    }

    /**
     * get the number of unpacked packages in the solution.
     * @return int
     */
    public int unpacked() {
        return this.cannotPlace.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.unpacked() > 0) {
            sb.append("Unpacked Packages:\n");
            List<Package> un = this.getUnpacked();
            for(Package unpacked : un) {
                sb.append(unpacked);
            }
            sb.append("\n");
        }

        sb.append("Packed Packages:\n");
        List<Bin> bins = this.getBins();
        int count = 1;
        for(Bin curr : bins) {
            sb.append("Bin ");
            sb.append(count++);
            sb.append(":\n");
            sb.append(curr);
        }
        sb.append("Unpacked: ");
        sb.append(this.unpacked());
        sb.append(". Bins: ");
        sb.append(this.numOfBins());
        sb.append(".");
        return sb.toString();
    }
}
