package codeforces.round621d2;

import java.io.*;
import java.util.StringTokenizer;

public class SolutionA {

    public static void main(String[] args) {


        try {
            FastScanner in = new FastScanner(System.in);
            PrintWriter out = new PrintWriter(System.out);

            int T = in.nextInt();
            for (int i = 0; i < T; i++) {
                int n = in.nextInt();
                int d = in.nextInt();
                int[] hays = new int[n];
                for (int j = 0; j < n; j++) {
                    hays[j] = in.nextInt();
                }
                out.println(getMaxFirstHay(hays, d));
            }
            out.close();

        } catch(Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    public static int getMaxFirstHay(int[] hays, int d) {
        if (hays.length == 1)
            return hays[0];
        int startingHay = hays[0];
        int nextAvailableHaysIndex = findNextAvailableIndex(hays, 1);
        while (d > 0 && nextAvailableHaysIndex < hays.length) {
            if (hays[nextAvailableHaysIndex] == 0)
                nextAvailableHaysIndex = findNextAvailableIndex(hays, nextAvailableHaysIndex);
            if (nextAvailableHaysIndex >= hays.length)
                break;
            if (d >= nextAvailableHaysIndex) {
                d -= nextAvailableHaysIndex;
                hays[nextAvailableHaysIndex]--;
                startingHay++;
            } else {
                break;
            }
        }
        return startingHay;
    }

    private static int findNextAvailableIndex(int[] hays, int start) {
        int index = start;
        while (index < hays.length && hays[index] <= 0) {
            index++;
        }
        return index;
    }

    /**
     * FastScanner class helps me write input/output code very quickly.
     * I would be able to skip writing the type conversion code such as (e.g. Integer.parseInt(x)).
     * This is adopted from another Code Jam competitor from 2018 solution
     * Credit goes to: electronicsaddict
     */

    //@
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(InputStream i) {
            br = new BufferedReader(new InputStreamReader(i));
            st = new StringTokenizer("");
        }

        public String next() throws IOException {
            if(st.hasMoreTokens())
                return st.nextToken();
            else
                st = new StringTokenizer(br.readLine());
            return next();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        //#
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
        //$
    }

}
