import java.util.List;
import java.util.Set;

/**
 * Residual Space Merit Calculator
 */
public class RSMC extends MeritCalculator {

    /**
     * Constructor
     */
    public RSMC() {
        this.setWorst(Integer.MAX_VALUE);
    }

    @Override
    public int calc(Package p, Bin b) {
        if (p.getVolume() > b.getLeftVolume()) {
            return this.getWorst();
        }
        Set<Orientation> orientations = p.getOrientations();
        List<EP> eps = b.getPossibleEP(p);
        EPSorter epsorter = new EPSorter();
        eps = epsorter.sort(eps);

        int min = Integer.MAX_VALUE;

        for(EP ep : eps) {
            for (Orientation o : orientations) {
                if (ep.canPut(o)) {
                    int g = calc(o, ep);
                    if (g < min) {
                        min = g;
                    }
                }
            }
        }
        return min;
    }

    /**
     * Help function for calc
     * @param o Orientation
     * @param ep Extreme Point
     * @return the score
     */
    private int calc(Orientation o, EP ep) {
        int rs1 = ep.getRsx() - o.getW();
        int rs2 = ep.getRsy() - o.getD();
        int rs3 = ep.getRsz() - o.getH();
        if(rs1 < 0 || rs2 < 0 || rs3 < 0) {
            return this.getWorst();
        }
        return rs1 + rs2 + rs3;
    }

    @Override
    public boolean isBetter(int score1, int score2) {
        return !super.isBetter(score1, score2);
    }
}
