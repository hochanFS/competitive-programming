package codejam.y2021.qualification.n4;

import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);

        try {
            int T = in.nextInt();
            int N = in.nextInt();
            int Q = in.nextInt();
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= N; i++) {
                list.add(i);
            }

            for (int i = 1; i <= T; i++) {
//                List<Integer> values = new ArrayList<>();
                int[] values = new int[3];
                int idx = ask(1, 2, 3, out, in);
                if (idx == 1) values = new int[] {2, 1, 3};
                else if (idx == 2) values = new int[] {1, 2, 3};
                else values = new int[] {1, 3, 2};


                for (int j = 4; j <= N; j++) {

                    int l = -1, r = values.length;

                    int m1 = (r - l) / 3 + l, m2 = ((r - l) / 3) * 2 + l;
                    if ((r - l) % 3 == 2)
                        m2 += 1;
                    int a1 = values[m1], a2 = values[m2];
                    while (r - l > 1) {
                        int q = ask(a1, a2, j, out, in);
                        if (q == a1) r = m1;
                        else if (q == a2) l = m2;
                        else {
                            l = m1;
                            r = m2;
                        }

                        m1 = (r - l) / 3 + l;
                        m2 = ((r - l) / 3) * 2 + l;

                        if ((r - l) % 3 == 2)
                            m2++;

                        if (r - l <= 1) break;

                        if (r - l == 2) {
                            if (l > -1) {
                                m1 = r - 2;
                                m2 = r - 1;
                                l = r - 3;
                            } else {
                                m1 = l + 1;
                                m2 = l + 2;
                                r = l + 3;
                            }
                        }

                        a1 = values[m1];
                        a2 = values[m2];
                    }
                    values = insert(values, l + 1, j);
                }
                for (int j = 0; j < N - 1; j++)
                    out.printf("%d ", values[j]);
                out.printf("%d\n", values[N - 1]);
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
        out.printf("%d %d %d\n", i, j, k);
        out.flush();
        return in.nextInt();
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
