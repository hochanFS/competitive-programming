package hackercup.y2019.qualification;

import java.io.*;
import java.util.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 *
 * UPDATE (6/21/2019) FB HACKERCUP NOTIFIED IT AS A WRONG ANSWER.
 * TODO: correct the solution
 *
 * In order for the tree to be valid, there should be only one zero (root).
 * Thus, we can quickly remove the case where all nodes are dependent on each other (no root).
 * In other cases, we can simply assume that a zero node's parent is another zero node.
 * In my case, I have conveniently selected the first-appearing zero as the parent.
 *
 * One way to find the LCA of two nodes is to consider the depth of each node.
 * Then, we iteratively follow through the parents of the nodes in more depth
 * until the two nodes are the same.
 *
 * Since each node has only one parent, we can assume that the size of all ancestors - 1 (root)
 * is the depth of each node.
 *
 * To find all ancestors, I have used a stack data structure and iteratively added new ancestors.
 * For more detail, see TreeBuilder.getDepth() method.
 * This is one of the places that are most run-time heavy in this problem: O(N^2) in the worst case.
 *
 * Finding the immediate parent node can be easily done by looking among the ancestors
 * until the depth is the node's depth - 1.
 *
 * I have validated the tree works by manually finding the LCA and matching it with the text input.
 * Slow (which is unlikely an issue given the size constraints) - but the safest way to confirm!
 */

public class SolutionD {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2019\\qualification\\inputD.txt"; // renamed
        String output = "src\\hackercup\\y2019\\qualification\\solutionD.txt"; // renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] setting = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(setting[0]);
                int M = Integer.parseInt(setting[1]);
                TreeBuilder treeBuilder = new TreeBuilder(br, N, M);
                StringBuilder sb = new StringBuilder();
                if (treeBuilder.isValid()) {
                    for (int j = 1; j <= N; j++) {
                        sb.append(treeBuilder.getTree()[j]).append(' ');
                    }
                }
                else {
                    sb.append("Impossible");
                }
                printWriter.printf("Case #%d: %s\n", i, sb.toString().trim());
            }
            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static class TreeBuilder {
        private int N;
        private int[] tree; // filled with 0 by default
        private int[] depths;
        private HashMap<Integer, HashSet<Integer>> ancestors;
        private HashMap<Integer, HashSet<Integer>> depthLookup;
        private boolean isValid;
        private Constraint constraints;

        TreeBuilder(BufferedReader br, int N, int M) throws IOException{
            this.isValid = true;
            this.N = N;
            this.ancestors = new HashMap<>();
            this.tree = new int[N + 1];
            this.depths = new int[N + 1];
            this.depthLookup = new HashMap<>();
            Arrays.fill(depths, -1);
            for (int j = 0; j <= N; j++) {
                ancestors.put(j, new HashSet<>());
                depthLookup.put(j, new HashSet<>());
            }
            this.constraints = new Constraint();
            for (int j = 0; j < M; j++) {
                String[] lca = br.readLine().trim().split("\\s+");
                int X = Integer.parseInt(lca[0]);
                int Y = Integer.parseInt(lca[1]);
                int Z = Integer.parseInt(lca[2]);
                constraints.add(X, Y, Z);
                if (X != Z)
                    ancestors.get(X).add(Z);
                if (Y != Z)
                    ancestors.get(Y).add(Z);
            }
            boolean doesContainEmpty = false;
            int ancestorIfZero = -1;
            for (int j = 1; j <= N; j++) {
                if (ancestors.get(j).isEmpty()) {
                    if (ancestorIfZero == -1) {
                        ancestorIfZero = j;
                        ancestors.get(j).add(0);
                        doesContainEmpty = true;
                        tree[j] = 0;
                    }
                    if (ancestors.get(j).isEmpty()) {
                        ancestors.get(j).add(ancestorIfZero); // ensure that we have only one root
                    }
                }
            }
            isValid = doesContainEmpty;
            if (doesContainEmpty) {
                for (int j = 1; j <= N; j++) {
                    depths[j] = getDepth(j);
                    depthLookup.get(depths[j]).add(j);
                }
                //<TODO: uncomment for debugging > System.out.println(depthLookup);
                //<TODO: uncomment for debugging > System.out.println(ancestors);
                if (isValid) {
                    for (int j = 1; j <= N; j++) {
                        int depthOfJ = depths[j];
                        if (depthOfJ != 0) {
                            for (int k : ancestors.get(j)) {
                                if (depthLookup.get(depthOfJ - 1).contains(k)) {
                                    tree[j] = k;
                                    break;
                                }
                            }
                        }
                    }
                    for (int j = 0; j < M; j++) {
                        int X = constraints.lists.get(j).get(0);
                        int Y = constraints.lists.get(j).get(1);
                        int Z = constraints.lists.get(j).get(2);
                        //<TODO: uncomment for debugging > System.out.println(X + " " + Y + " " + Z);
                        int xDepth = depths[X];
                        int yDepth = depths[Y];
                        while (xDepth > yDepth) {
                            X = tree[X];
                            xDepth--;
                        }
                        while (yDepth > xDepth) {
                            Y = tree[Y];
                            yDepth--;
                        }
                        while (X != Y) {
                            X = tree[X];
                            Y = tree[Y];
                            xDepth--;
                        }
                        if (X != Z) {
                            isValid = false;
                            break;
                        }
                    }
                }
            }
        }

        boolean isValid() {
            return isValid;
        }

        int[] getTree() {
            if (isValid) {
                return tree;
            }
            else {
                throw new RuntimeException("Attempted to return an invalid tree");
            }
        }

        int getDepth(int i) {
            HashSet<Integer> store = ancestors.get(i);
            ArrayDeque<Integer> stack = new ArrayDeque<>(store);
            while (!stack.isEmpty()) {
                int x = stack.pop();
                if (x != 0) {
                    HashSet<Integer> set = ancestors.get(x);
                    for (Integer y : set) {
                        if (!store.contains(y)) {
                            store.add(y);
                            stack.push(y);
                        }
                    }
                }
            }
            return store.size() - 1;
        }

    }

    public static class Constraint {
        private List<ArrayList<Integer>> lists;
        Constraint() {
            lists = new ArrayList<>();
        }
        public void add(int X, int Y, int Z) {
            Integer[] test = {X, Y, Z};
            lists.add(new ArrayList<>(Arrays.asList(test)));
        }
    }


}
