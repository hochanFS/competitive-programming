package codejam.y2019.round1AP2.Try2;

import java.io.*;
import java.util.*;

public class Solution {
    private List<Integer>[] adj;
    private int dimension;
    private int CC;
    private int RR;
    private List<Coordinate> coordinates;
    private boolean[] visited;
    private Random rand;
    int MAX = 1000;

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);

        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int R = in.nextInt();
                int C = in.nextInt();
                Solution sol = new Solution(R, C);
                String answer1 = sol.possible() ? "POSSIBLE" : "IMPOSSIBLE";
                out.println("Case #" + i + ": " + answer1);
                if (sol.possible()) {
                    for (Coordinate x : sol.solutions()) {
                        out.println(x);
                    }
                }
            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Solution(int R, int C) {
        RR = R;
        CC = C;
        dimension = R * C;
        adj = (ArrayList<Integer>[]) new ArrayList[dimension];
        rand = new Random();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                adj[i * C + j] = new ArrayList<>();
                for (int k = 0; k < R; k++) {
                    for (int l = 0; l < C; l++) {
                        if (i != k && j != l && i - j != k - l && i + j != k + l)
                            adj[i * C + j].add(k * C + l);
                    }
                }
                Collections.shuffle(adj[i]);
            }
        }

        /**for (int i = 0; i < dimension; i ++) {
         adj[i] = new ArrayList<>();
         for (int j = 0; j < dimension; j++) {
         if (i / C != j / C && i % C != j % C && Math.abs(i - j) % (C + 1) != 0 && Math.abs(i - j) % (C - 1) != 0)
         adj[i].add(j);
         }
         }*/
        coordinates = new ArrayList<>();
        visited = new boolean[dimension];

        ////TODO: REMOVE
        /**for (int i = 0; i < dimension; i ++) {
         System.out.println(i + ": " + adj[i]);
         }*/
    }

    public boolean possible() {
        int count = 0;
        for (int i = 0; i < Math.min(CC, CC / 2 + 1); i++) {
            attempt(i, count);
            if (isComplete())
                return true;
            visited[i] = false;
            coordinates.remove(0);
        }
        return false;
    }

    private void attempt(int i, int count) {
        if (coordinates.size() == dimension)
            return;
        int countWithinLoop = 0;
        if (visited[i])
            return;
        visited[i] = true;
        coordinates.add(new Coordinate(i));
        for (int x : adj[i]) {
            if (!visited[x] && countWithinLoop++ < MAX) {
                attempt(x, count + 1);
                if (coordinates.size() == dimension)
                    return;
                coordinates.remove(count + 1);
                visited[x] = false;
            }
        }
    }

    public List<Coordinate> solutions() {
        return coordinates;
    }


    private boolean isComplete() {
        return coordinates.size() == dimension;
    }


    public class Coordinate {
        int r;
        int c;

        Coordinate(int i) {
            r = i / CC + 1;
            c = i % CC + 1;
        }

        @Override
        public String toString() {
            return r + " " + c;
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
