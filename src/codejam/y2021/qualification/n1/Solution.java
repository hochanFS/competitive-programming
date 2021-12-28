package codejam.y2021.qualification.n1;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);

        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int[] arr = new int[N];
                for (int j = 0; j < N; j++) {
                    arr[j] = in.nextInt();
                }
                out.printf("Case #%d: %d\n", i, costOfReversort(arr));
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static long costOfReversort(int[] arr) {
        long cnt = 0L;

        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            int k = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    k = j;
                }
            }
            cnt += k - i + 1;
            reverse(arr, i, k);
        }

        return cnt;
    }

    private static void reverse(int[] arr, int i, int j) {
        while (i < j) {
            int t = arr[i];
            arr[i++] = arr[j];
            arr[j--] = t;
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
