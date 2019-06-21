package codejam.y2019.round1CP2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.io.PrintWriter;

/**
 * TODO: WRONG ANSWER. CORRECT THE SOLUTION
 * @author Hochan Lee
 */

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            int F = in.nextInt();

            for (int i = 1; i <= T; i++) {
                int[] letterCount = new int[5];
                int[] letters = new int[119];
                boolean[] skip = new boolean[119];
                StringBuilder sb = new StringBuilder();
                if (F == 475) {
                    for (int k = 0; k < 5; k ++) {
                        Arrays.fill(letterCount, 0);
                        for (int j = 0; j < 119; j++) {
                            if (!skip[j]) {
                                out.println(j * 5 + k + 1);
                                out.flush();
                                String next = in.next().trim();
                                letterCount[next.charAt(0) - 65]++;
                                letters[j] = next.charAt(0) - 65;
                            }
                        }
                        if (letterCount[k] == 50) {
                            for (int j = 0; j < 119; j++) {
                                if (letters[j] == k) {
                                    skip[j] = true;
                                }
                            }
                        }
                        if (letterCount[k] == 49) {
                            sb.append(((char) k + 65));
                        }
                    }
                    String x = sb.toString();
                    Set<Character> charSet = new HashSet<>();
                    for (char s : x.toCharArray()) {
                        charSet.add(s);
                    }
                    if (!charSet.contains('A'))
                        sb.append('A');

                    if (!charSet.contains('B'))
                        sb.append('B');

                    if (!charSet.contains('C'))
                        sb.append('C');

                    if (!charSet.contains('D'))
                        sb.append('D');

                    if (!charSet.contains('E'))
                        sb.append('E');

                    out.println(sb.toString());
                    out.flush();
                    int verdict = in.nextInt();
                    if (verdict == -1) {
                        throw new RuntimeException();
                    }
                }
                else {
                    out.println(1);
                    out.flush();
                    out.println("ABCDE");
                    out.flush();
                    int verdict = in.nextInt();
                    if (verdict == -1) {
                        throw new RuntimeException();
                    }
                }
            }
            out.close();

        }
        catch (IOException ie) {
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