import java.util.List;
import java.util.Set;

/**
 * best fit decreasing placing heuristic.
 */
public class BFD implements Placer {

    // merit calculator
    private final MeritCalculator mc;

    /**
     * Constructor
     * @param mc the merit calculator we need to use.
     */
    public BFD(MeritCalculator mc) {
        this.mc = mc;
    }

    @Override
    public boolean put(List<Bin> bins, Package p) {
        int bestScore = this.mc.getWorst();
        Orientation bestO = null;
        Bin bestBin = null;
        EP bestEP = null;
        // check possible placing score for each bin.
        for (Bin b : bins) {
            // check if the package can fit in the bin.
            if (b.canItFit(p)) {
                Set<Orientation> orientations = p.getOrientations();
                List<EP> eps = b.getPossibleEP(p);
                EPSorter epsorter = new EPSorter();
                eps = epsorter.sort(eps);
                // check score for each extreme point
                for(EP ep : eps) {
                    // check score for each orientation
                    for(Orientation o : orientations) {
                        // check if the bin can be placed in ep at this orientation.
                        if (ep.canPut(o)) {
                            // calculate score
                            int score = this.mc.calc(o, ep);
                            // check if it is the best score
                            if (this.mc.isBetter(score, bestScore)) {
                                bestScore = score;
                                bestBin = b;
                                bestO = o;
                                bestEP = ep;
                            }
                        }
                    }
                }
            }
        }
        // found a bin.
        if (bestBin != null) {
            // put it.
            bestBin.put(p, bestEP, bestO);
            return true;
        }
        // no possible location found.
        return false;
    }
}
