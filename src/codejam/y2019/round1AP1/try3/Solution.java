package codejam.y2019.round1AP1.try3;

import java.io.*;
import java.util.*;

public class Solution {

    private int dimension;
    private int C;
    private int R;
    private Coordinate[] coordinates;
    private boolean[] visited;
    private GraphPlain graph;

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
        this.R = R;
        this.C = C;
        dimension = R * C;
        graph = new GraphPlain(dimension);
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                for (int x = 0; x < R; x++) {
                    for (int y = 0; y < C; y++) {
                        if (i != x && j != y && i + j != x + y && i - j != x - y) {
                            graph.addDirectedEdge(i * C + j, x * C + y);
                        }
                    }
                }
            }
        }
        graph.shuffle();
        coordinates = new Coordinate[dimension];
        visited = new boolean[dimension];
    }


    public class Coordinate {
        int i;

        Coordinate(int i) {
            this.i = i;
        }

        @Override
        public String toString() {
            int r = i / C + 1;
            int c = i % C + 1;
            return r + " " + c;
        }
    }

    public boolean possible() {
        int count = 0;
        {
            int i = C + 1;
            attempt(i, count);
            if (isComplete())
                return true;
            visited[i] = false;
        }
        return false;
    }

    private void attempt(int i, int count) {
        if (coordinates[dimension - 1] != null)
            return;

        if (visited[i])
            return;

        visited[i] = true;
        coordinates[count] = new Coordinate(i);

        for (int x : graph.getAdj(i)) {
            if (!visited[x]) {
                attempt(x, count + 1);
                if (isComplete())
                    return;
                visited[x] = false;
            }
        }
    }

    public Coordinate[] solutions() {
        return coordinates;
    }


    private boolean isComplete() {
        return coordinates[dimension - 1] != null;
    }


    /**
     * Graph class built for competitive programming
     * This intends to support graph problems, including non-integer type.
     * For non-integer type, one can use an array's index as the vertex id.
     * This graph does NOT support weights
     *
     * @author Hochan Lee
     */
    public class GraphPlain {
        private final int V;
        private int E;
        private ArrayList<Integer>[] adj;
        private final String NEWLINE = System.getProperty("line.separator");
        private final int MY_FAVORITE_RANDOM_NUMBER = 6; // can be any integer

        @SuppressWarnings("unchecked")
        public GraphPlain(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
            this.V = V;
            this.E = 0;
            adj = (ArrayList<Integer>[]) new ArrayList[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<>();
            }
        }

        /**
         * Shuffling often improves the run time of DFS or BFS significantly.
         * Run time would be O(E)
         */
        public void shuffle() {
            Random random = new Random(MY_FAVORITE_RANDOM_NUMBER);
            for (int i = 0; i < V; i++) {
                int size = adj[i].size();
                for (int j = 0; j < size; j++) {
                    int at = random.nextInt(size);
                    int temp = adj[i].get(at);
                    adj[i].set(at, adj[i].get(j));
                    adj[i].set(j, temp);
                }
            }
        }

        public void addDirectedEdge(int v, int w) {
            E++;
            adj[v].add(w);
        }

        public Iterable<Integer> getAdj(int v) {
            return adj[v];
        }

        /**
         * For debugging
         *
         * @return the description of the Graph
         */
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V).append(" vertices, ").append(E).append(" edges ").append(NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(v).append(": ");
                for (int w : adj[v]) {
                    s.append(w).append(" ");
                }
                s.append(NEWLINE);
            }
            return s.toString();
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
