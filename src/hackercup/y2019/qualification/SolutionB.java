package hackercup.y2019.qualification;

import java.io.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 * Unlike the first problem, A can also leap over B and land immediately to B's left.
 * One observation: Since A can leap to the right side, all possible cases where A could
 * successfully land to the rightmost with only jumps to the right should be valid.
 * In case A cannot reach the rightmost lilypad only with jumps to the right, we should consider
 * how jumps to the left can help A achieve its goal.
 * Consider the following case:
 *
 * >> ABB... // emptyCount = 3, betaCount = 2
 * >> AB.B..
 * >> .BAB..
 * >> .B.BA.
 * >> ..BBA.
 * >> .ABB..
 * >> .AB.B.
 * >> ..BAB.
 * >> ..B.BA // success!
 *
 * Or,
 *
 * >> ABBB.... // emptyCount = 4, betaCount = 3
 * >> AB.B.B..
 * >> .B.B.BA.
 * >> ...BBBA.
 * >> ..ABBB..
 * >> ..ABB.B.
 * >> ...BB.BA // success!
 *
 * As shown above, given A's position at x and number of B's b, A can first jump up to A + 2 * b,
 * then jump back to the left to A - b - 1, and then jump again to the right by A + 2 * b.
 * So, as long as B >= 2, and there is at least one empty lilypad (emptyCount >= 1), A can reach anywhere!
 */

public class SolutionB {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2019\\qualification\\inputB.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2019\\qualification\\solutionB.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                char[] ponds = br.readLine().trim().toCharArray(); // read the pond state for each day
                int N = ponds.length;
                boolean canReach = false; // tracks whether reaching to the right is at all possible
                int betaCount = 0;
                int emptyCount = 0;
                for (int j = 1; j < N; j ++) { // constraint: N >= 2 for all cases
                    if (ponds[j] == '.') {
                        emptyCount++;
                        continue;
                    }
                    if (ponds[j] == 'B') {
                        betaCount++;
                        continue;
                    }
                    System.out.println("Unexpected token at row " + i + " column" + j);
                }
                if (emptyCount != 0 && (betaCount >= 2 || betaCount >= emptyCount)) {
                    canReach = true;
                }
                else {
                    canReach = false;
                }
                String solution = canReach ? "Y" : "N";

                printWriter.printf("Case #%d: %s\n", i, solution);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
}
