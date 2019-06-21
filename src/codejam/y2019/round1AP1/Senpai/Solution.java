package codejam.y2019.round1AP1.Senpai;

import java.io.*;
import java.util.*;

public class Solution {
    FastScanner in;
    PrintWriter out;

    class Cell {
        int x, y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Cell(" + x + ", " + y + ")";
        }
    }

    Random rnd = new Random(1233);

    final int MAX = 1000;

    int go;

    boolean ok(List<Cell> cur, int pos) {
        System.out.println(cur);
        if (go++ > MAX) {
            return false;
        }
        if (pos == cur.size()) {
            return true;
        }
        List<Cell> check = cur.subList(pos, cur.size());
        for (Cell c : check) {
            int curPos = pos;
            while (cur.get(curPos) != c) {
                curPos++;
            }
            cur.set(curPos, cur.get(pos));
            cur.set(pos, c);
            if (pos == 0 || ok(c, cur.get(pos - 1))) {
                if (ok(cur, pos + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean ok(Cell a, Cell b) {
        if (a.x == b.x) {
            return false;
        }
        if (a.y == b.y) {
            return false;
        }
        if (Math.abs(a.x - b.x) == Math.abs(a.y - b.y)) {
            return false;
        }
        return true;
    }

    List<Cell> findSol(int n, int m) {
        List<Cell> all = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                all.add(new Cell(i, j));
            }
        }
        Collections.shuffle(all, rnd);
        if (n > 4 || m > 4) {
            go = 0;
            while (!ok(all, 0)) {
                go = 0;
            }
            return all;
        }
        if (ok(all, 0)) {
            return all;
        }
        return null;
    }

    void solve() {
        int tc = in.nextInt();
        for (int t = 0; t < tc; t++) {
            int n = in.nextInt();
            int m = in.nextInt();
            List<Cell> r = findSol(n, m);
            out.println("Case #" + (t + 1) + ": " + (r == null ? "IMPOSSIBLE" : "POSSIBLE"));
            if (r != null) {
                for (Cell c : r) {
                    out.println(((c.x + 1) + " " + (c.y + 1)));
                }
            }
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("Solution.in"));
            out = new PrintWriter(new File("Solution.out"));

            solve();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void runIO() {

        in = new FastScanner(System.in);
        out = new PrintWriter(System.out);

        solve();

        out.close();
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return null;
                st = new StringTokenizer(s);
            }
            return st.nextToken();
        }

        boolean hasMoreTokens() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return false;
                st = new StringTokenizer(s);
            }
            return true;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) {
        new Solution().runIO();
    }
}