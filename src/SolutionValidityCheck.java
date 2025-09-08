import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Solution validity check class , used for testing the algorithm.
 */
public class SolutionValidityCheck {

    // list of cases to check.
    private final List<Case> cases = new LinkedList<>();

    /**
     * Check of the solution is valid.
     * @param solution the solution we need to check.
     * @return true if valid, else - false.
     */
    public boolean check(Solution solution) {
        List<Bin> bins = solution.getBins();
        // check for each bin if the solution is valid.
        for (Bin b : bins) {
            boolean[][][] arr = new boolean[b.getWidth()][b.getDepth()][b.getHeight()];
            Set<Location> loc = b.getLocations().keySet();
            // check for each package if there is an overlap
            for(Location l : loc) {
                Orientation o = l.getOrientation();
                Point p = l.getBackBottomLeftPoint();
                // check if the package in this orientation can fit the bin
                if(p.getX() + o.getW() > b.getWidth() || p.getY() + o.getD() > b.getDepth() || p.getZ() + o.getH() > b.getHeight()) {
                    return false;
                }
                // check for overlap with other packages
                for (int x = p.getX(); x < p.getX() + o.getW(); x++) {
                    for(int y = p.getY(); y < p.getY() + o.getD(); y++) {
                        for(int z = p.getZ(); z < p.getZ() + o.getH(); z++) {
                            if(arr[x][y][z]) {
                                // overlap found
                                return false;
                            }
                            // place is empty - mark as taken.
                            arr[x][y][z] = true;
                        }
                    }
                }
            }
        }
        // no overlaps found
        return true;
    }

    /**
     * Generate the cases for the test.
     */
    private void genCases() {
        // --- Basic Cases ---
        this.cases.add(new Case(new Bin(10, 10, 10), new Package(10, 10, 10, true, "perfect-fit"), 0));
        this.cases.add(new Case(new Bin(10, 10, 10), new Package(11, 10, 10, true, "impossible-fit"), 0));

        // --- Volumetric Mismatch Case ---
        // Total package volume (2000) > bin volume (1000), so some should be unpacked.
        Bin volumeTestBin = new Bin(10, 10, 10);
        LinkedList<Package> smallPackages = new LinkedList<>();
        for (int i = 0; i < 200; i++) {
            smallPackages.add(new Package(1, 2, 5, true, "small-" + i));
        }
        this.cases.add(new Case(volumeTestBin, smallPackages, 0));

        // --- Standard Cases with different constraints and flip options ---
        LinkedList<Bin> standardBins = new LinkedList<>();
        standardBins.add(new Bin("BinA", 10, 30, 10));
        standardBins.add(new Bin("BinB", 12, 13, 20));
        standardBins.add(new Bin("BinC", 15, 14, 13));

        int[] constraintLevels = {0, 5, 9};
        boolean[] flipOptions = {true, false};

        for (Bin b1 : standardBins) {
            for (int constraint : constraintLevels) {
                for (boolean canFlip : flipOptions) {
                    LinkedList<Package> packages = new LinkedList<>();
                    for (int i = 0; i < 10; i++) {
                        packages.add(new Package(2, 3, 5, canFlip, "p-" + i));
                    }
                    this.cases.add(new Case(new Bin(b1), packages, constraint));
                }
            }
        }

        // --- Edge Cases with different package shapes ---
        Bin edgeCaseBin = new Bin("EdgeCaseBin", 20, 20, 20);
        LinkedList<Package> edgePackages = new LinkedList<>();
        // Add "plates"
        for (int i = 0; i < 5; i++) {
            edgePackages.add(new Package(10, 1, 10, true, "plate-" + i));
        }
        // Add "sticks"
        for (int i = 0; i < 5; i++) {
            edgePackages.add(new Package(1, 18, 1, true, "stick-" + i));
        }
        // Add "cubes"
        for (int i = 0; i < 5; i++) {
            edgePackages.add(new Package(4, 4, 4, true, "cube-" + i));
        }
        this.cases.add(new Case(edgeCaseBin, edgePackages, 0));
    }

    /**
     * test the algorithm with the cases.
     */
    public void test() {
        // generate cases
        this.genCases();
        // counters
        int testCount = 0, validCount = 0, invalidCount = 0;
        // check validity for each case
        for(Case c : this.cases) {
            if(this.check(c.solve())) {
                // passed
                System.out.println("Test #" + testCount++ + ": Valid.");
                validCount++;
            } else {
                // failed
                System.out.println("Test #" + testCount++ + ": Invalid.");
                invalidCount++;
            }
        }
        System.out.println("\nPassed: " + validCount + ".");
        System.out.println("Failed: " + invalidCount + ".");
    }

    /**
     * Case class.
     */
    static class Case {
        private final List<Bin> bins;
        private final List<Package> packages;
        private final int constraint;

        /**
         * Constructor
         * @param bins bins.
         * @param packages packages.
         * @param constraint constraint level.
         */
        public Case(List<Bin> bins, List<Package> packages, int constraint) {
            this.bins = bins;
            this.packages = packages;
            this.constraint = constraint;
        }

        /**
         * Constructor with single bin.
         * @param bin a bin.
         * @param packages packages.
         * @param constraint constraint level.
         */
        public Case(Bin bin, List<Package> packages, int constraint) {
            List<Bin> bins = new LinkedList<>();
            bins.add(bin);
            this.bins = bins;
            this.packages = packages;
            this.constraint = constraint;
        }

        /**
         * Constructor with single package
         * @param bin a bin
         * @param pack a package
         * @param constraint constraint level.
         */
        public Case(Bin bin, Package pack, int constraint) {
            LinkedList<Bin> bins = new LinkedList<>();
            bins.add(bin);
            LinkedList<Package> packages = new LinkedList<>();
            packages.add(pack);
            this.bins = bins;
            this.packages = packages;
            this.constraint = constraint;
        }


        /**
         * Solving the case - generating a Solution.
         * @return Solution.
         */
        public Solution solve() {
            PackingProblemSolver3D pm = new PackingProblemSolver3D(new PackingProblemDefinition(this.bins, this.packages, this.constraint));
            return pm.findBestSolution();
        }
    }

    /**
     * Run the tests.
     * @param args command line args.
     */
    public static void main(String[] args) {
        SolutionValidityCheck svc = new SolutionValidityCheck();
        svc.test();
    }
}
