package codejam.y2018.round1A2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Intends to solve a problem (#2) for Round 1A in Code Jam Competition.
 *
 * @author: hochanlee
 */

public class Solution {

    public static long MAX_POSSIBLE_VALUE = Long.MAX_VALUE / 2;

    public static class Casher {
        private long scanTime;
        private long packageTime;
        private long capacity;

        public Casher(long M, long S, long P) {
            scanTime = S;
            packageTime = P;
            capacity = M;
        }

        public long getMaxQuantityBoughtByTime(long time) {
            return Math.min(capacity, Math.max(time - packageTime, 0) / scanTime);
        }
    }

    public static boolean canBuyRequiredAmountByGivenTime(Casher[] cashers, int robotNum, long bitsNeeded, long time) {
        int N = cashers.length;
        long[] maxBitsPossiblyPurchased = new long[N];
        for (int i = 0; i < N; i++)
            maxBitsPossiblyPurchased[i] = cashers[i].getMaxQuantityBoughtByTime(time);
        Arrays.sort(maxBitsPossiblyPurchased);
        long bitsBoughtFromCasher = 0;
        for (int i = N - 1; i > N - 1 - robotNum; i--) {
            bitsBoughtFromCasher += maxBitsPossiblyPurchased[i];
        }
        return bitsBoughtFromCasher >= bitsNeeded;
    }

    public static void main(String[] args) {
        try {
            FastScanner in = new FastScanner(System.in);
            PrintWriter out = new PrintWriter(System.out);

            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int R = in.nextInt();
                long B = in.nextLong();
                int C = in.nextInt();
                Casher[] cashers = new Casher[C];

                for (int j = 0; j < C; j++) {
                    cashers[j] = new Casher(in.nextLong(), in.nextLong(), in.nextLong());
                }

                /** reverse binary search. Set a very high value as the maximum possible solution.
                 * Then find the value of time t such that maximum possible sum of bits bought are still GT/EQ to B.
                 */

                long hi = MAX_POSSIBLE_VALUE;
                long lo = 0L;
                long minTime = 0;
                while (hi >= lo) {
                    out.println("i = " + i + " hi = " + hi + " lo = " + lo);
                    long mid = (hi + lo) / 2;
                    if (canBuyRequiredAmountByGivenTime(cashers, R, B, mid)) {
                        hi = mid - 1;
                        minTime = mid;
                    } else {
                        lo = mid + 1;
                    }
                }
                out.printf("Case #%d: %d\n", i, minTime);
            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * FastScanner class helps me write input/output code very quickly.
     * I would be able to skip writing the type conversion code such as (e.g. Integer.parseInt(x)).
     * This is adopted from another Code Jam competitor from 2018 solution
     * Credit goes to: electronicsaddict
     */

    //@
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(InputStream i) {
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
