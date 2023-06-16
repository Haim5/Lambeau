import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * configuration generator
 */
public class PackerConfigurationGenerator {
    // optimal lambda values
    private final List<Integer> OPT1 = new ArrayList<>(Arrays.asList(
            21, 22, 23, 24, 50, 51, 52, 53, 54, 55, 56, 57));
    private final List<Integer> OPT2 = new ArrayList<>(Arrays.asList(
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60));
    private final List<Integer> OPT3 = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
    private final List<Integer> OPT4 = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
            19, 20, 21, 22, 23, 24, 25));
    private final PackingProblemDefinition ppd;

    private final List<List<Integer>> optimalList = new ArrayList<>(Arrays.asList(OPT1, OPT2, OPT3, OPT4));
    private int optListIndex = 0;
    private int optValueIndex = 0;

    /**
     * Constructor
     * @param ppd problem definition
     */
    public PackerConfigurationGenerator(PackingProblemDefinition ppd) {
        this.ppd = ppd;
    }

    /**
     * Generate a configuration.
     * @return Configuration.
     */
    public PackerConfiguration generate() {
        // check if it is the last list of values.
        if (this.optListIndex >= this.optimalList.size()) {
            return null;
        }
        // get the optimal values list
        List<Integer> currOptimal = this.optimalList.get(this.optListIndex);
        // check if we already ran all the values of the list
        if (this.optValueIndex >= currOptimal.size()) {
            // update list
            this.optValueIndex = 0;
            this.optListIndex++;
            if (this.optListIndex >= this.optimalList.size()) {
                return null;
            }
            currOptimal = this.optimalList.get(this.optListIndex);
        }
        // get current lambda value
        int lambda = currOptimal.get(this.optValueIndex++);
        return switch (this.optListIndex) {
            case 0 -> new PackerConfiguration(new BFD(new RSMC()),
                    new ClusteredDeliverySorter(new ClusteredHeightAreaSorter(new Bin(this.ppd.getBins().get(0)), lambda), this.ppd.getConstraintLevel()),
                    this.ppd);
            case 1 -> new PackerConfiguration(new FFD(),
                    new ClusteredDeliverySorter(new ClusteredHeightAreaSorter(new Bin(this.ppd.getBins().get(0)), lambda), this.ppd.getConstraintLevel()),
                    this.ppd);
            case 2 -> new PackerConfiguration(new BFD(new RSMC()),
                    new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.ppd.getBins().get(0)), lambda), this.ppd.getConstraintLevel()),
                    this.ppd);
            case 3 -> new PackerConfiguration(new FFD(),
                    new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.ppd.getBins().get(0)), lambda), this.ppd.getConstraintLevel()),
                    this.ppd);
            default -> null;
        };
    }
}
