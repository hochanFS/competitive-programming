package codejam.y2020.round1c.a;


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
        Map<Character, int[]> map = new HashMap<>();
        map.put('E', new int[]{0, 1});
        map.put('N', new int[]{1, 0});
        map.put('W', new int[]{0, -1});
        map.put('S', new int[]{-1, 0});
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int X = in.nextInt();
                int Y = in.nextInt();
                char[] directions = in.next().toCharArray();
                int len = directions.length;
                int[][] times = new int[len + 1][2];
                times[0][0] = Y; times[0][1] = X;


                boolean found = false;
                int k = 0;

                if (X != 0 || Y != 0)
                    for (int j = 0; j < len; j++) {
                        k++;
                        int x_delta = map.get(directions[j])[0];
                        int y_delta = map.get(directions[j])[1];
                        times[j + 1][0] = times[j][0] + x_delta;
                        times[j + 1][1] = times[j][1] + y_delta;

                        if (Math.abs(times[j + 1][0]) + Math.abs(times[j + 1][1]) <= k) {
                            found = true;
                            break;
                        }
                    }
                else {
                    found = true;
                }



                String solution = found ? String.valueOf(k) : "IMPOSSIBLE";

                out.printf("Case #%d: %s\n", i, solution);

                //out.println(Arrays.toString(strings)); input checked
            }
            out.close();
        }
        catch (IOException ie) {
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
