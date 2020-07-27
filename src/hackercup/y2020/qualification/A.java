package hackercup.y2020.qualification;


import java.io.*;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class A {
    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\travel_restrictions_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\a_official_out.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                int N = Integer.parseInt(br.readLine().trim());
                char[] ins = br.readLine().trim().toCharArray();
                char[] outs = br.readLine().trim().toCharArray();
                StringBuilder sb = new StringBuilder();
                for (int from = 0; from < N; from++) {
                    char[] possibles = new char[N];
                    Arrays.fill(possibles, 'N');
                    int left = from, right = from;
                    possibles[from] = 'Y';
                    while (outs[left] != 'N' && left >= 1) {
                        --left;
                        possibles[left] = ins[left] == 'Y' ? 'Y': 'N';
                        if (possibles[left] == 'N') break;
                    }
                    while (outs[right] != 'N' && right < N - 1) {
                        ++right;
                        possibles[right] = ins[right] == 'Y' ? 'Y': 'N';
                        if (possibles[right] == 'N') break;
                    }
                    sb.append(new String(possibles)).append('\n');
                }
                printWriter.printf("Case #%d: \n%s", i, sb.toString());
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
}

