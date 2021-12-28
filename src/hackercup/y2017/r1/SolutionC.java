package hackercup.y2017.r1;

import java.io.*;
import java.util.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

public class SolutionC {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2017\\r1\\inputC.in";
        String output = "src\\hackercup\\y2017\\r1\\outputC.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                String[] integers = br.readLine().split("\\s+");
                int N = Integer.parseInt(integers[0]);
                int M = Integer.parseInt(integers[1]);
                int K = Integer.parseInt(integers[2]);
                SolutionC solutionC = new SolutionC(N, M, K, br);
                printWriter.printf("Case #%d: %d\n", i, solutionC.getSolution());
                System.out.printf("Case #%d done!\n", i);
            }
            printWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private Graph graph;
    private Delivery[] deliveries;
    private long[][] distances;
    private int[][] at;
    private int K;


    public SolutionC(int N, int M, int K, BufferedReader br) throws IOException {
        this.graph = new Graph(N, M);
        this.deliveries = new Delivery[K];
        this.K = K;
        for (int i = 0; i < M; i++) {
            String[] edgeInfo = br.readLine().trim().split("\\s+");
            int v1 = Integer.parseInt(edgeInfo[0]);
            int v2 = Integer.parseInt(edgeInfo[1]);
            int weight = Integer.parseInt(edgeInfo[2]);
            graph.addEdge(v1 - 1, v2 - 1, weight);
        }
        graph.calculateShortestPaths();
        for (int i = 0; i < K; i++) {
            String[] deliveryInfo = br.readLine().trim().split("\\s+");
            int from = Integer.parseInt(deliveryInfo[0]);
            int to = Integer.parseInt(deliveryInfo[1]);
            deliveries[i] = new Delivery(from - 1, to - 1);
        }
        distances = new long[2 * K][2];
        at = new int[2 * K][2];

    }

    public long getSolution() {
        if (K == 1) {
            return graph.hasPath(0, deliveries[0].S) && graph.hasPath(deliveries[0].S, deliveries[0].D) ?
                    graph.dist(0, deliveries[0].S) + graph.dist(deliveries[0].S, deliveries[0].D) : -1;
        }
        //graph.print();
        if (graph.hasPath(0, deliveries[0].S)) {
            distances[0][0] = graph.dist(0, deliveries[0].S);
            distances[0][1] = distances[0][0];
            at[0][0] = deliveries[0].S;
            at[0][1] = at[0][0];
        } else {
            return -1;
        }
        int to0 = 0;
        int to1 = 0;
        for (int i = 1; i < 2 * K - 1; i += 2) {
            to0 = deliveries[(i / 2) + 1].S;
            to1 = deliveries[i / 2].D;

            if (!graph.hasPath(at[i - 1][0], to0) || !graph.hasPath(to0, to1)) {
                return -1;
            }
            at[i][0] = to0;
            at[i][1] = to1;
            at[i + 1][0] = to1;
            at[i + 1][1] = to0;
            distances[i][0] = Math.min(graph.dist(at[i - 1][0], to0) + distances[i - 1][0],
                    graph.dist(at[i - 1][1], to0) + distances[i - 1][1]);
            distances[i][1] = Math.min(graph.dist(at[i - 1][0], to1) + distances[i - 1][0],
                    graph.dist(at[i - 1][1], to1) + distances[i - 1][1]);
            distances[i + 1][0] = distances[i][0] + graph.dist(to0, to1);
            distances[i + 1][1] = distances[i][1] + graph.dist(to0, to1);
        }
        to0 = deliveries[K - 1].D;
        if (!graph.hasPath(at[K - 2][0], to0))
            return -1;
        distances[2 * K - 1][0] = distances[2 * K - 2][0] + graph.dist(at[2 * K - 2][0], to0);
        distances[2 * K - 1][1] = distances[2 * K - 2][1] + graph.dist(at[2 * K - 2][1], to0);
        at[2 * K - 1][0] = to0;
        at[2 * K - 1][1] = to1;

        // TODO: uncomment below for debugging
        /*System.out.println("Distances");
        for (int i = 0; i < 2 * K; i ++)
            System.out.println(Arrays.toString(distances[i]));
        System.out.println("At");
        for (int i = 0; i < 2 * K; i ++)
            System.out.println(Arrays.toString(at[i]));*/

        return Math.min(distances[2 * K - 1][1], distances[2 * K - 1][0]);
    }


    public class Graph {
        private long[][] dist;
        private int V;
        private int E;
        private Map<Integer, HashSet<Integer>> edges;
        private Edge[][] edgeTo;

        Graph(int V, int E) {
            this.V = V;
            this.E = E;
            this.dist = new long[V][V];
            this.edges = new HashMap<>();
            for (int i = 0; i < V; i++) {
                Arrays.fill(dist[i], Long.MAX_VALUE);
                dist[i][i] = 0;
                edges.put(i, new HashSet<>());
            }
            edgeTo = new Edge[V][E];
        }

        void addEdge(int v1, int v2, int weight) {
            edges.get(v1).add(v2);
            edges.get(v2).add(v1);
            dist[v1][v2] = Math.min(weight, dist[v1][v2]);
            dist[v2][v1] = dist[v1][v2];
        }

        void calculateShortestPaths() {
            // inspired by Dijkstra's algorithm
            for (int i = 0; i < V; i++) {
                boolean[] visited = new boolean[V];
                PriorityQueue<Edge> pq = new PriorityQueue<>();
                ArrayDeque<Integer> queue = new ArrayDeque<>();
                for (int j : edges.get(i))
                    queue.addLast(j);
                visited[i] = true;
                while (!queue.isEmpty()) {
                    int v1 = queue.removeFirst();
                    if (visited[v1])
                        continue;
                    for (int v2 : edges.get(v1)) {
                        if (!visited[v2]) {
                            queue.addLast(v2);
                        }
                        dist[i][v2] = Math.min(dist[i][v2], dist[i][v1] + dist[v1][v2]);
                        dist[v2][i] = dist[i][v2];
                        dist[i][v1] = Math.min(dist[i][v1], dist[i][v2] + dist[v2][v1]);
                        dist[v1][i] = dist[i][v1];
                    }
                    visited[v1] = true;
                }
            }
        }

        boolean hasPath(int v1, int v2) {
            return !(dist[v1][v2] == Long.MAX_VALUE);
        }

        long dist(int v1, int v2) {
            return dist[v1][v2];
        }

        void print() {
            // for debugging
            for (int i = 0; i < V; i++) {
                System.out.println(Arrays.toString(dist[i]));
            }
        }
    }

    public class Delivery {
        int S;
        int D;

        Delivery(int S, int D) {
            this.S = S;
            this.D = D;
        }
    }

    public class Edge implements Comparable<Edge> {
        int v1;
        int v2;
        long weight;

        Edge(int v1, int v2, long weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.weight != o.weight) {
                return Long.compare(this.weight, o.weight);
            }
            return Integer.compare(this.v2, o.v2);
        }
    }

}
