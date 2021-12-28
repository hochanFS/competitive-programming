package codejam.y2019.round1BP1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
                int P = in.nextInt();
                int Q = in.nextInt();
                int[] E = new int[Q + 1];
                int[] W = new int[Q + 1];
                int[] S = new int[Q + 1];
                int[] N = new int[Q + 1];
                List<Integer> row = new ArrayList<>(Q + 1);
                List<Integer> col = new ArrayList<>(Q + 1);
                for (int j = 0; j < P; j++) {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    String D = in.next();
                    if (D.equals("E")) {
                        E[x + 1]++;
                    }
                    if (D.equals("S")) {
                        S[y - 1]++;
                    }
                    if (D.equals("N")) {
                        N[y + 1]++;
                    }
                    if (D.equals("W")) {
                        W[x - 1]++;
                    }
                }
                for (int j = 0; j < Q; j++) {
                    E[j + 1] += E[j];
                    N[j + 1] += N[j];
                }
                for (int j = Q; j > 0; j--) {
                    W[j - 1] += W[j];
                    S[j - 1] += S[j];
                }
                for (int j = 0; j <= Q; j++) {
                    row.add(E[j] + W[j]);
                    col.add(S[j] + N[j]);
                }
                int maxRow = Collections.max(row);
                int maxCol = Collections.max(col);
                int solX = 0;
                int solY = 0;
                for (int j = 0; j <= Q; j++) {
                    if (row.get(j) == maxRow) {
                        solX = j;
                        break;
                    }
                }
                for (int j = 0; j <= Q; j++) {
                    if (col.get(j) == maxCol) {
                        solY = j;
                        break;
                    }
                }
                out.printf("Case #%d: %d %d\n", i, solX, solY);

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
