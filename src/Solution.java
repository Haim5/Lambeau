import java.util.List;

public class Solution {

    private List<Bin> bins;
    private List<Package> cannotPlace;

    public Solution(List<Bin> bins, List<Package> cannotPlace) {
        this.bins = bins;
        this.cannotPlace = cannotPlace;
    }

    public boolean isBetter(Solution other) {
        if(this.unpacked() == other.unpacked()) {
            return this.numOfBins() < other.numOfBins();
        }
        return this.unpacked() < other.unpacked();
    }

    public boolean isOptimal(int min) {
        return (this.unpacked() == 0 && this.numOfBins() == min);
    }

    public List<Package> getUnpacked() {
        return this.cannotPlace;
    }

    public List<Bin> getBins() {
        return this.bins;
    }

    public int numOfBins() {
        return this.bins.size();
    }

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
            sb.append("Bin " + count++ + ":\n");
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
