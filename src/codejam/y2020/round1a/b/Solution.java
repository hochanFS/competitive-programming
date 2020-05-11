package codejam.y2020.round1a.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Solution {

    public static final int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {-1, 0}, {0, -1}, {-1, -1}};

    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            long[][] memo = new long[300][300];
            memo[1][1] = 1;
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                out.printf("Case #%d:\n", i);
                for (long[] values : solvePascal(N, memo)) {
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

    private static List<long[]> solvePascal(int n, long[][] memo) {
        String s = Integer.toBinaryString(n); // e.g. 6 -> 1 1 0
        List<Long> paths = new ArrayList<>();
        Set<Long> seen = new HashSet<>();
        seen.add(302L);
        dfs(302, seen, paths, memo, 0, n); // start with (1, 1) with empty paths...
        return unwrapPath(paths);
    }

    private static boolean dfs(long current, Set<Long> visited, List<Long> paths, long[][] memo, long sum, long target) {
        long n = current / 301;
        long k = current % 301;
        long val = sum + getPascalValue((int)n, (int)k, memo);
        paths.add(current);
        if (val > target)
            return false;
        if (val == target)
            return true;
        PriorityQueue<long[]> pq = new PriorityQueue<>((u, v) -> Long.compare(v[1], u[1]));
        for (int[] direction : directions) {
            long nextN = n + direction[0];
            long nextK = k + direction[1];
            long key = getKey(nextN, nextK);
            if (nextK <= nextN && nextK >= 1 && !visited.contains(key)) {
                long addition = getPascalValue((int)nextN, (int)nextK, memo);
                if (addition + sum <= target) {
                    pq.add(new long[]{key, addition + sum});
                }

            }

        }
        while(!pq.isEmpty()) {
            long[] values = pq.poll();
            long key = values[0];
            long cum = values[1];
            visited.add(key);
            if (dfs(key, visited, paths, memo, cum, target))
                return true;
            visited.remove(key);
            paths.remove(paths.size() - 1);
        }
        return false;
    }

    private static long getPascalValue(int n, int k, long[][] memo) {
        if (k == 1 || k == n)
            return 1;
        if (memo[n][k] != 0L)
            return memo[n][k];
        memo[n][k] = getPascalValue(n - 1, k - 1, memo) + getPascalValue(n - 1, k, memo);
        return memo[n][k];
    }

    private static long getKey(long n, long k) {
        return n * 301 + k;
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
