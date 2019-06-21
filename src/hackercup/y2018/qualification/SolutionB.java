package hackercup.y2018.qualification;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */
public class SolutionB {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\qualification\\interception.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2018\\qualification\\outputB.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                int N = Integer.parseInt(br.readLine().trim());
                int[] constants = new int[N + 1];
                for (int j = 0; j < N + 1; j++) {
                    constants[j] = Integer.parseInt(br.readLine().trim());
                }
                StringBuilder sb = new StringBuilder();
                int numSol = N % 2 == 0 ? 0 : 1;
                printWriter.printf("Case #%d: %d\n", i, numSol);
                if (numSol == 1)
                    printWriter.println(0.0);
            }
            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

}