import java.util.List;

/**
 * Placer interface.
 */
public interface Placer {

    /**
     * try to put package in one of the bins.
     * @param bins the possible bins.
     * @param p the package.
     * @return if it was placed - true, else - false
     */
    boolean put(List<Bin> bins, Package p);
}
