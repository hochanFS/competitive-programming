package codejam.y2020.qualification.n3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
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
                int[][] schedules = new int[N][3];
                for (int j = 0; j < N; j++) {
                    schedules[j][0] = in.nextInt();
                    schedules[j][1] = in.nextInt();
                    schedules[j][2] = j;
                }

                out.printf("Case #%d: %s\n", i, arrangeSchedule(schedules));
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static String arrangeSchedule(int[][] schedules) {
        Arrays.sort(schedules, Comparator.comparingInt(u -> u[0]));
        int cameronNextAvailability = 0;
        int jamieNextAvailability = 0;
        StringBuilder sb = new StringBuilder();
        char[] assigned = new char[schedules.length];
        for (int i = 0; i < schedules.length; i++) {
            int start = schedules[i][0];
            int end = schedules[i][1];
            if (cameronNextAvailability <= start) {
                if (jamieNextAvailability <= start) {
                    if (jamieNextAvailability < cameronNextAvailability) {
                        assigned[schedules[i][2]] = 'J';
                        jamieNextAvailability = end;

                        continue;
                    }
                }
                assigned[schedules[i][2]] = 'C';
                cameronNextAvailability = end;
                continue;
            }
            if (jamieNextAvailability <= start ) {
                assigned[schedules[i][2]] = 'J';
                jamieNextAvailability = end;
                continue;
            }
            return "IMPOSSIBLE";
        }
        for (char c : assigned) sb.append(c);
        return sb.toString();
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