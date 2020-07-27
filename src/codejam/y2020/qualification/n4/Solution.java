package codejam.y2020.qualification.n4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Solution {

    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            int B = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int[] bitArray = new int[B];
                Arrays.fill(bitArray, -1);
                int start = 0;
                int end = B - 1;

                boolean incrementStart = true;
                boolean complementDetermined = true;
                boolean reverseDetermined = true;
                int complementKey1, reverseKey1;
                complementKey1 = reverseKey1 = -1;
                int queryCount = 1;
                while (end >= start) {

                    // put new values
                    if (reverseDetermined && complementDetermined) {
                        // track the number of queries
                        queryCount++;

                        if (incrementStart) {
                            out.println(start + 1);
                            out.flush();
                            bitArray[start++] = in.nextInt();
                            incrementStart = false;
                        }
                        else {
                            out.println(end + 1);
                            out.flush();
                            bitArray[end--] = in.nextInt();
                            incrementStart = true;

                            // find the keys to query to find what shocks were applied
                            if (complementKey1 == -1 && bitArray[end + 1] == bitArray[start - 1]) {
                                complementKey1 = start - 1;
                            }
                            if (reverseKey1 == -1 && bitArray[end + 1] != bitArray[start - 1]) {
                                reverseKey1 = end + 1;
                            }
                        }
                    } else {

                        if (!complementDetermined) {
                            // track the number of queries
                            queryCount++;
                            out.println(complementKey1 + 1);
                            out.flush();
                            int val = in.nextInt();
                            if (val != bitArray[complementKey1])
                                applyComplement(bitArray, start - 1, end + 1);
                            complementDetermined = true;
                        }

                        if (!reverseDetermined) {
                            // track the number of queries
                            queryCount ++;
                            out.println(reverseKey1 + 1);
                            out.flush();
                            int val = in.nextInt();
                            if (val != bitArray[reverseKey1]) {
                                applyReverse(bitArray, end + 1);
                            }
                            reverseDetermined = true;
                        }

                        if (start + 1 > bitArray.length - end) {
                            start--;
                        }
                    }

                    if (queryCount % 10 == 1) {
                        if (reverseKey1 != -1)
                            reverseDetermined = false;
                        if (complementKey1 != -1)
                            complementDetermined = false;
                    }
                }
                out.println(getBitValues(bitArray));
                out.flush();

                String verdict = in.next();
                if ("N".equals(verdict))
                    throw new RuntimeException("Wrong Answer");
            }
            out.close();
        }
        catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    private static void applyComplement(int[] bitArray, int start, int end) {
        for (int i = 0; i <= start; i++) {
            bitArray[i] = bitArray[i] == 0 ? 1 : 0;
        }
        for (int i = end; i < bitArray.length; i++) {
            bitArray[i] = bitArray[i] == 0 ? 1 : 0;
        }
    }

    private static void applyReverse(int[] bitArray, int end) {
        int temp;
        for (int i = end; i < bitArray.length; i++) {
            temp = bitArray[i];
            bitArray[i] = bitArray[bitArray.length - i - 1];
            bitArray[bitArray.length - i - 1] = temp;
        }
    }

    private static String getBitValues(int[] bitArray) {
        StringBuilder sb = new StringBuilder();
        for (int value : bitArray) {
            sb.append(value);
        }
        return sb.toString();
    }

    private static int isFilled(int[] bitArray) {
        for (int i = 0; i < bitArray.length; i++) {
            if (bitArray[i] == -1) return i;
        }
        return -1;
    }


    //@
    static class In {
        BufferedReader br;
        StringTokenizer st;

        public In(InputStream i) {
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