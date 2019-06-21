package codejam.y2019.round1AP4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author Hochan Lee
 */


public class Solution {
    private static final int[] TRIES = {17, 16, 13, 11, 9, 7, 5};
    private static final int NUM_WINDMILLS = 18;


    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int[] cumulative = new int[TRIES.length];
        cumulative[0] = 1;
        for (int j = 1; j < TRIES.length; j++) {
            cumulative[j] = TRIES[j - 1] * cumulative[j - 1];
        }
        try {
            int T = in.nextInt();
            int N = in.nextInt();
            int M = in.nextInt();
            for (int i = 1; i <= T; i ++) {

                int[] feedback = new int[7];
                //int min = 0;
                for (int k = 0; k < TRIES.length; k++) {
                    int x = TRIES[k];
                    out.println(repeat(x, NUM_WINDMILLS));
                    out.flush();
                    int count = 0;
                    for (int j = 0; j < NUM_WINDMILLS; j++) {
                        count += in.nextInt();
                    }
                    //min = Math.max(min, count);
                    feedback[k] = count % TRIES[k];
                }
                out.println(solve(feedback, cumulative, M));
                out.flush();
                int correct = in.nextInt();
                if (correct != 1)
                    throw new RuntimeException();
            }

            out.close();

        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }



    public static String repeat(int i, int k) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < k; j++) {
            sb.append(i).append(' ');
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

    public static int solve(int[] feedback, int[] cumulative, int max) {
        int attemptNum = feedback[0];
        int index = 1;
        while (index < TRIES.length) {
            while (attemptNum % TRIES[index] != feedback[index])
                attemptNum += cumulative[index];
            if (attemptNum + cumulative[index] < max)
                index++;
            else
                break;
        }
        return attemptNum;
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
