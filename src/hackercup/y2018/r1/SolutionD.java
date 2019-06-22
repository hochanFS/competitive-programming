package hackercup.y2018.r1;

import java.io.*;
import java.math.BigInteger;

/**
 *
 * TODO: COMPLETE THE SOLUTION
 * @author Hochan Lee (hochan.lee@financescript.com)
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

    private Probability[][] probabilities;
    private Fence[] fences;
    private BigInteger[] zombies;
    private int N;
    private int M;

    SolutionD(int N, int M, BufferedReader br) throws IOException {
        this.N = N;
        this.M = M;
        this.fences = new Fence[N];
        this.zombies = new BigInteger[M];
        this.probabilities = new Probability[M][N];
        for (int i = 0; i < N - 1; i++) {
            String[] bounds = br.readLine().trim().split("\\s+");
            fences[i] = new Fence(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
        }
        for (int i = 0; i < M; i++) {
            String[] infos = br.readLine().trim().split("\\s+");

        }


    }

    public long getSolution() {
        return 1;
    }

    public class Fence {
        private long L; // lower bound
        private long U; // upper bound
        Fence(long L, long U) {
            this.L = L;
            this.U = U;
        }

    }

    public class Probability {
        private BigInteger P;
        private BigInteger Q;
        Probability(long p, long q) {
            this.P = new BigInteger(Long.toString(p));
            this.Q = new BigInteger(Long.toString(q));
            simplify();
        }
        Probability(BigInteger p, BigInteger q) {
            this.P = p;
            this.Q = q;
            simplify();
        }
        private void simplify() {
            BigInteger gcd = P.gcd(Q);
            P = P.divide(gcd);
            Q = Q.divide(gcd);
        }

        public Probability add(Probability p2) {
            BigInteger X = (P.multiply(p2.Q)).add(Q.multiply(p2.P));
            BigInteger Y = (Q.multiply(p2.Q));
            return new Probability(X, Y);
        }

        public Probability multiply(Probability p2) {
            BigInteger X = P.multiply(p2.P);
            BigInteger Y = Q.multiply(p2.Q);
            return new Probability(X, Y);
        }
    }
}
