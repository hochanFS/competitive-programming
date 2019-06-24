package hackercup.y2017.r1;

import java.io.*;
import java.util.*;

public class SolutionC {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2017\\r1\\sampleC.txt";
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
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private Graph graph;
    private Delivery[] deliveries;


    public SolutionC(int N, int M, int K, BufferedReader br) throws IOException{
        this.graph = new Graph(N);
        this.deliveries = new Delivery[K];
        for (int i = 0; i < M; i++) {
            String[] edgeInfo = br.readLine().trim().split("\\s+");
            int v1 = Integer.parseInt(edgeInfo[0]);
            int v2 = Integer.parseInt(edgeInfo[1]);
            int weight = Integer.parseInt(edgeInfo[2]);
            graph.addEdge(v1 - 1, v2 - 1, weight);
        }
        graph.calculateShortestPaths();
        for (int i = 0; i < K ; i++) {
            String[] deliveryInfo = br.readLine().trim().split("\\s+");
            int from = Integer.parseInt(deliveryInfo[0]);
            int to = Integer.parseInt(deliveryInfo[0]);
            deliveries[i] = new Delivery(from, to);
        }
    }

    public int getSolution() {

        return 1; // placeholder
    }


    public class Graph {
        private int[][] dist;
        private int V;
        private Map<Integer, HashSet<Integer>> edges;

        Graph(int V) {
            this.V = V;
            this.dist = new int[V][V];
            this.edges = new HashMap<>();
            for (int i = 0; i < V; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
                dist[i][i] = 0;
                edges.put(i, new HashSet<>());
            }
        }

        void addEdge(int v1, int v2, int weight) {
            edges.get(v1).add(v2);
            edges.get(v2).add(v1);
            dist[v1][v2] = Math.min(weight, dist[v1][v2]);
            dist[v2][v1] = dist[v1][v2];
        }

        void calculateShortestPaths() {
            for (int i = 0; i < V; i++) {
                boolean[] visited = new boolean[V];
                ArrayDeque<Integer> queue = new ArrayDeque<>();
                for (int j : edges.get(i))
                    queue.addLast(j);
                visited[i] = true;
                while (! queue.isEmpty()) {
                    int v1 = queue.removeFirst();
                    if (visited[v1])
                        continue;
                    for (int v2 : edges.get(v1)) {
                        if (!visited[v2]) {
                            queue.addLast(v2);
                        }
                        dist[i][v2] = Math.min(dist[i][v2], dist[i][v1] + dist[v1][v2]);
                    }
                    visited[v1] = true;
                }
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

}
