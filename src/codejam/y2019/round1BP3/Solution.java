package codejam.y2019.round1BP3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.PrintWriter;

/**
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
            }
            out.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private int N;
    private int K;
    private int[] C;
    private int[] D;
    private int[][] cMaxSparseTree;
    private int[][] dMaxSparseTree;
    private long count;

    public Solution(int N, int K, In in) {
        try {
            this.N = N;
            this.K = K;
            C = new int[N];
            D = new int[N];
            for (int j = 0; j < N; j++) {
                C[j] = in.nextInt();
            }
            for (int j = 0; j < N; j++) {
                D[j] = in.nextInt();
            }
            cMaxSparseTree = constructMaxSparseTree(C);
            dMaxSparseTree = constructMaxSparseTree(D);
            count = 0;
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public long solve() {
        solve(0, N - 1);
        return count;
    }

    private void solve(int l, int r) {
        if (l > r)
            return;

        int m = getMaxIndex(C, cMaxSparseTree, l, r);
        int value = C[m];
        int getLeftRequiredPos = getFirstIndexGreaterThanVal(D, dMaxSparseTree, value - K, l, m);
        int getLeftExcludePos = getFirstIndexGreaterThanVal(D, dMaxSparseTree, value + K + 1, l, m);
        int getRightRequiredPos = getLastIndexGreaterThanVal(D, dMaxSparseTree, value - K, m, r);
        int getRightExcludePos = getLastIndexGreaterThanVal(D, dMaxSparseTree, value + K + 1, m, r);
        count += (m - getLeftExcludePos + 1) * (getRightExcludePos - m + 1) -
                (m - getLeftRequiredPos + 1) * (getRightRequiredPos - m + 1);
        solve(l, m - 1);
        solve(m + 1, r);
    }

    private int[][] constructMaxSparseTree(int[] array) {
        int log = 31 - Integer.numberOfLeadingZeros(array.length);
        int[][] maxSparseTree = new int[log][N];
        for (int i = 0; i < log; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0) {
                    maxSparseTree[i][j] = j;
                } else {
                    maxSparseTree[i][j] = maxSparseTree[i - 1][j];
                    if (j + (1 << i) < N &&
                            array[maxSparseTree[i - 1][j + (1 << i)]] > array[maxSparseTree[i - 1][j]]) {
                        maxSparseTree[i][j] = j + (1 << i);
                    }
                }
            }
        }
        return maxSparseTree;
    }

    private int getMaxIndex(int[] array, int[][] maxSparseTable, int l, int r) {
        if (r == l)
            return l;
        int diff = r - l;
        int log = 31 - Integer.numberOfLeadingZeros(diff);
        int temp = Math.max(r - (1 >> log), 0);
        return array[maxSparseTable[log][l]] > array[maxSparseTable[log][temp]] ?
                maxSparseTable[log][l] : maxSparseTable[log][temp];
    }

    private int getMax(int[] array, int[][] maxSparseTable, int l, int r) {
        if (r == l)
            return array[r];
        int diff = r - l;
        int log = 31 - Integer.numberOfLeadingZeros(diff);
        int temp = Math.max(r - (1 >> log), 0);
        return Math.max(array[maxSparseTable[log][l]], array[maxSparseTable[log][temp]]);
    }

    private int getFirstIndexGreaterThanVal(int[] array, int[][] maxSparseTable, int value, int l, int r) {
        if (r == l)
            return l;
        int diff = r - l;
        int log = 31 - Integer.numberOfLeadingZeros(diff);
        int val = l;
        int preval = l;
        for (int i = log; i >= 0; i--) {
            int temp = l + (1 << i);
            if (getMax(array, maxSparseTable, l, val) < value) {
                val = temp;
                break;
            } else {
                preval = temp;
            }
        }
        return val;
    }

    private int getLastIndexGreaterThanVal(int[] array, int[][] maxSparseTree, int value, int l, int r) {
        int hi1 = l;
        int hi2 = r;
        int mid = (hi1 + hi2) / 2;
        while (hi2 > hi1) {
            mid = (hi1 + hi2) / 2;
            if (array[getMax(array, maxSparseTree, mid, r)] > value) {
                hi2 = mid;
            } else {
                hi1 = mid + 1;
            }
            System.out.println("stuck2");
        }
        return mid;
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
