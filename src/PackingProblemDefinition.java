import java.util.List;

/**
 * represent the problem
 */
public class PackingProblemDefinition {
    private final List<Bin> bins;
    private final List<Package> packs;
    private final int constraint;

    /**
     * Constructor
     * @param bins bins
     * @param packs packages
     * @param constraint constraint level
     */
    public PackingProblemDefinition(List<Bin> bins, List<Package> packs, int constraint)  {
        this.bins = bins;
        this.packs = packs;
        this.constraint = constraint;
    }

    /**
     * get the bins
     * @return list of bins
     */
    public List<Bin> getBins() {
        return this.bins;
    }

    /**
     * get the packages.
     * @return list of packages
     */
    public List<Package> getPackages() {
        return this.packs;
    }

    /**
     * get the constraint level.
     * @return constraint level.
     */
    public int getConstraintLevel() {
        return this.constraint;
    }
}
