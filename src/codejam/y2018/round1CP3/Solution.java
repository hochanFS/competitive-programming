package codejam.y2018.round1CP3;

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
                long[] weights = new long[N + 1];
                for (int j = 1; j <= N; j++) {
                    weights[j] = in.nextLong();
                }
                long[] numAnts = new long[N + 1];
                long[][] cumWeights = new long[N + 1][142];
                numAnts[1] = 1;
                cumWeights[1][1] = weights[1];
                for (int j = 2; j <= N; j++) {
                    long weight = weights[j];
                    numAnts[j] = numAnts[j - 1];
                    cumWeights[j][1] = Math.min(cumWeights[j - 1][1], weight);
                    for (int k = 1; k <= numAnts[j - 1]; k++) {
                        if (weight * 6 >= cumWeights[j - 1][k]) {
                            if (k == numAnts[j - 1]) {
                                numAnts[j]++;
                                cumWeights[j][k + 1] = cumWeights[j - 1][k] + weight;
                            } else {
                                cumWeights[j][k + 1] = Math.min(cumWeights[j - 1][k] + weight, cumWeights[j - 1][k + 1]);
                            }
                        }
                        if (cumWeights[j][k + 1] == 0 && cumWeights[j - 1][k + 1] != 0) {
                            cumWeights[j][k + 1] = cumWeights[j - 1][k + 1];
                        }
                    }
                }

                /*for (int j = 1; j <= N; j++) {
                    out.println(Arrays.toString(cumWeights[j]));

                }
                out.println(Arrays.toString(numAnts));*/


                out.printf("Case #%d: %d\n", i, numAnts[N]);
            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
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
