package codejam.y2020.round1c.c;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Solution {
    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int D = in.nextInt();
                long[] slices = new long[N];
                TreeMap<Long, Integer> freq = new TreeMap<>();
                for (int j = 0; j < N; j++) {
                    slices[j] = in.nextLong();
                    freq.put(slices[j], freq.getOrDefault(slices[j], 0) + 1);
                }
                Arrays.sort(slices);
                int solution = solve(freq, D, slices);
                out.printf("Case #%d: %d\n", i, solution);
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static int solve(TreeMap<Long, Integer> freq, int D, long[] slices) {
        for (long key : freq.keySet()) {
            if (freq.get(key) >= D)
                return 0;
        }

        if (D == 2) {
            return 1;
        }

        for (long key : freq.keySet()) {
            if (freq.containsKey(2 * key))
                return 1;
        }

        for (long key : freq.keySet()) {
            if (freq.get(key) == 2 && key != freq.lastKey())
                return 1;
        }

        return 2;
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
