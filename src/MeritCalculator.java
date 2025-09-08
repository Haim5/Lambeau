/**
 * Merit Calculator.
 */
public abstract class MeritCalculator {

    private int worst = 0;

    /**
     * calculate the merit score of placing p in b.
     * @param o orientation
     * @param ep extreme point
     * @return int - score.
     */
    public abstract int calc(Orientation o, EP ep);

    /**
     * return which score is better.
     * @param score1 score
     * @param score2 score
     * @return if score1 is better than score2 - true, else - false.
     */
    public boolean isBetter(int score1, int score2) {
        return score1 >= score2;
    }

    /**
     * get the worst possible score.
     * @return int
     */
    public int getWorst() {
        return this.worst;
    }

    /**
     * set the worst possible score.
     * @param w worst possible score.
     */
    protected void setWorst(int w) {
        this.worst = w;
    }
}
