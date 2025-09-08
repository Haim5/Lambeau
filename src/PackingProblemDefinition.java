import java.util.List;
import java.util.stream.Collectors;

/**
 * represent the problem
 */
public class PackingProblemDefinition {
    private final List<Bin> bins;
    private final List<Package> packs;
    private List<ContainerTableRow> containerTableRows; // Can be null
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
        this.containerTableRows = null;
    }

    /**
     * Constructor
     * @param bins bins
     * @param packs packages
     * @param constraint constraint level
     * @param containerTableRows original container definitions from GUI
     */
    public PackingProblemDefinition(List<Bin> bins, List<Package> packs, int constraint, List<TableRow> containerTableRows)  {
        this.bins = bins;
        this.packs = packs;
        this.constraint = constraint;
        this.containerTableRows = containerTableRows.stream().map(row -> (ContainerTableRow) row).collect(Collectors.toList());
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

    /**
     * get the container table rows.
     * @return list of container table rows.
     */
    public List<ContainerTableRow> getContainerTableRows() {
        return this.containerTableRows;
    }
}
