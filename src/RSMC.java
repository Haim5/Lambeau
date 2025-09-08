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
    public int calc(Orientation o, EP ep) {
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
