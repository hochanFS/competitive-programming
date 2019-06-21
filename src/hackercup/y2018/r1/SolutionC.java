package hackercup.y2018.r1;

import java.io.*;
import java.util.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */


public class SolutionC {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\r1\\sampleC.txt";
        String output = "src\\hackercup\\y2018\\r1\\sampleOutputC.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] dimension = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(dimension[0]);
                int M = Integer.parseInt(dimension[1]);
                SolutionC solutionC = new SolutionC(N, M, br);
                printWriter.printf("Case #%d: %f\n", i, solutionC.getSolution() / 2.0);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    //public static double MAX_ERROR = 1e-7;
    public static long MAX_HEIGHT = 1000000 * 2;
    public static long MIN_HEIGHT = 0;
    private long[] heights;
    private Parkourist[] parkourists;
    private long[] up;
    private long[] dn;
    private int first;
    private int last;

    SolutionC(int N, int M, BufferedReader br) throws IOException {
        this.heights = new long[N + 1];
        this.parkourists = new Parkourist[M]; // for validation
        this.up = new long[N + 1]; // 1 is between 1 and 2
        this.dn = new long[N + 1];

        String[] heightLine = br.readLine().trim().split("\\s+");
        heights[1] = Long.parseLong(heightLine[0]);
        heights[2] = Long.parseLong(heightLine[1]);
        long W = Long.parseLong(heightLine[2]);
        long X = Long.parseLong(heightLine[3]);
        long Y = Long.parseLong(heightLine[4]);
        long Z = Long.parseLong(heightLine[5]);
        for (int j = 3; j <= N; j++) {
            heights[j] = (W * heights[j - 2] + X * heights[j - 1] + Y) % Z;
        }
        Arrays.fill(up, Long.MAX_VALUE);
        Arrays.fill(dn, Long.MAX_VALUE);
        for (int j = 0; j <= N; j++) {
            heights[j] *= 2;
        }

        first = N;
        last = 0;
        for (int j = 0; j < M; j++) {
            String[] infos = br.readLine().trim().split("\\s+");
            Parkourist p = new Parkourist(infos[0], infos[1], infos[2], infos[3]);
            parkourists[j] = p;
            if (p.A < p.B) {
                for (int k = p.A; k < p.B; k++) {
                    up[k] = Math.min(up[k], p.U);
                    dn[k] = Math.min(dn[k], p.D);
                    first = Math.min(first, p.A);
                    last = Math.max(last, p.B);
                }
            }
            else {
                for (int k = p.B; k < p.A; k++) {
                    up[k] = Math.min(up[k], p.D);
                    dn[k] = Math.min(dn[k], p.U);
                    first = Math.min(first, p.B);
                    last = Math.max(last, p.A);
                }
            }
        }

        for (int j = first; j <= last; j++) {
            up[j] *= 2;
            dn[j] *= 2;
        }
    }

    public long getSolution() {
        long high = MAX_HEIGHT;
        long low = MIN_HEIGHT;
        long mid;
        while (low < high) {
            mid = (low + high) / 2;
            if (isValid(mid)) high = mid;
            else low = mid + 1;
        }
        return low;
    }

    private boolean isValid(double x) {
        double lowerBound = Math.max(heights[first] - x, 0);
        double upperBound = heights[first] + x;
        for (int i = first + 1; i <= last; i++) {
            lowerBound = Math.max(Math.max(lowerBound - dn[i - 1], heights[i] - x),0);
            upperBound = Math.min(upperBound + up[i - 1], heights[i] + x);
            if (lowerBound > upperBound)
                return false;
        }
        return true;
    }

    private static class Parkourist {
        int A;
        int B;
        long U;
        long D;
        double maxUp;
        double maxDown;
        Parkourist(String a, String b, String u, String d) {
            this.A = Integer.parseInt(a);
            this.B = Integer.parseInt(b);
            this.U = Long.parseLong(u);
            this.D = Long.parseLong(d);
            this.maxUp = 0;
            this.maxDown = 0;
        }

    }

}