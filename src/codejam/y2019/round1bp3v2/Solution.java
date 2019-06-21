package codejam.y2019.round1bp3v2;

import java.io.*;
import java.util.*;

public class Solution {
    void run() {
        int n = in.nextInt();
        int maxDiff = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            b[i] = in.nextInt();
        }
        long ans = n * (n + 1L) / 2;
        ans -= win(a, b, maxDiff);
        ans -= win(b, a, maxDiff);
        out.println(ans);
    }

    static long win(int[] a, int[] b, int diff) {
        long ans = 0;
        int n = a.length;
        SegmentsTree sa = new SegmentsTree(n);
        SegmentsTree sb = new SegmentsTree(n);
        for (int i = 0; i < n; i++) {
            sa.set(i, a[i]);
            sb.set(i, b[i]);
        }
        for (int i = 0; i < n; i++) {
            if (b[i] >= a[i] - diff) {
                continue;
            }
            int x, y;
            {
                int low = -1;
                int high = i;
                while (low + 1 < high) {
                    int mid = (low + high) / 2;
                    int aMax = sa.getMax(mid, i);
                    int bMax = sb.getMax(mid, i);
                    if (aMax < a[i] && bMax < a[i] - diff) {
                        high = mid;
                    } else {
                        low = mid;
                    }
                }
                x = high;
            }
            {
                int low = i + 1;
                int high = n + 1;
                while (low + 1 < high) {
                    int mid = (low + high) / 2;
                    int aMax = sa.getMax(i + 1, mid);
                    int bMax = sb.getMax(i + 1, mid);
                    if (aMax <= a[i] && bMax < a[i] - diff) {
                        low = mid;
                    } else {
                        high = mid;
                    }
                }
                y = low - 1;
            }
            ans += (i - x + 1L) * (y - i + 1L);
        }
        return ans;
    }

    static class SegmentsTree {
        int n;
        int[] max;
        int size = 1 << 17;

        public SegmentsTree(int n) {
            this.n = n;
            max = new int[2 * size];
        }

        void set(int index, int value) {
            int i = size + index;
            max[i] = value;
            while (i > 1) {
                i /= 2;
                max[i] = Math.max(max[2 * i], max[2 * i + 1]);
            }
        }

        int getMax(int from, int to) {
            from += size;
            to += size;
            int res = Integer.MIN_VALUE;
            while (from < to) {
                if (from % 2 == 1) {
                    res = Math.max(res, max[from]);
                    from++;
                }
                if (to % 2 == 1) {
                    to--;
                    res = Math.max(res, max[to]);
                }
                from /= 2;
                to /= 2;
            }
            return res;
        }
    }

    static long slow(int[] a, int[] b, int maxDiff) {
        int n = a.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int maxA = -1;
            int maxB = -1;
            for (int j = i; j < n; j++) {
                if (a[j] > maxA) maxA = a[j];
                if (b[j] > maxB) maxB = b[j];
                if (Math.abs(maxA - maxB) <= maxDiff) {
                    ans++;
                }
            }
        }
        return ans;
    }

    static MyScanner in;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        boolean stdStreams = true;
        String fileName = Solution.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
        String inputFileName = fileName + ".in";
        String outputFileName = fileName + ".out";

        Locale.setDefault(Locale.US);
        BufferedReader br;
        if (stdStreams) {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);
        } else {
            br = new BufferedReader(new FileReader(inputFileName));
            out = new PrintWriter(outputFileName);
        }
        in = new MyScanner(br);
        int tests = in.nextInt();
        for (int test = 0; test < tests; test++) {
            out.print("Case #" + (test + 1) + ": ");
            new Solution().run();
        }
        br.close();
        out.close();
    }

    static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        MyScanner(BufferedReader br) {
            this.br = br;
        }

        void findToken() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        String next() {
            findToken();
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}

