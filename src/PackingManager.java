import java.util.*;

public class PackingManager {
    private static final List<Integer> OPT1 = new ArrayList<>(Arrays.asList(
            21, 22, 23, 24, 50, 51, 52, 53, 54, 55, 56, 57));
    private static final List<Integer> OPT2 = new ArrayList<>(Arrays.asList(
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60));
    private final List<Bin> b;
    private final List<Package> l;
    private final int constraint;
    private int vol = 0;

    public PackingManager(List<Bin> b, List<Package> p, int constraint) {
        this.b = b;
        this.l = p;
        this.constraint = constraint;

        for (Bin bin : b) {
            this.vol += bin.getVolume();
        }
    }

    public Solution findSolution() {
        int volume = 0;
        for(Package pack : l) {
            volume += pack.getVolume();
        }
        if(volume <= 0) {
            return null;
        }
        int m = 1;
        if (this.vol > 0) {
            m = (int) Math.ceil((double) volume / this.vol);
        }
        Solution best = this.find(new BFD(new RSMC()),
                new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.b.get(0)), 5), this.constraint),
                this.copyBins(this.b),
                l);
        if(best.isOptimal(m)) {
            return best;
        }

        for(int i = 6; i <= 15; i++) {
            Solution t = this.find(new BFD(new RSMC()),
                    new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l);
            if(t.isOptimal(m)) {
                return t;
            }
            if(t.isBetter(best)) {
                best = t;
            }
        }

        for(int i : OPT1) {
            Solution t = (this.find(new BFD(new RSMC()),
                    new ClusteredDeliverySorter(new ClusteredHeightAreaSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l));
            if (t.isOptimal(m)) {
                return t;
            }
            if(t.isBetter(best)) {
                best = t;
            }
        }

        for(int i : OPT2) {
            Solution t = (this.find(new FFD(),
                    new ClusteredDeliverySorter(new ClusteredHeightAreaSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l));
            if (t.isOptimal(m)) {
                return t;
            }
            if(t.isBetter(best)) {
                best = t;
            }
        }

        for(int i = 5; i <= 25; i++) {
            Solution t = (this.find(new FFD(),
                    new ClusteredDeliverySorter(new ClusteredAreaHeightSorter(new Bin(this.b.get(0)), i), this.constraint),
                    this.copyBins(this.b),
                    l));
            if(t.isOptimal(m)) {
                return t;
            }
            if(t.isBetter(best)) {
                best = t;
            }
        }
        return best;
    }


    private List<Bin> copyBins(List<Bin> bins) {
        LinkedList<Bin> copy = new LinkedList<>();
        for(Bin b1 : bins) {
            copy.add(new Bin(b1));
        }
        return copy;
    }

    private Solution find(Placer placer, Sorter sorter, List<Bin> b, List<Package> packages) {
        Packer ar = new Packer(placer, sorter, b, packages);
        ar.pack();
        return ar.getSolution();
    }

}
