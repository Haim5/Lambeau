import java.util.List;

/**
 * Sorter interface, used for sorting packages
 */
public interface Sorter {
    /**
     * Sort a list of packages
     * @param l the list we want to sort
     * @return Sorted list of packages
     */
    List<Package> sort(List<Package> l);

    /**
     * Sort a list of packages, select descending or ascending order.
     * @param l the list we need to sort
     * @param isDescending variable that decides if we should sort it in descending order.
     * @return  a sorted list of packages.
     */
    List<Package> sort(List<Package> l, boolean isDescending);
}
