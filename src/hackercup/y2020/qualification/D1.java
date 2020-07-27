package hackercup.y2020.qualification;

import java.io.*;
import java.util.Arrays;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class D1 {
    private static long NOT_AVAILABLE = 1_000_000L * Integer.MAX_VALUE;

    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\running_on_fumes_chapter_1_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\d1_official_out.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] line = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(line[0]), M = Integer.parseInt(line[1]);
                long[] cost = new long[N];
                for (int c = 0; c < N; c++) {
                    int v = Integer.parseInt(br.readLine().trim());
                    cost[c] = v == 0 ? NOT_AVAILABLE : v;
                }

                long solution = solve(N, M, cost);
                printWriter.printf("Case #%d: %d\n", i, solution);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public static long solve(int n, int m, long[] cost) {
        if (m >= n - 1) return 0;
        SegmentTree st = new SegmentTree(cost);
        long ret = 0;
        for (int i = 1; i < cost.length; i++) {
            if (i > m) {
                long c = st.query(i - m, i);
                if (c >= NOT_AVAILABLE) return -1;
                if (i == cost.length - 1) ret = c;
                st.update(i, c + cost[i]);
            }
        }
        return ret;
    }

    private static class SegmentTree {
        int size;
        long[] st;

        SegmentTree(long[] array) {
            this.size = array.length;
            int n = 1;
            while (n < size) n <<= 1;
            this.st = new long[n << 1];
            Arrays.fill(this.st, NOT_AVAILABLE);
            buildTree(array, 0, 0, size);
        }

        private void buildTree(long[] array, int x, int lx, int rx) {
            if (rx - lx == 1) {
                if (lx < array.length) st[x] = array[lx];
                return;
            }
            int mid = lx + (rx - lx) / 2;
            buildTree(array, 2 * x + 1, lx, mid);
            buildTree(array, 2 * x + 2, mid, rx);
            st[x] = Math.min(st[2 * x + 1], st[2 * x + 2]);
        }

        private long query(int from, int to) {
            return query(0, from, to, 0, size);
        }

        public long query(int x, int from, int to, int lx, int rx) {
            if (from <= lx && rx <= to) return st[x];
            else if (from >= rx || to <= lx) return NOT_AVAILABLE;
            int mid = (lx + rx) / 2;
            long min = NOT_AVAILABLE;
            min = Math.min(min, query(2 * x + 1, from, to, lx, mid));
            min = Math.min(min, query(2 * x + 2, from, to, mid, rx));
            return min;
        }

        public void update(int i, long v) {
            update(0, i, 0, size, v);
        }

        private void update(int x, int i , int lx, int rx, long v) {
            if (rx - lx == 1) {st[x] = v; return;}
            int mid = lx + (rx - lx) / 2;
            if (i < mid) update(2 * x + 1, i, lx, mid, v);
            else update(2 * x + 2, i, mid, rx, v);
            st[x] = Math.min(st[2 * x + 1], st[2 * x + 2]);
        }
    }
}

