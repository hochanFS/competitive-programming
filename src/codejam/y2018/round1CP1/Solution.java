package codejam.y2018.round1CP1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.io.PrintWriter;

/**
 * @author Hochan Lee
 */

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int L = in.nextInt();

                List<HashSet<Character>> charList = new ArrayList<>();
                List<List<Character>> lookupList = new ArrayList<>();
                for (int j = 0; j < L; j++) {
                    charList.add(new HashSet<>());
                    lookupList.add(new ArrayList<>());
                }

                Set<String> strings = new HashSet<>();
                for (int j = 0; j < N; j++) {
                    String s = in.next();
                    strings.add(s);
                    for (int k = 0; k < L; k++) {
                        if (!charList.get(k).contains(s.charAt(k))) {
                            charList.get(k).add(s.charAt(k));
                            lookupList.get(k).add(s.charAt(k));
                        }
                    }
                }

                long count = 1;
                long[] sizeArray = new long[L];
                for (int j = 0; j < L; j++) {
                    count *= charList.get(j).size();
                    sizeArray[j] = lookupList.get(j).size();
                }


                boolean found = false;

                if (N >= count) {
                    out.printf("Case #%d: - \n", i);
                } else {
                    StringBuilder sb;
                    long trial = 0;

                    while (trial <= Math.min(count, 3000)) {
                        sb = new StringBuilder();
                        long temp = 1;
                        for (int j = 0; j < L; j++) {
                            sb.append(lookupList.get(j).get((int) ((trial / temp) % sizeArray[j])));
                            temp *= sizeArray[j];
                        }
                        if (!strings.contains(sb.toString())) {
                            out.printf("Case #%d: ", i);
                            out.println(sb.toString());
                            found = true;
                            break;
                        }
                        trial++;
                    }
                    if (!found) {
                        out.printf("Case #%d: - \n", i);
                    }
                }
            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
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
            if (st.hasMoreTokens())
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