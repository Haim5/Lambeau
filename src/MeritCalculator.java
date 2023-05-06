public abstract class MeritCalculator {

    private Orientation bestO = null;
    private EP bestEP = null;
    private int worst = 0;

    public abstract int calc(Package p, Bin b);

    public boolean isBetter(int score1, int score2) {
        return score1 >= score2;
    }

    public Orientation getBestOrientation() {
        return this.bestO;
    }

    public EP getBestEP() {
        return this.bestEP;
    }

    protected void setBestEP(EP e) {
        this.bestEP = e;
    }

    protected void setBestO(Orientation o) {
        this.bestO = o;
    }

    public int getWorst() {
        return this.worst;
    }

    protected void setWorst(int w) {
        this.worst = w;
    }
}
