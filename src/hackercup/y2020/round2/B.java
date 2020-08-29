package hackercup.y2020.round2;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class B {

    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\elimination_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\r2_b_official_output.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] line = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(line[0]);
                double P = Double.parseDouble(line[1]);
                printWriter.printf("Case #%d:\n%s", i, solve(N, P));
            }
            printWriter.close();

        } catch(IOException ie) {
            ie.printStackTrace();
        }
//        double[] expected = new double[3];
//        double[][] dp = new double[3][3];
//        boolean[][] filled = new boolean[3][3];
//        System.out.println(calculate(filled, dp, 1, 1, 1.0));
//        System.out.println("dp" + calculate(filled, dp, 0, 1, 1.0));
    }

    private static String solve(int n, double p) {
        StringBuilder sb = new StringBuilder();

        double[] expected = new double[n];
        double[][] dp = new double[n][n];
        boolean[][] filled = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            expected[i] = calculate(filled, dp, i, n - 1 - i, p);
        }

        // print
        for (int i = 0; i < n; i++) {
            DecimalFormat df = new DecimalFormat("#.00000000");
            sb.append(df.format(expected[i])).append('\n');
        }
        return sb.toString();
    }

    private static double calculate(boolean[][] filled, double[][] dp, int lt, int gt, double p) {
        if (filled[lt][gt]) return dp[lt][gt];
        if (lt + gt == 1) return 1;

        int matchBetweenWinner = gt < 2 ? 0 : gt * (gt - 1) / 2;
        int matchBetweenLoser = lt < 2 ? 0 : lt * (lt - 1) / 2;
        int matchMixedWithoutMe = lt < 1 || gt < 1? 0 : lt * gt;
//        System.out.println(matchBetweenWinner);
//        System.out.println(matchBetweenLoser);
//        System.out.println(matchMixedWithoutMe);
//        System.out.println(lt);

        double ret = 1;
        int k = lt + gt + 1;
        int matches = k * (k - 1) / 2;

        double probLoserDecrease = (((double) matchBetweenLoser) + (double) (lt + matchMixedWithoutMe) * p) / (double) matches;
//        System.out.println(probLoserDecrease);
        double probWinnerDecrease = (((double) matchBetweenWinner) + (double) (gt + matchMixedWithoutMe) * (1 - p)) / (double) matches;

        if (lt >= 1) ret += probLoserDecrease * calculate(filled, dp, lt - 1, gt, p);
        if (gt >= 1) ret += probWinnerDecrease * calculate(filled, dp, lt, gt - 1, p);

        dp[lt][gt] = ret;
        filled[lt][gt] = true;
        return ret;
    }

    private static double sum(List<Double> list) {
        while (list.size() > 1) {
            List<Double> newList = new ArrayList<>();
            int s = 0, n = list.size() - 1;
            while (s <= n) {
                if (s != n) newList.add(list.get(s++) + list.get(n--));
                else newList.add(list.get(s++));
            }
            list = newList;
        }
        return list.get(0);
    }
}