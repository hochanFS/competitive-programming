package codejam.y2021.qualification.n2;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);

        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int X = in.nextInt(), Y = in.nextInt(); // X = CJ, Y = JC
                String S = in.next();
                char[] arr = S.toCharArray();
                int[][] dp = new int[arr.length][4];
                fill(dp, X, Y);
                out.printf("Case #%d: %d\n", i, cost(arr, dp, X, Y));
            }

            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static void fill(int[][] dp, int X, int Y) {
        dp[0][0] = Math.min(0, X + Y); // C??C = C?JC + C?CC
        dp[0][1] = X; // C?J
        dp[0][2] = Y; // J?C
        dp[0][3] = Math.min(0, X + Y); // J?JJ
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + Y);
            dp[i][1] = Math.min(dp[i - 1][1], dp[i - 1][0] + X);
            dp[i][2] = Math.min(dp[i - 1][2], dp[i - 1][3] + Y);
            dp[i][3] = Math.min(dp[i - 1][2] + X, dp[i - 1][3]);
        }
    }

    private static int cost(char[] ca, int[][] dp, int X, int Y) {
        if (ca.length == 1) return 0;

        int val = 0;
        int n = ca.length;
        int min = Integer.MAX_VALUE;
        if (ca[0] == '?') {
            ca[0] = 'J';
            min = Math.min(min, cost(ca.clone(), dp, X, Y));
            ca[0] = 'C';
            min = Math.min(min, cost(ca.clone(), dp, X, Y));
        }
        else if (ca[n - 1] == '?') {
            ca[n - 1] = 'J';
            min = Math.min(min, cost(ca.clone(), dp, X, Y));
            ca[n - 1] = 'C';
            min = Math.min(min, cost(ca.clone(), dp, X, Y));
        }
        else {
            if (ca.length == 2) {
                if (ca[0] == ca[1]) return 0;
                return ca[0] == 'C' ? X: Y;
            }

            for (int i = 1; i < n; i++) {
                int a = i;
                if (ca[i] == 'C' && ca[i - 1] == 'J') val += Y;
                if (ca[i] == 'J' && ca[i - 1] == 'C') val += X;
                if (ca[i] != '?') continue;
                int len = 1;
                while (i + 1 < ca.length && ca[i + 1] == '?') {
                    i++;
                    len++;
                }
                if (ca[a - 1] == 'C' && ca[i + 1] == 'C') val += dp[len - 1][0];
                if (ca[a - 1] == 'C' && ca[i + 1] == 'J') val += dp[len - 1][1];
                if (ca[a - 1] == 'J' && ca[i + 1] == 'C') val += dp[len - 1][2];
                if (ca[a - 1] == 'J' && ca[i + 1] == 'J') val += dp[len - 1][3];
            }
            min = val;
        }

        return min;
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
