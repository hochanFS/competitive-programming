package coursera.week1;

import java.util.*;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> ret = new ArrayList<>();
        if (n <= 0 || edges == null || edges.length == 0) return ret;

        if (n == 1) {
            System.out.println("?");
            ret.add(0);
            return ret;
        }

        List<List<Integer>> adj = new ArrayList<>();
        int[] degrees = new int[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] edge: edges) {
            degrees[edge[0]]++;
            degrees[edge[1]]++;
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (adj.get(i).size() == 1) {
                queue.addLast(i);
                degrees[i]--;
                if (n <= 2) ret.add(i);
            }

        while (n > 2) {
            int k = queue.size();
            n -= k;
            for (int i = 0; i < k; i++) {
                int node = queue.removeFirst();
                for (int to : adj.get(node)) {
                    degrees[to]--;
                    if (degrees[to] == 1) {
                        if (n <= 2) ret.add(to);
                        queue.addLast(to);
                    }
                }
            }

        }
        return ret;
    }
}