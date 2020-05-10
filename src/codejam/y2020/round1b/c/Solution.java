package codejam.y2020.round1b.c;

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
                int R = in.nextInt();
                int S = in.nextInt();
                int[] values = new int[R * S];

                for (int j = 0; j < R * S; j++) {
                    values[j] = j % R;
                }
                List<int[]> solution = solve(values, S, R);
                out.printf("Case #%d: %d\n", i, solution.size());
                for (int[] x: solution)
                    out.printf("%d %d\n", x[0], x[1]);
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static List<int[]> solve(int[] values, int S, int R) {
        List<int[]> solution = new ArrayList<>();
        while (true) {
            int[] next = sorted(values, S, R);
            //System.out.println(next);
            if (next[0] == -1) {
                break;
            }
            int lookFor = next[1];
            for (int i = next[0]; i >= 0; i--) {
                if (values[i] == lookFor) {
                    //System.out.println(lookFor);
                    solution.add(new int[] {i, next[0]});
                    //System.out.println(i + " " + next[0] + " " + Arrays.toString(values));
                    swap(values, i, next[0]);
                    break;
                }
            }
        }

        return solution;
    }

    private static int[] sorted(int[] values, int S, int R) {
        int start = values.length - 1;
        int val = R;
        int count = 0;
        for (int i = start; i >= 1; i--) {
            if (count % S == 0) {
                val--;
            }
            count++;
            if (values[i] != val) {
                return new int[] {i, val};
            }
        }
        return new int[] {-1, -1};
    }

    private static void swap(int[] values, int l, int r) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int i = 0; i <= l; i++) {
            left.add(values[i]);
        }
        for (int i = l + 1; i <= r; i++) {
            right.add(values[i]);
        }
        for (int i = 0; i < r - l; i++) {
            values[i] = right.get(i);
        }
        for (int i = r - l; i <= r; i++) {
            values[i] = left.get(i - r + l);
        }
    }

    private static class Node {
        int value;
        Node next;
        Node (int value) {
            this.value = value;
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
