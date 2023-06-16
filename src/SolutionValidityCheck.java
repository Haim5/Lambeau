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
                for (int x = p.getX(); x < o.getW(); x++) {
                    for(int y = p.getY(); y < o.getD(); y++) {
                        for(int z = p.getZ(); z < o.getH(); z++) {
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
        // single package
        this.cases.add(new Case(new Bin(1,1,1), new Package(1,1,1, true, "p"), 0));
        // can't fit single package
        this.cases.add(new Case(new Bin(1,1,1), new Package(1,2,1, true, "p"), 0));

        LinkedList<Bin> b = new LinkedList<>();
        b.add(new Bin(1,30,10));
        b.add(new Bin(2,3,20));
        b.add(new Bin(2, 4, 3));

        for(Bin b1 : b) {
            LinkedList<Package> p1 = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                p1.add(new Package(2,3,5, true, String.valueOf(i)));
            }
            this.cases.add(new Case(b1, p1, 0));
        }

        for(Bin b1 : b) {
            LinkedList<Package> p1 = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                p1.add(new Package(2,3,5, false, String.valueOf(i)));
            }
            this.cases.add(new Case(b1, p1, 0));
        }

        // constraint level 5
        for(Bin b1 : b) {
            LinkedList<Package> p1 = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                p1.add(new Package(2,3,5, true, String.valueOf(i)));
            }
            this.cases.add(new Case(b1, p1, 5));
        }

        for(Bin b1 : b) {
            LinkedList<Package> p1 = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                p1.add(new Package(2,3,5, false, String.valueOf(i)));
            }
            this.cases.add(new Case(b1, p1, 5));
        }

        // constraint level - 9
        for(Bin b1 : b) {
            LinkedList<Package> p1 = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                p1.add(new Package(2,3,5, true, String.valueOf(i)));
            }
            this.cases.add(new Case(b1, p1, 9));
        }

        for(Bin b1 : b) {
            LinkedList<Package> p1 = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                p1.add(new Package(2,3,5, false, String.valueOf(i)));
            }
            this.cases.add(new Case(new Bin(b1), p1, 9));
        }

        LinkedList<Package> p2 = new LinkedList<>();
        for(int i = 0; i < 10; i++) {
            p2.add(new Package(4,3,5, false, String.valueOf(i)));
        }
        this.cases.add(new Case(b, p2, 0));

        LinkedList<Package> p3 = new LinkedList<>();
        for(int i = 0; i < 10; i++) {
            boolean flip = (i % 2 == 0);
            p3.add(new Package(2,3,5, flip, String.valueOf(i)));
        }
        this.cases.add(new Case(b, p3, 0));
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
    private static class Case {
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
