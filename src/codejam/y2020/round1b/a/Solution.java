package codejam.y2020.round1b.a;

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
                int X = in.nextInt();
                int Y = in.nextInt();

                String solution = solve(X, Y);
                out.printf("Case #%d: %s\n", i, solution);
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static String solve(int x, int y) {
        if (x == 0 && y == 0)
            return "";


        List<String> permuteSet = new ArrayList<>();

        for (int i = 1; i < 10; i++)
            permutation(permuteSet, i);

        for (String s: permuteSet) {
            int step = 1, x_comp = 0, y_comp = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'N')
                    y_comp += step;
                if (s.charAt(i) == 'S')
                    y_comp -= step;
                if (s.charAt(i) == 'E')
                    x_comp += step;
                if (s.charAt(i) == 'W')
                    x_comp -= step;
                if (y_comp == y && x_comp == x)
                    return s.substring(0, i + 1);
                step <<= 1;
            }
        }

        return "IMPOSSIBLE";
    }

    private static void permutation(List<String> values, int len) {
        Deque<String> queue = new ArrayDeque<>();
        queue.addLast("E");
        queue.addLast("W");
        queue.addLast("S");
        queue.addLast("N");
        while (!queue.isEmpty()) {
            String str = queue.removeFirst();
            if (str.length() == len) {
                values.add(str);
                continue;
            }
            queue.addLast(str + "E");
            queue.addLast(str + "W");
            queue.addLast(str + "S");
            queue.addLast(str + "N");
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
