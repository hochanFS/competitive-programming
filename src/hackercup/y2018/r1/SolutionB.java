package hackercup.y2018.r1;

import java.io.*;
import java.util.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 * First step is to build the tree.
 * The assumptions that the first node is always the root and the tree is always valid
 * relieves the implementation headache.
 * <p>
 * One way to solve this problem is simply to list pre-order and post-order traversal.
 * Then, we will be able to observe which nodes should have the same ID.
 * <p>
 * If there is no way we can have greater than equal to K ID's, we know it is impossible.
 * If we can have more than K, we can simply fill the rest nodes with the same ID.
 */

public class SolutionB {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\r1\\inputB.txt";
        String output = "src\\hackercup\\y2018\\r1\\solutionB.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                String[] settings = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(settings[0]);
                int K = Integer.parseInt(settings[1]);
                Node[] nodes = new Node[N + 1];
                for (int j = 1; j <= N; j++) {
                    nodes[j] = new Node(j);
                }
                nodes[0] = null;

                for (int j = 1; j <= N; j++) {
                    String[] childs = br.readLine().trim().split("\\s+");
                    int leftId = Integer.parseInt(childs[0]);
                    int rightId = Integer.parseInt(childs[1]);
                    nodes[j].left = nodes[leftId];
                    nodes[j].right = nodes[rightId];

                }
                int[] preOrder = preOrderTraverse(nodes, N);
                int[] postOrder = postOrderTraverse(nodes, N);
                int[] solution = new int[N + 1];

                // TODO: remove debugging code
                System.out.printf("Case #%d: \n", i);
                System.out.println("PreOrder:  " + Arrays.toString(preOrder));
                System.out.println("PostOrder: " + Arrays.toString(postOrder));

                TreeSet<Integer> indexToReview = new TreeSet<>();
                Map<Integer, Integer> lookUpPreOrder = new HashMap<>();
                for (int j = 0; j < N; j++) {
                    indexToReview.add(j + 1);
                    lookUpPreOrder.put(preOrder[j], j);
                }
                int newId = 1;
                while (!indexToReview.isEmpty()) {
                    int begin = indexToReview.first();
                    solution[begin] = newId;
                    indexToReview.remove(begin);
                    int next = postOrder[lookUpPreOrder.get(begin)];
                    while (next != begin) {
                        solution[next] = newId;
                        indexToReview.remove(next);
                        next = postOrder[lookUpPreOrder.get(next)];
                    }
                    if (newId < K && !indexToReview.isEmpty()) {
                        newId++;
                    }
                }
                StringBuilder sb = new StringBuilder();
                if (newId == K) {
                    for (int j = 1; j <= N; j++) {
                        sb.append(solution[j]).append(' ');
                    }
                } else {
                    sb.append("Impossible");
                }

                printWriter.printf("Case #%d: %s\n", i, sb.toString().trim());

                //validation
                {
                    if (newId == K) {
                        //int[] newPreOrder = new int[N];
                        //int[] newPostOrder = new int[N];
                        for (int j = 0; j < N; j++) {
                            if (solution[preOrder[j]] != solution[postOrder[j]]) {
                                System.out.printf("FAILED: case#%d\n", i);
                            }
                        }
                    }
                }
            }

            printWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static int[] preOrderTraverse(Node[] nodes, int N) {
        ArrayDeque<Node> stack = new ArrayDeque<>();
        stack.push(nodes[1]); // root
        int index = 0;
        int[] traversal = new int[N];
        while (!stack.isEmpty() && index < N) {
            Node node = stack.pop();
            traversal[index] = node.id;
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
            index++;
        }
        return traversal;
    }

    public static int[] postOrderTraverse(Node[] nodes, int N) {
        ArrayDeque<Node> stack = new ArrayDeque<>();
        stack.push(nodes[1]); // root
        int index = N - 1;
        int[] traversal = new int[N];
        while (!stack.isEmpty() && index >= 0) {
            Node node = stack.pop();
            traversal[index] = node.id;
            if (node.left != null)
                stack.push(node.left);
            if (node.right != null)
                stack.push(node.right);
            index--;
        }
        return traversal;
    }

    private static class Node {
        Node left;
        Node right;
        int id;

        Node(int id) {
            this.id = id;
            this.left = null;
            this.right = null;
        }
    }
}

