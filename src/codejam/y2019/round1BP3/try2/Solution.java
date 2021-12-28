package codejam.y2019.round1BP3.try2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.PrintWriter;

/**
 * This "Large" problem was one of the most challenging problems I encountered as of May 2019.
 * In short, the problem asks to find the number of the sublist [i ... j], such that
 * abs(max(c[i], c[i + 1], + ... + c[j]) - max(d[i] + d[i + 1] + ... + d[j])) <= k
 * The keys to solving this problem are the following:
 * 1. Dividing the problem as --
 * #{ALL SUBLIST'S} - #{max(c[i]...) > max(d[i]...) + k} - #{max(c[i]...) < MAX(d[i] ...) - k}
 * 2. Seeing the # of cases of containing the index i is (i - left + 1) * (right - i + 1)
 * 3. Implementing efficient algorithm for finding Maximum (Minimum) Range Query
 *
 * @author Hochan Lee
 */

public class Solution {
    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int K = in.nextInt();
                Solution sol = new Solution(N, K, in);
                long ans = sol.solve();
                out.printf("Case #%d: %d", i, ans);
                out.println();
            }
            out.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private int N;
    private int K;
    private long[] C;
    private long[] D;
    private SegmentTreeMax cMax;
    private SegmentTreeMax dMax;
    private long count;

    public Solution(int N, int K, In in) {
        try {
            this.N = N;
            this.K = K;
            C = new long[N];
            D = new long[N];
            for (int j = 0; j < N; j++) {
                C[j] = in.nextLong();
            }
            for (int j = 0; j < N; j++) {
                D[j] = in.nextLong();
            }
            cMax = new SegmentTreeMax(C);
            dMax = new SegmentTreeMax(D);

            count = (C.length + 1L) * C.length / 2L;

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public long solve() {
        return count - numTooLow(C, D, cMax, dMax) - numTooLow(D, C, dMax, cMax);
    }

    private long numTooLow(long[] a, long[] b, SegmentTreeMax aMax, SegmentTreeMax bMax) {
        long ans = 0;
        int n = a.length;
        for (int i = 0; i < n; i++) {
            if (b[i] >= a[i] - K)
                continue;
            long x, y;
            {
                int low = -1;
                int high = i;
                while (low + 1 < high) {
                    int mid = (low + high) / 2;
                    long maxA = aMax.getMax(mid, i);
                    long maxB = bMax.getMax(mid, i);
                    if (maxA < a[i] && maxB < a[i] - K) {
                        high = mid;
                    } else {
                        low = mid;
                    }
                }
                x = (long) high;
            }
            {
                int low = i + 1;
                int high = n + 1;
                while (low + 1 < high) {
                    int mid = (low + high) / 2;
                    long maxA = aMax.getMax(i + 1, mid);
                    long maxB = bMax.getMax(i + 1, mid);
                    if (maxA <= a[i] && maxB < a[i] - K) {
                        low = mid;
                    } else {
                        high = mid;
                    }
                }
                y = (long) low - 1;
            }
            ans += (i - x + 1L) * (y - i + 1L);
        }
        return ans;
    }


    /**
     * SegmentTree class
     * This class implements efficient Maximum Range Query algorithm.
     * I found a good source of information here:
     * https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
     * Memory consumption of O(N)
     * Search time of Max O(ln(N))
     *
     * @author Hochan Lee
     */

    public static class SegmentTreeMax {
        private final long[] segmentTree;
        private final int index;

        public SegmentTreeMax(long[] array) {
            this.index = 1 << (31 - Integer.numberOfLeadingZeros(array.length)) + 1;
            segmentTree = new long[index * 2];
            Arrays.fill(segmentTree, Long.MIN_VALUE);
            for (int i = 0; i < array.length; i++) {
                int j = index + i;
                segmentTree[j] = array[i];

                while (j > 0) {
                    j /= 2;
                    segmentTree[j] = Math.max(segmentTree[2 * j], segmentTree[2 * j + 1]);
                }
            }
        }

        @Override
        public String toString() {
            return Arrays.toString(segmentTree);
        }

        public long getMax(int from, int to) {
            from += index;
            to += index;
            long res = Long.MIN_VALUE;
            while (from < to) {
                if (from % 2 == 1) {
                    res = Math.max(res, segmentTree[from]);
                    from++;
                }
                if (to % 2 == 1) {
                    to--;
                    res = Math.max(res, segmentTree[to]);
                }
                from /= 2;
                to /= 2;
            }
            return res;
        }
    }

    //@
    static class In {
        BufferedReader br;
        StringTokenizer st;

        public In(InputStream i) {
            br = new BufferedReader(new InputStreamReader(i));
            st = new StringTokenizer("");
        }

        public String next() throws IOException {
            if (st.hasMoreTokens())
                return st.nextToken();
            else
                st = new StringTokenizer(br.readLine());
            return next();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        //#
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
        //$
    }
}