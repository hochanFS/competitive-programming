package codejam.y2021.qualification.n3;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);

        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt(), C = in.nextInt();
                if (C > (N + 1) * N / 2 - 1 || C < N - 1) out.printf("Case #%d: IMPOSSIBLE\n", i);
                else {
                    out.printf("Case #%d: %s\n", i, toSolutionString(solve(N, C)));
                }
            }

            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static int[] solve(int N, int C) {
        int[] ret = new int[N];
        for (int i = 1; i <= N; i++)
            ret[i - 1] = i;

        int d = C - (N - 1);

        int pivot = 1;
        while (d > 0) {
            int subtract = Math.min(d, pivot);
            reverse(ret,  N - pivot - 1,N - pivot + subtract - 1);
            pivot++;
            d -= subtract;
        }

        return ret;
    }

    private static void reverse(int[] arr, int i, int j) {
        while (i < j) {
            int t = arr[i];
            arr[i++] = arr[j];
            arr[j--] = t;
        }
    }

    private static String toSolutionString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i]).append(' ');
        }
        sb.append(arr[arr.length - 1]);
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