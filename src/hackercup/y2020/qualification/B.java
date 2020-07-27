package hackercup.y2020.qualification;

import java.io.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class B {
    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\alchemy_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\b_official_out.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                int N = Integer.parseInt(br.readLine().trim());
                char[] fragments = br.readLine().trim().toCharArray();
                int numA = 0, numB = 0;
                for (int index = 0; index < N; index++) {
                    if (fragments[index] == 'A') numA++;
                    else numB++;
                }
                String solution = Math.abs(numA - numB) == 1? "Y": "N";
                printWriter.printf("Case #%d: %s\n", i, solution);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
}

