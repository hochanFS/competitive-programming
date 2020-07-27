package codejam.y2020.round1a.a;

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
                int N = in.nextInt();
                String[] strings = new String[N];
                for (int j = 0; j < N; j++) {
                    strings[j] = in.next();
                }
                String solution = getString(strings);
                out.printf("Case #%d: %s\n", i, solution);

                //out.println(Arrays.toString(strings)); input checked
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static String getString(String[] values) {
        List<String[]> splitString = new ArrayList<>();
        for (String s : values) {
            if (s.endsWith("*"))
                s += "@";
            String[] temp = s.split("\\*");
            if (temp[temp.length - 1].equals("@"))
                temp[temp.length - 1] = "";
            splitString.add(temp);
        }
        String maxLeft = "";
        String maxRight = "";
        StringBuilder sb = new StringBuilder();
        for (String[] strings : splitString) {
            String left = strings[0];
            String right = strings[strings.length - 1];
            if (maxLeft.length() < left.length())
                maxLeft = left;
            if (maxRight.length() < right.length())
                maxRight = right;
            for (int i = 1; i < strings.length - 1; i++) {
                sb.append(strings[i]);
            }
        }

        String candidate = maxLeft + sb.toString() + maxRight;

        if (candidate.length() > 10000)
            return "*";

        for (String[] strings : splitString)
        {
            if (! (candidate.startsWith(strings[0]) &&
                    (strings[strings.length -1].isEmpty() || candidate.endsWith(strings[strings.length - 1]))))
                return "*";
        }
        return candidate;
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
