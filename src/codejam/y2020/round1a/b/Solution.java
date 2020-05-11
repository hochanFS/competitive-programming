package codejam.y2020.round1a.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;

/**
 * Uses the property of Pascal where sum of row == 2^(row - 1)
 */

public class Solution {

    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                out.printf("Case #%d:\n", i);
                for (int[] values : solvePascal(N)) {
                    out.printf("%d %d\n", values[0], values[1]);
                }
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static List<long[]> unwrapPath(List<Long> paths) {
        List<long[]> output = new ArrayList<>();
        for (long path : paths) {
            output.add(new long[]{path / 301, path % 301});
        }
        return output;
    }

    private static List<int[]> solvePascal(int n) {
        List<int[]> output = new ArrayList<>();
        if (n <= 30) {
            for (int i = 1; i <= n; i++)
            output.add(new int[]{i, 1});
            return output;
        }
        n -= 30;
        boolean left = true;
        int countBit = 0;
        for (int i = 0; i < 30; i++) {
            output.add(new int[]{i + 1, left? 1: i + 1});
            if (((n >> i) & 1) == 1) {
                if (left) {
                    for (int j = 1; j <= i; ++j) {
                        output.add(new int[]{i + 1, j + 1});
                    }
                } else {
                    for (int j = i - 1; j >= 0; j--) {
                        output.add(new int[]{i + 1, j + 1});
                    }
                }
                left = !left;
                countBit++;
            }
        }
        for (int i = 1; i <= countBit; i++) {
            output.add(new int[]{30 + i, left? 1: 30 + i});
        }
        return output;

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
