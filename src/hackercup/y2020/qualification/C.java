package hackercup.y2020.qualification;

import java.io.*;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class C {
    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\timber_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\c_official_out.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                int N = Integer.parseInt(br.readLine().trim());
                Tree[] trees = new Tree[N];
                for (int tree = 0; tree < N; tree++) {
                    String[] line = br.readLine().trim().split("\\s+");
                    long pos = Long.parseLong(line[0]), height = Long.parseLong(line[1]);
                    trees[tree] = new Tree(pos, height);
                }
                Map<Long, Long> map = new HashMap<>();
                long max = 0;

                Arrays.sort(trees, Comparator.comparingLong(u -> u.pos));

                for (Tree tree: trees) {
                    long pos = tree.pos, height = tree.height;
                    long currentMax = map.getOrDefault(pos, 0L);
                    long lagMax = map.getOrDefault(pos - height, 0L);
                    max = Math.max(max, Math.max(currentMax, lagMax) + height);

                    // cut by right
                    map.put(pos + height,
                            Math.max(map.getOrDefault(pos + height, 0L), currentMax + height));

                    // cut by left
                    map.put(pos, Math.max(map.getOrDefault(pos, 0L), lagMax + height));

                }
                printWriter.printf("Case #%d: %d\n", i, max);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static class Tree {
        long pos, height;
        Tree(long pos, long height) {
            this.pos = pos;
            this.height = height;
        }
    }
}
