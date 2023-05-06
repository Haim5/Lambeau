import java.util.List;
import java.util.Set;

public class BFD implements Placer {

    private MeritCalculator mc;

    public BFD(MeritCalculator mc) {
        this.mc = mc;
    }

    @Override
    public boolean put(List<Bin> bins, Package p) {
        int bestScore = this.mc.getWorst();
        Orientation bestO = null;
        Bin bestBin = null;
        EP bestEP = null;
        for (Bin b : bins) {
            if (b.canItFit(p)) {
                Set<Orientation> orientations = p.getOrientations();
                List<EP> eps = b.getPossibleEP(p);
                EPSorter epsorter = new EPSorter();
                eps = epsorter.sort(eps);
                for(EP ep : eps) {
                    for(Orientation o : orientations) {
                        if (ep.canPut(o)) {
                            int score = this.mc.calc(p, b);
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
        if (bestBin != null) {
            bestBin.put(p, bestEP, bestO);
            return true;
        }
        return false;
    }
}
