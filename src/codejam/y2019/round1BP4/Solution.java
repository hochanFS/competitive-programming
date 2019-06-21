package codejam.y2019.round1BP4;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        C solver = new C();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class C {
        SparseTableMax maxC;
        SparseTableMax maxD;
        long ans;
        int k;

        void go(int l, int r) {
            if (l > r) {
                return;
            }

            int m = maxC.get(l, r);
            long value = maxC.value[m];
            int left1 = getLast(maxD, value + k + 1, l, m);
            int left2 = getLast(maxD, value - k, l, m);
            int right1 = getFirst(maxD, value + k + 1, m, r);
            int right2 = getFirst(maxD, value - k, m, r);
            System.out.println("m" + m + " left1 = " + left1 + ", left2 = " + left2);

            ans += (long) (m - left1) * (right1 - m) - (long) (m - left2) * (right2 - m);

            go(l, m - 1);
            go(m + 1, r);
        }

        private int getLast(SparseTableMax maxD, long value, int l, int m) {
            int cur = m + 1;
            for (int i = maxD.table.length - 1; i >= 0; i--) {
                int pos = Math.max(0, cur - (1 << i));
                if (m == 12)
                    System.out.println(cur + " " + (1 << i) + " " + pos + " " + value + " " + l);
                if (maxD.value[maxD.table[i][pos]] < value) {
                    cur = pos;
                }
            }
            return Math.max(l, cur) - 1;
        }

        private int getFirst(SparseTableMax maxD, long value, int m, int r) {
            int cur = m - 1;
            for (int i = maxD.table.length - 1; i >= 0; i--) {
                int pos = cur + 1;
                if (pos < maxD.value.length && maxD.value[maxD.table[i][pos]] < value) {
                    cur = Math.min(cur + (1 << i), maxD.value.length - 1);
                }
            }
            return Math.min(r, cur) + 1;
        }

        public void solve(int testNumber, FastScanner in, PrintWriter out) {
            out.printf("Case #%d: ", testNumber);
            int n = in.nextInt();
            k = in.nextInt();
            long[] c = in.nextLongArray(n);
            long[] d = in.nextLongArray(n);
            maxC = new SparseTableMax(c);
            maxD = new SparseTableMax(d);
            ans = 0;
            go(0, n - 1);
            out.println(ans);
        }

    }

    static class SparseTableMax {
        public long[] value;
        public int[][] table;

        public SparseTableMax(long[] a) {
            int log = 1;
            while ((1 << log) <= a.length) {
                log++;
            }
            value = a.clone();
            table = new int[log][a.length];
            for (int l = 0; l < log; l++) {
                for (int i = 0; i < a.length; i++) {
                    if (l == 0) {
                        table[l][i] = i;
                    } else {
                        table[l][i] = table[l - 1][i];
                        if (i + (1 << (l - 1)) < a.length) {
                            table[l][i] = larger(table[l][i], table[l - 1][i + (1 << (l - 1))]);
                        }
                    }
                }
            }
        }

        public int get(int l, int r) {
            // [l, r] both inclusive
            int dist = r - l;
            int log = 31 - Integer.numberOfLeadingZeros(dist);
            if (log < 0) {
                log = 0;
            }
            return larger(table[log][l], table[log][r - (1 << log) + 1]);
        }

        private int larger(int i, int j) {
            if (i == -1 || j == -1) {
                return i + j + 1;
            }
            return value[i] > value[j] ? i : j;
        }

    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        public FastScanner(String fileName) {
            try {
                br = new BufferedReader(new FileReader(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                String line = null;
                try {
                    line = br.readLine();
                } catch (IOException e) {
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public long[] nextLongArray(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

    }
}

