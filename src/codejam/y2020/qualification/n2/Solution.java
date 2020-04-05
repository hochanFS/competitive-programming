package codejam.y2020.qualification.n2;


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
            for (int i = 1; i <= T; i++) {
                String s = in.next();
                out.printf("Case #%d: %s\n", i, balanceNumericString(s));
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }
    // incorrect need to consider adjacency
    private static String balanceNumericString(String s) {
        StringBuilder sb = new StringBuilder();
        int lag = 0;
        int j = 9;
        for (int i = 0; i < s.length(); i++) {
            int x = s.charAt(i) - '0';
            while (x > lag) {
                sb.append('(');
                lag++;
            }
            while (x < lag) {
                sb.append(')');
                lag--;
            }
            sb.append(x);
            lag = x;
        }
        int x = 0;
        while (x < lag) {
            sb.append(')');
            lag--;
        }
        return sb.toString();
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