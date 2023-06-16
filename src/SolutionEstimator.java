import java.util.List;

/**
 * Solution estimator.
 */
public class SolutionEstimator {

    private final int minimalNumberOfBins;

    /**
     * Constructor
     * @param ppd problem definition
     */
    public SolutionEstimator(PackingProblemDefinition ppd) {
        // calculate the minimal possible bins in a valid solution by the volumes.
        int binsVolume = 0, packagesVolume = 0;
        List<Bin> bins = ppd.getBins();
        for(Bin b : bins) {
            binsVolume += b.getVolume();
        }

        List<Package> packages = ppd.getPackages();
        for(Package p : packages) {
            packagesVolume += p.getVolume();
        }

        int m = 1;
        if (binsVolume > 0) {
            m = (int) Math.ceil((double) packagesVolume / binsVolume);
        }
        this.minimalNumberOfBins = m;
    }

    /**
     * is the solution optimal
     * @param s solution.
     * @return true if optimal, else - false.
     */
    public boolean isOptimal(Solution s) {
        return (s != null && s.unpacked() == 0 && s.numOfBins() == this.minimalNumberOfBins);
    }

    /**
     * is s1 better than s2.
     * @param s1 solution
     * @param s2 solution
     * @return true if s1 is better than s2, else - false
     */
    public boolean isBetter(Solution s1, Solution s2) {
        if(s1.unpacked() == s2.unpacked()) {
            // number of unpacked is tied. decide by the number of bins used.
            return s1.numOfBins() < s2.numOfBins();
        }
        // the better solution is the solution with less unpacked packages.
        return s1.unpacked() < s2.unpacked();
    }
}
