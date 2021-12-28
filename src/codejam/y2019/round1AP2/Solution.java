package codejam.y2019.round1AP2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.io.PrintWriter;

/**
 * @author Hochan Lee
 */

public class Solution {
    public int R;
    public int C;
    public int[][] array;
    public boolean[][] marked;
    public int[] edgeTo;
    List<Set<Coordinate>> edges;
    List<Coordinate> starting;

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int R = in.nextInt();
                int C = in.nextInt();
                int[][] matrix = new int[R][C];


            }

            //out.printf("Case #%d: %.6f\n", i, sumInitArea + memoize[N][constraint]);
            //out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static class Coordinate {
        int x, y;

        Coordinate(int a, int b) {
            x = a;
            y = b;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(x).append(' ').append(y);
            return sb.toString();
        }
    }

    Solution(int r, int c) {
        R = r;
        C = c;
        array = new int[r][c];
        marked = new boolean[r][c];
        edges = new ArrayList<>();
        starting = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                edges.set(i * c + r, buildEdge(i, j));
                starting.set(i * c + r, new Coordinate(i, j));
            }
        }
        edgeTo = new int[r * c];
    }

    private Set<Coordinate> buildEdge(int x, int y) {

        Set<Coordinate> temp = new HashSet<>();
        for (int i = 0; i < R; i++) {
            if (i != x) {
                for (int j = 0; j < C; j++) {
                    if (j != y && Math.abs(x - y) != Math.abs(i - j)) {
                        temp.add(new Coordinate(i, j));
                    }
                }
            }
        }
        return temp;
    }

    public List<Coordinate> getSolution() {
        for (Coordinate c : starting) {
            if (!marked[c.x][c.y])
                dfs(c, 0);
        }
        return null;
    }

    private boolean dfs(Coordinate c, int count) {
        marked[c.x][c.y] = true;
        if (count == C * R - 1)
            return true;
        for (Coordinate next : edges.get(c.x * C + c.y)) {
            if (!marked[next.x][next.y]) {
                edgeTo[next.x * C + next.y] = c.x * C + c.y;
                dfs(next, count + 1);
                marked[c.x][c.y] = false;
            }
        }

        return false;
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
