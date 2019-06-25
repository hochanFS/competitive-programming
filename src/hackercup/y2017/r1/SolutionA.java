package hackercup.y2017.r1;


import java.io.*;
import java.util.Arrays;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */
public class SolutionA {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2017\\r1\\inputA.in";
        String output = "src\\hackercup\\y2017\\r1\\outputA.out";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                String[] integers = br.readLine().split("\\s+");
                int N = Integer.parseInt(integers[0]);
                int M = Integer.parseInt(integers[1]);
                SolutionA solutionA = new SolutionA(N, M, br);
                printWriter.printf("Case #%d: %d\n", i, solutionA.getSolution());
                System.out.printf("Case #%d done!\n", i);
            }
            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public static long MAX_COST = 1000000L * 300 + 300 * 300 + 1;

    private int N;
    long[][] costs;
    long[][] cumulativeCosts;
    long[][] memo; // memo[days][itemsBought] records the minimum cost of buying #itemsBought up to #days

    public SolutionA(int N, int M, BufferedReader br) throws IOException {
        this.costs = new long[N + 1][M + 1];
        this.cumulativeCosts = new long[N + 1][M + 1];
        this.memo = new long[N + 1][N + 1];
        //System.out.println("Cumulative Costs");
        this.N = N;
        for (int i = 1; i <= N; i++) {
            String[] sales = br.readLine().trim().split("\\s+");
            for (int j = 1; j <= M; j++) {
                costs[i][j] = Long.parseLong(sales[j - 1]);
            }
            Arrays.sort(costs[i]);
            for (int j = 1; j <= M; j++) {
                cumulativeCosts[i][j] = cumulativeCosts[i][j - 1] + costs[i][j];
            }
            for (int j = 1; j <= M; j++) {
                cumulativeCosts[i][j] += j * j; // add tax cost
            }
        }

        Arrays.fill(memo[0], MAX_COST);
        memo[0][0] = 0;
        for (int i = 1; i <= N; i++) {
            int maxFill = Math.min(i * M, N);
            Arrays.fill(memo[i], MAX_COST);
            memo[i][0] = 0;
            for (int j = 0; j <= maxFill; j++) {

                for (int k = 0; k <= Math.min(j, M); k++) {
                    memo[i][j] = Math.min(memo[i][j], memo[i - 1][j - k] + cumulativeCosts[i][k]);
                }
            }
        }
    }

    public long getSolution() {
        return memo[N][N];
    }
}
