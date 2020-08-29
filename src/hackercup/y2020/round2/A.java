package hackercup.y2020.round2;

import java.io.*;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class A {

    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\capastaty_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\r2_a_official_output.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] line1 = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(line1[0]);
                int K = Integer.parseInt(line1[1]);
                long[] sections = new long[N];
                long[] minimums = new long[N];
                long[] maximums = new long[N];
                String[] line2 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    sections[j] = Long.parseLong(line2[j]);
                String[] line3 = br.readLine().trim().split("\\s+");
                long a_s = Long.parseLong(line3[0]), b_s = Long.parseLong(line3[1]), c_s = Long.parseLong(line3[2]), d_s = Long.parseLong(line3[3]);
                for (int j = K; j < N; j++)
                    sections[j] = (a_s * sections[j - 2] + b_s * sections[j - 1] + c_s) % d_s;
                String[] line4 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    minimums[j] = Long.parseLong(line4[j]);
                String[] line5 = br.readLine().trim().split("\\s+");
                long aw = Long.parseLong(line5[0]), bw = Long.parseLong(line5[1]), cw = Long.parseLong(line5[2]), dw = Long.parseLong(line5[3]);
                for (int j = K; j < N; j++)
                    minimums[j] = (aw * minimums[j - 2] + bw * minimums[j - 1] + cw) % dw;
                String[] line6 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    maximums[j] = Long.parseLong(line6[j]);
                String[] line7 = br.readLine().trim().split("\\s+");
                long aH = Long.parseLong(line7[0]), bH = Long.parseLong(line7[1]), cH = Long.parseLong(line7[2]), dH = Long.parseLong(line7[3]);
                for (int j = K; j < N; j++)
                    maximums[j] = (aH * maximums[j - 2] + bH * maximums[j - 1] + cH) % dH;

                printWriter.printf("Case #%d: %d\n", i, solve(N, sections, minimums, maximums));
//                System.out.println("\n\n");

            }
            printWriter.close();

        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static long solve(int n, long[] sections, long[] minimums, long[] maximums) {
        for (int i = 0; i < n; i ++) {
            maximums[i] += minimums[i];
        }
        long section_sum = 0L, min_sum = 0L, max_sum = 0L, section_minus_max = 0L, section_minus_min = 0L;
        for (int i = 0; i < n; i ++) {
            section_sum += sections[i];
            min_sum += minimums[i];
            max_sum += maximums[i];
            section_minus_max += Math.max(sections[i] - maximums[i], 0L);
            section_minus_min += Math.max(-(sections[i] - minimums[i]), 0L);
        }
        if (section_sum > max_sum || section_sum < min_sum) return -1L;

        return Math.max(section_minus_max, section_minus_min);
    }
}