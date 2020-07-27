package codejam.y2020.round1c.b;


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
                // int[] dp = new int[26];
                int U = in.nextInt();
                Map<Character, Set<Integer>> dp = new HashMap<>();

                for (int j = 0; j < 10000; j++) {
                    int Q = in.nextInt();
                    char[] M = in.next().toCharArray();
                    solve(dp, Q, M);
                }

                //
                Map<Integer, Character> sanitized = new HashMap<>();

                for (int k = 0; k < 10; k++)
                for (int j = 0; j < 10; j++) {
                    for (Character c: dp.keySet()) {
                        if (dp.get(c).contains(j)) {
//                            System.out.println(dp.get(c));
                            if (dp.get(c).size() == 1)
                                sanitized.put(j, c);
                            else if (sanitized.containsKey(j))
                                dp.get(c).remove(j);
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 10; j++) {
                    sb.append(sanitized.get(j));
                }
                out.printf("Case #%d: %s\n", i, sb.toString());
            }

            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static void solve(Map<Character, Set<Integer>> dp, int q, char[] m) {

        List<Set<Integer>> values = new ArrayList<>();
        int start = 1;
        int end = 9;
        values.add(new HashSet<>());
        if (!dp.containsKey(m[0])) {
            dp.put(m[0], new HashSet<>()); // put the new character
            for (int k = 0; k < 10; k++)
                dp.get(m[0]).add(k);
        }

        for (int i = 1; i < m.length; i++) {
            if (!dp.containsKey(m[i])) {
                dp.put(m[i], new HashSet<>()); // put the new character
                for (int k = 0; k < 10; k++)
                    dp.get(m[i]).add(k);
            }
            start *= 10; // start should match the # of digits of m
            values.add(new HashSet<>());
            end *= 10;
            end += 9;
        }
        end = Math.min(end, q); // get the maximum possible values

        for (int i = start; i <= end; i++) {
            int val = i;
            for (int j = 0; j < m.length; j++) {
                //System.out.println("m.length " + m.length + " m.length - j - 1: " + (m.length - j - 1));
                int digit = val % 10; // extract the digit
                values.get(m.length - j - 1).add(digit);
                val /= 10;
            }
        }

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < 10; j++) {
                if (!values.get(i).contains(j)) {
                    dp.get(m[i]).remove(j);
                }
            }
        }
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
