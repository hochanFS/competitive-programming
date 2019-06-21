package hackercup.y2018.qualification;

import java.io.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */
public class SolutionC {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\qualification\\inputC.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2018\\qualification\\outputC.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                String A = br.readLine().trim();
                String solution = "";
                boolean solvable = false;
                if (A.length() <= 3) {
                    solvable = false;
                } else {
                    char[] array = A.toCharArray();
                    char firstCharacter = array[0];
                    for (int j = 1; j < array.length; j++) {
                        if (array[j] == firstCharacter) {
                            int remainingLength = array.length - j;
                            //System.out.println(j + " " + Math.min(j, remainingLength));
                            for (int k = 0; k < Math.min(j + 1, remainingLength); k++) {
                                if (array[k] != array[j + k]) {
                                    System.out.println(j + " " + k);
                                    solvable = true;
                                    solution = A.substring(0, j) + A;
                                    break;
                                }
                            }
                        }
                        if (solvable)
                            break;
                    }
                }
                if (!solvable) {
                    solution = "Impossible";
                }
                printWriter.printf("Case #%d: %s\n", i, solution);
            }
            printWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
