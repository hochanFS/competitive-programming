package codejam.y2018.round1BP3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
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
                int M = in.nextInt();
                int[] initialCondition = new int[M];
                Formula[] formulas = new Formula[M];
                Solution sol = new Solution(M, in);
                long solution = sol.solve();
                out.printf("Case #%d: %d\n", i, solution);

            }
            out.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private final int M;
    private Formula[] formulas;
    private long[] remaining;
    private boolean needProxy;

    Solution(int m, In in) {
        this.M = m;
        needProxy = false;
        try {
            formulas = new Formula[M];
            for (int j = 0; j < M; j++) {
                formulas[j] = new Formula(in.nextInt() - 1, in.nextInt() - 1);
            }
            remaining = new long[M];
            for (int k = 0; k < M; k++) {
                remaining[k] = in.nextLong();
                if (remaining[k] > 100)
                    needProxy = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Formula {
        int M1, M2;

        Formula(int i, int j) {
            M1 = i;
            M2 = j;
        }
    }

    public long solve() {
        long[] proxyArray = new long[M];

        if (needProxy) {

        } else {
            System.arraycopy(remaining, 0, proxyArray, 0, M);
        }
        Comparator<Formula> formulaComparator = new Comparator<Formula>() {
            @Override
            public int compare(Formula o1, Formula o2) {
                long o1Min = Math.min(proxyArray[o1.M1], proxyArray[o1.M2]);
                long o2Min = Math.min(proxyArray[o2.M1], proxyArray[o2.M2]);
                if (o1Min != o2Min)
                    return Long.compare(o1Min, o2Min);

                long o1TheOther = o1Min == proxyArray[o1.M1] ? proxyArray[o1.M2] : proxyArray[o1.M1];
                long o2TheOther = o2Min == proxyArray[o1.M1] ? proxyArray[o2.M2] : proxyArray[o2.M1];
                return Long.compare(o1TheOther, o2TheOther);
            }
        };

        long count = 0;
        PriorityQueue<Formula> pq = new PriorityQueue<Formula>(formulaComparator);
        pq.addAll(Arrays.asList(formulas));
        while (canBeUsed(pq.peek(), proxyArray)) {
            Formula temp = pq.poll();
            proxyArray[temp.M1]--;
            proxyArray[temp.M2]--;
            count++;
            pq.add(new Formula(temp.M1, temp.M2));
        }

        return count;
    }

    private boolean canBeUsed(Formula f, long[] proxyArray) {
        return proxyArray[f.M1] > 0 && proxyArray[f.M2] > 0;
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
