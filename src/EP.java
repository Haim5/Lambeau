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

        int p_x = this.p.getX();
        int p_y = this.p.getY();
        int p_z = this.p.getZ();

        int pack_x = where.getX();
        int pack_y = where.getY();
        int pack_z = where.getZ();

        int pack_w = k.getW();
        int pack_d = k.getD();
        int pack_h = k.getH();

        // Update rsx
        if (p_x < pack_x + pack_w && p_y >= pack_y && p_y < pack_y + pack_d && p_z >= pack_z && p_z < pack_z + pack_h) {
            this.rsx = Math.min(this.rsx, pack_x - p_x);
        }

        // Update rsy
        if (p_y < pack_y + pack_d && p_x >= pack_x && p_x < pack_x + pack_w && p_z >= pack_z && p_z < pack_z + pack_h) {
            this.rsy = Math.min(this.rsy, pack_y - p_y);
        }

        // Update rsz
        if (p_z < pack_z + pack_h && p_x >= pack_x && p_x < pack_x + pack_w && p_y >= pack_y && p_y < pack_y + pack_d) {
            this.rsz = Math.min(this.rsz, pack_z - p_z);
        }
    }
}
