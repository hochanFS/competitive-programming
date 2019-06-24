package hackercup.y2017.r1;

import java.io.*;
import java.util.Random;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 * Used to confirm that SolutionA.java runs sufficiently fast for big input.
 */

public class BigSampleWriterA {
    public static final int T = 20;
    public static final int N = 300;
    public static final int MAX_C = 1000000;

    public static void main(String[] args) {
        String output = "src\\hackercup\\y2017\\r1\\bigSampleA.txt";
        try {

            Random random = new Random();
            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(T);
            for (int i = 1; i <= T; i++) {
                printWriter.println("300 300");
                StringBuilder sb = new StringBuilder();
                for (int j = 1; j <= N; j++) {
                    for (int k = 1; k <= N; k++) {
                        sb.append(random.nextInt(MAX_C)).append(' ');
                    }
                    printWriter.println(sb.toString().trim());
                }
            }
            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
}
