import java.util.List;

public interface Sorter {
    public List<Package> sort(List<Package> l);
    public List<Package> sort(List<Package> l, boolean isDescending);
}
