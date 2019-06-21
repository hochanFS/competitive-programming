package codejam.y2019.round1BP2;

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
                int[] C = new int[N];
                int[] D = new int[N];

                for (int j = 0; j < N; j++) {
                    C[j] = in.nextInt();
                }
                for (int j = 0; j < N; j++) {
                    D[j] = in.nextInt();
                }

                int count = 0;
                for (int j = 0; j < N; j++) {
                    int maxC = C[j];
                    int maxD = D[j];
                    for (int k = j; k < N; k++) {
                        maxC = Math.max(maxC, C[k]);
                        maxD = Math.max(maxD, D[k]);
                        if (Math.abs(maxC - maxD) <= K)
                            count ++;
                    }
                }



                out.printf("Case #%d: %d\n", i, count);

            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static class SubRange {
        int lo, hi;
        public SubRange(int i, int j) {
            lo = i;
            hi = j;
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
            if(st.hasMoreTokens())
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
