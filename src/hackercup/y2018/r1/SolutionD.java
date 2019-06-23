package hackercup.y2018.r1;

import java.io.*;
import java.math.BigInteger;
import java.util.TreeSet;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

/**
 * One key observations is that the probability of having safe yards from 1 to x
 * depends on the following factors:
 *   1. The probability of having safe yards from 1 to x - 1
 *   2. The maximum fence of height h
 *   3. The maximum Zombie height z
 *   4. The distribution of new fence adjacent to yard i
 *   5. The height and presence of zombie at yard i
 *
 * One way to solve this is to use dynamic programming with two arrays for memoization.
 *   > safe[yardPos][maxFenceHeight]    : probability that at least a yard between 1 and yardPos is safe
 *   > unsafe[yardPos][maxZombieHeight] : probability that no yard between 1 and yardPos is safe
 *
 * First, we initialize safe[i][0] as 1 if there is a zombie at yard 0;
 * otherwise, we initialize unsafe[i][1st zombie height] as 1.
 *
 * The probability of safe[yardPos][h] =
 *    safe[yardPos - 1][h] * P(fence <= h)
 *  + unsafe[yardPos - 1][h'] * P(h | h > z >= h' and z_i = 0)
 *
 * Additionally, if a fence of higher than Z appears at i in unsafe state,
 * it is as if we solve a reduced problem from i to N. Thus,
 *    safe[yardPos][0] = unsafe[yardPos - 1][z] * P(h > z)
 *
 * Aggregate probability is also tracked since when safe[yardPos][h] > 0, safe[yardPos][h + x] > 0
 * The probability depends on safe[yardPos][h + x] and P(h < fence < h + x)
 *
 * The probability of unsafe[yardPos][z] =
 *    (safe[yardPos - 1][h] & h < z_lag) * P(fence <= z_lag)
 *  + (unsafe[yardPos - 1][z_lag] * ([1 && z_new >= h or E[z] && z_new < h])
 *
 * Also note that the modular operations are added.
 * For division, I used BigInteger's modInverse method.
 */

public class SolutionD {

    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\r1\\sampleD.txt";
        String output = "src\\hackercup\\y2018\\r1\\sampleOutputD.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] dimension = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(dimension[0]);
                int M = Integer.parseInt(dimension[1]);
                SolutionD solutionD = new SolutionD(N, M, br);
                printWriter.printf("Case #%d: %d\n", i, solutionD.getSolution());
                long x = 1;
                BigInteger b = new BigInteger(Long.toString(x));
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public static final int MOD = (int) 1e9 + 7;
    private int N;
    private int M;
    private int[] lowerBound;
    private int[] upperBound;
    private TreeSet<Integer> zombieLimits; // TreeSet for sorting & duplicate removal
    private int[] zombieHeights;           // array for storage & fast look up
    private int[][] safe;
    private int[][] unsafe;
    private int[] zombieInit;


    SolutionD(int N, int M, BufferedReader br) throws IOException {
        this.N = N;
        this.M = M;
        lowerBound = new int[N];
        upperBound = new int[N];
        zombieLimits = new TreeSet<>();
        zombieInit = new int[N + 1];

        for (int i = 0; i < N - 1; i ++) {
            String[] bounds = br.readLine().trim().split("\\s+");
            lowerBound[i] = Integer.parseInt(bounds[0]);
            upperBound[i] = Integer.parseInt(bounds[1]);
        }
        for (int i = 0; i < M; i++) {
            String[] infos = br.readLine().trim().split("\\s+");
            int height = Integer.parseInt(infos[1]);
            int position = Integer.parseInt(infos[0]);
            zombieLimits.add(height);
            zombieInit[position] = Math.max(zombieInit[position], height);
        }
        zombieHeights = new int[zombieLimits.size() + 1];
        int k = 0;
        for (int x: zombieLimits) {
            zombieHeights[k++] = x;
        }
        zombieHeights[k++] = Integer.MAX_VALUE;

        safe = new int[N + 1][k];
        unsafe = new int[N + 1][k];

    }

    public long getSolution() {
        return 1;
    }


    // Modular Operations

    public static int modularSum(int a, int b, int mod) {
        return ((a % mod) + (b % mod)) % mod;
    }

    public static int modularSubtract(int a, int b, int mod) {
        return a - b > 0 ? (a - b) % mod : (a - b + mod) % mod;
    }

    public static int modularMult(int a, int b, int mod) {
        return (int) ((long) a * b % mod);
    }

    public static int modularDiv(int a, int b, int mod) {
        int inverseMod = BigInteger.valueOf(b).modInverse(BigInteger.valueOf(mod)).intValue();
        return modularMult(a, inverseMod, mod);
    }

}
