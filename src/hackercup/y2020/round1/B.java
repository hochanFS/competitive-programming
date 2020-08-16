package hackercup.y2020.round1;


import java.io.*;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class B {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\dislodging_logs_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\r2_official_output.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] line1 = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(line1[0]);
                int M = Integer.parseInt(line1[1]);
                int K = Integer.parseInt(line1[2]);
                int S = Integer.parseInt(line1[3]);
                long[] P = new long[N];
                long[] Q = new long[M];
                String[] line2 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    P[j] = Long.parseLong(line2[j]);
                String[] line3 = br.readLine().trim().split("\\s+");
                long ap = Long.parseLong(line3[0]), bp = Long.parseLong(line3[1]), cp = Long.parseLong(line3[2]), dp = Long.parseLong(line3[3]);
                for (int j = K; j < N; j++)
                    P[j] = (ap * P[j - 2] + bp * P[j - 1] + cp) % dp + 1;
                String[] line4 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    Q[j] = Long.parseLong(line4[j]);
                String[] line5 = br.readLine().trim().split("\\s+");
                long aq = Long.parseLong(line5[0]), bq = Long.parseLong(line5[1]), cq = Long.parseLong(line5[2]), dq = Long.parseLong(line5[3]);
                for (int j = K; j < M; j++)
                    Q[j] = (aq * Q[j - 2] + bq * Q[j - 1] + cq) % dq + 1;
                printWriter.printf("Case #%d: %d\n", i, solve(N, M, P, Q));

            }
            printWriter.close();

        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static long solve(int n, int m, long[] p, long[] q) {
        Arrays.sort(p);
        Arrays.sort(q);
        long lo = 0L, hi = 1_000_000_002L, ret = 2L * hi;
        while(lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            if (isFeasible(mid, p, q, n, m)) {
                ret = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return ret;
    }

    private static boolean isFeasible(long attempt, long[] p, long[] q, int n, int m) {
        int qi = 0;
        for (int i = 0; i < n; i++) {
            long pi = p[i];
            long min = Math.min(pi, q[qi]);
            if (q[qi] <= pi) {
                while(qi + 1 < m && q[qi + 1] <= pi) {
                    qi++;
                }
                qi++;
            }
            if (pi - min > attempt) return false;
            long next = Long.MAX_VALUE;
            if (qi < m) next = Math.min(2 * (pi - min) + q[qi] - pi, pi - min + 2 * (q[qi] - pi));
            while (next <= attempt) {
                if (qi + 1 < m) next = Math.min(2 * (pi - min) + q[qi + 1] - pi, pi - min + 2 * (q[qi + 1] - pi));
                else next = Long.MAX_VALUE;
                qi++;
            }
            if (qi == m) return true;
        }
        return false;
    }
}

