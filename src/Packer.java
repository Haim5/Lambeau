import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Packer {
    private List<Bin> bins = new LinkedList<>();
    private List<Package> cannotPlace = new LinkedList<>();
    private List<Package> packages;
    private Sorter sorter;
    private Bin binModel = null;
    private Placer placer;
    private Solution solution;

    public Packer(Placer placer, Sorter sorter, Bin b, List<Package> packages) {
        this.bins.add(new Bin(b));
        this.packages = packages;
        this.sorter = sorter;
        this.placer = placer;
        this.binModel = b;
    }

    public Packer(Placer placer, Sorter sorter, List<Bin> bins, List<Package> packages) {
        this.bins = bins;
        this.packages = packages;
        this.sorter = sorter;
        this.placer = placer;
    }

    public void pack() {
        this.packages = this.sorter.sort(this.packages);
        for(Package p : this.packages) {
            this.place(p);
        }
        this.solution = new Solution(this.bins, this.cannotPlace);
    }

    public void add(Package p) {
        this.packages.add(p);
        this.place(p);
    }

    public void repack() {
        this.cannotPlace.clear();
        if(this.binModel != null) {
            this.bins.clear();
            this.bins.add(new Bin(this.binModel));
        } else {
            for(Bin b : this.bins) {
                b.clear();
            }
        }
        this.pack();
    }

    private void place(Package p) {
        if(this.binModel != null && !this.binModel.canItFit(p)) {
            this.cannotPlace.add(p);
        } else {
            if(!this.placer.put(this.bins, p)) {
                if (this.binModel != null) {
                    Bin b1 = new Bin(this.binModel);
                    ArrayList<Bin> added = new ArrayList<>();
                    added.add(b1);
                    this.placer.put(added, p);
                    this.bins.add(b1);
                } else {
                    this.cannotPlace.add(p);
                }
            }
        }

    }

    public Solution getSolution() {
        return this.solution;
    }
}
