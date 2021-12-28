package hackercup.y2019.qualification;

import java.io.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 * Since A can jump over B and land immediately to its right and A can jump only to
 * the right, A can move up to 2 * B times.
 * Thus, if emptyCount > betaCount, there is no way for A to reach the rightmost lilypad.
 * In other cases, (betaCount >= emptyCount) A can, as long as
 * there is at least one empty lilypad so B and A can move as needed.
 * There is no need to consider the case where A is already at the rightmost
 */

public class SolutionA {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2019\\qualification\\inputA.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2019\\qualification\\solutionA.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                char[] ponds = br.readLine().trim().toCharArray(); // read the pond state for each day
                int N = ponds.length;
                boolean canReach = false; // tracks whether reaching to the right is at all possible
                int betaCount = 0;
                int emptyCount = 0;
                for (int j = 1; j < N; j++) { // constraint: N >= 2 for all cases
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

                if (betaCount >= emptyCount && emptyCount >= 1)
                    canReach = true;
                String sol = canReach ? "Y" : "N";

                printWriter.printf("Case #%d: %s\n", i, sol);
            }

            printWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
