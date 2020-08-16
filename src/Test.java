import java.util.TreeMap;

public class Test {
    public static void main(String[] args) {
        TreeMap<Long, Long> tm = new TreeMap<>();
        tm.put(1L , 2L);
        tm.put(2L, 3L);
        tm.put(5L, 4L);
        tm.put(6L, 2L);
        tm.put(8L, 1L);
        System.out.println(tm.subMap(2L, 6L));
    }
}
