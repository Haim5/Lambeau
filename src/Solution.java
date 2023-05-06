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
}
