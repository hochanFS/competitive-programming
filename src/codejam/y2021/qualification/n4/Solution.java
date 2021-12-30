package codejam.y2021.qualification.n4;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);

        try {
            int T = in.nextInt();
            int N = in.nextInt();
            int Q = in.nextInt();

            for (int i = 1; i <= T; i++) {
                int[] values = new int[]{0, 1};

                for (int j = 2; j < N; j++) {

                    int s = 0, e = values.length + 1;

                    while (s + 1 < e) {
                        int m1 = (s + s + e) / 3, m2 = (s + e + e) / 3;
                        if (m1 == 0) m1++;
                        while(m2 <= m1) m2++;

                        int res = ask(j, values[m1 - 1], values[m2 - 1], out, in);
                        if (res == j) {
                            s = Math.max(s, m1);
                            e = Math.min(e, m2);
                        } else if (res == values[m1 - 1]) {
                            e = Math.min(e, m1);
                        } else if (res == values[m2 - 1]) {
                            s = Math.max(m2, s);
                        }
                    }

                    values = insert(values, s, j);
                }
                for (int j = 0; j < N - 1; j++)
                    out.printf("%d ", values[j] + 1);
                out.printf("%d\n", values[N - 1] + 1);
                out.flush();

                int result = in.nextInt();
                if (result < 0)
                    throw new RuntimeException();
            }

            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static int ask(int i, int j, int k, PrintWriter out, In in) throws IOException {
        out.printf("%d %d %d\n", i + 1, j + 1, k + 1);
        out.flush();
        return in.nextInt() - 1;
    }

    private static int[] insert(int[] arr, int idx, int val) {
        int[] ret = new int[arr.length + 1];
        if (idx == arr.length) {
            System.arraycopy(arr,0,ret,0,arr.length);
            ret[arr.length] = val;
            return ret;
        }
        int i = 0;
        for (int value : arr) {
            if (i == idx) ret[i++] = val;
            ret[i++] = value;
        }

        return ret;
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
