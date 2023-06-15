import java.util.HashSet;
import java.util.Set;

/**
 * EP - Extreme Point class.
 */
public class EP {

    private final Point p;
    private int rsx;
    private int rsy;
    private int rsz;

    /**
     * Constructor.
     * @param p Point.
     * @param rsx residual space in X axis.
     * @param rsy residual space in Y axis.
     * @param rsz residual space in Z axis.
     */
    public EP(Point p, int rsx, int rsy, int rsz) {
        this.p = p;
        this.rsx = rsx;
        this.rsy = rsy;
        this.rsz = rsz;
    }

    /**
     * get the point
     * @return Point.
     */
    public Point getPoint() {
        return this.p;
    }

    /**
     * can a package in Orientation o be places in this EP.
     * @param o Orientation.
     * @return if it can be placed - true, else - false.
     */
    public boolean canPut(Orientation o) {
        return o.getW() <= this.rsx && o.getH() <= this.rsz && o.getD() <= this.rsy;
    }

    /**
     * can package be placed at this EP.
     * @param p package
     * @return if it can be placed - true, else - false.
     */
    public boolean canPut(Package p) {
        Set<Orientation> orientations = p.getOrientations();
        // find a orientation that works.
        for(Orientation o : orientations) {
            if(canPut(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get residual space at X axis
     * @return int
     */
    public int getRsx(){
        return this.rsx;
    }

    /**
     * get residual space at Y axis
     * @return int
     */
    public int getRsy(){
        return this.rsy;
    }

    /**
     * get residual space at Z axis
     * @return int
     */
    public int getRsz(){
        return this.rsz;
    }

    /**
     * get the next EPs after placing a package on this one.
     * @param pack the package orientation/
     * @return a set of EP.
     */
    public Set<EP> getNextEP(Orientation pack) {
        Set<EP> l = new HashSet<>();
        if(this.rsx - pack.getW() > 0) {
            Point p1 = new Point(this.p.getX() + pack.getW(), this.p.getY(), this.p.getZ());
            l.add(new EP(p1, this.rsx - pack.getW(), this.rsy, this.rsz));
        }
        if(this.rsy - pack.getD() > 0) {
            Point p2 = new Point(this.p.getX(), this.p.getY() + pack.getD(), this.p.getZ());
            l.add(new EP(p2, this.rsx, this.rsy - pack.getD(), this.rsz));
        }
        if(this.rsz - pack.getH() > 0) {
            Point p3 = new Point(this.p.getX(), this.p.getY(), this.p.getZ() + pack.getH());
            l.add(new EP(p3, this.rsx, this.rsy, this.rsz - pack.getH()));
        }
        return l;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EP)) {
            return false;
        }
        EP ep = (EP)obj;
        return this.rsx == ep.rsx && this.rsy == ep.rsy && this.rsz == ep.rsz && this.p.equals(ep.p);
    }

    /**
     * update this EP by the newly used EP.
     * @param k the orientation.
     * @param place the EP on which it was placed.
     */
    public void update(Orientation k, EP place) {
        this.update(k, place.getPoint());
    }

    /**
     * update this EP.
     * @param k the orientation.
     * @param where the point in was placed on.
     */
    public void update(Orientation k, Point where) {
        int x1 = this.p.getX();
        int y1 = this.p.getY();
        int z1 = this.p.getZ();
        int x2 = where.getX();
        int y2 = where.getY();
        int z2 = where.getZ();
        if (z1 >= z2 && z1 < k.getH() + z2) {
            if (x1 <= x2 && y2 <= y1 && y1 <= y2 + k.getD()) {
                this.rsx = Math.min(this.rsx, x2 - x1);
            }
            if (y1 <= y2 && x2 <= x1 && x1 <= x2 + k.getW()) {
                this.rsy = Math.min(this.rsy, y2 - y1);
            }
        }
        if (z1 <= z2 && y2 <= y1 && y1 <= y2 + k.getD() && x2 <= x1 && x1 <= x2 + k.getW()) {
            this.rsz = Math.min(this.rsz, z2 - z1);
        }
    }
}
