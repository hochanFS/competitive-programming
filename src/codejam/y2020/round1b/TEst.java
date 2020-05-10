package codejam.y2020.round1b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TEst {
    public static void main(String[] args) {
        int[] values = {1, 2, 2, 1, 3, 3};
        swap(values,1, 3);
        System.out.println(Arrays.toString(values));
    }

    private static void swap(int[] values, int l, int r) {
        List<Integer> left = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            left.add(values[i]);
        }
        if (r - l >= 0) System.arraycopy(values, l, values, 0, r - l);
        for (int i = r - l; i < r; i++) {
            values[i] = left.get(i - (r - l));
        }
    }
}
