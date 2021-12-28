package codejam.y2019.round1BP5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.PrintWriter;

/**
 * @author Hochan Lee
 */

public class Solution {
    //INVERSE MAT
    public static Double[][] MULTIPLIER = {
            {0.1, -0.1, -0.05, 0.0, 0.0, 0.025},
            {-1.2, 1.2, 0.1, 0.0, 0.0, -0.05},
            {-0.4, -0.6, 1.2, 0.0, 0.0, -0.1},
            {1.6, -1.6, -0.8, 1.0, 0.0, -0.1},
            {-1.6, 1.6, 0.8, -1.0, 1.0, -0.4},
            {2.4, -0.4, -1.2, 0.0, -1.0, 0.6}
    };

    //public static BigInteger divisor1 =

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            int W = in.nextInt();

            for (int i = 1; i <= T; i++) {
                BigInteger feedback1, feedback2;
                out.println(57);
                out.flush();
                feedback1 = new BigInteger(in.next().trim());

                out.println(231);
                out.flush();
                feedback2 = new BigInteger(in.next().trim());

                BigInteger two = new BigInteger("2");

                List<BigInteger> sols = new ArrayList<>();
                sols.add(feedback1.shiftRight(57));
                sols.add(feedback1.mod(two.shiftLeft(57)).shiftRight(28));
                sols.add(feedback1.mod(two.shiftLeft(28)).shiftRight(19));
                sols.add(feedback2.shiftRight(57));
                sols.add(feedback2.mod(two.shiftLeft(57)).shiftRight(46));
                sols.add(feedback2.mod(two.shiftLeft(46)).shiftRight(38));

                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < sols.size(); j++) {
                    sb.append(sols.get(j)).append(' ');
                }
                sb.setLength(sb.length() - 1);

                out.println(sb.toString());
                out.flush();

                int correct = in.nextInt();
                if (correct != 1) {
                    throw new IllegalArgumentException();
                }
            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    public static Double[][] multiplicar(Double[][] A, Double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
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
