
/**
 * PackingManager class - used to generate a packing solution.
 */
public class PackingProblemSolver3D {

    private final PackerConfigurationGenerator generator;
    private final SolutionEstimator estimator;

    /**
     * Constructor
     * @param ppd problem definition.
     */
    public PackingProblemSolver3D(PackingProblemDefinition ppd) {
        this.generator = new PackerConfigurationGenerator(ppd);
        this.estimator = new SolutionEstimator(ppd);
    }

    /**
     * find the best solution to the packing problem.
     * @return Solution.
     */
    public Solution findBestSolution() {
        PackerConfiguration config = this.generator.generate();
        Packer ar = new Packer(config);
        ar.pack();
        Solution best = ar.getSolution();
        if(this.estimator.isOptimal(best)) {
            return best;
        }
        config = this.generator.generate();
        while(config != null) {

            Packer packer = new Packer(config);
            packer.pack();
            Solution tempSolution = packer.getSolution();
            if(this.estimator.isBetter(tempSolution, best)) {
                best = tempSolution;
                if(this.estimator.isOptimal(best)) {
                    return best;
                }
            }
            config = this.generator.generate();
        }
        return best;
    }

}
