import java.util.List;
import java.util.Set;

public class FFD implements Placer {
    @Override
    public boolean put(List<Bin> bins, Package p) {
        for (Bin b : bins) {
            if (b.canItFit(p)) {
                Set<Orientation> orientations = p.getOrientations();
                List<EP> eps = b.getPossibleEP(p);
                EPSorter epsorter = new EPSorter();
                eps = epsorter.sort(eps);
                for(EP ep : eps) {
                    for(Orientation o : orientations) {
                        if (ep.canPut(o)) {
                            b.put(p, ep, o);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
