package codejam.y2018.round1BP1;

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
    private int N;
    private double N_D;
    private int surveyed;
    private List<Integer> lc;
    private Map<Integer, Integer> map;
    private int minEQ;

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int L = in.nextInt();
                ArrayList<Integer> languageChoices = new ArrayList<>();
                int count = 0;

                for (int j = 0; j < L; j++) {
                    int k = in.nextInt();
                    languageChoices.add(k);
                    count += k;
                }

                Solution sol = new Solution(N, count, languageChoices);

                Collections.sort(languageChoices, Collections.reverseOrder());
                out.printf("Case #%d: %d\n", i, sol.solve()); //TODO: 1 ->

            }

            out.close();


        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public Solution(int N, int k, ArrayList<Integer> lc) {
        this.N = N;
        this.N_D = (double) N;
        surveyed = k;
        this.lc = lc;
        this.map = new HashMap<>();
        minEQ = getMinZeroEqual();

    }

    @SuppressWarnings("unchecked")
    public long solve() {
        if (100 % N == 0)
            return 100;

        long incrementByOne = Math.round(1.0 / N_D * 100.0);
        long count = 0;

        if (isOptimized(1)) {

            for (int i = 0; i < lc.size(); i++) {
                count += Math.round(lc.get(i) / N_D * 100.0);
            }
            return count + incrementByOne * (N - surveyed);
        }

        int pos = 0;

        lc.add(0);
        Comparator<Integer> byLength = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(requiredForOptimization(o1), requiredForOptimization(o2));
            }
        };

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(byLength);
        pq.addAll(lc);
        Integer temp;

        while (true) {
            temp = pq.peek();
            int k = requiredForOptimization(temp);
            if (k <= N - surveyed) {
                pq.poll();
                if (temp == 0) {
                    pq.add(0);
                }
                pq.add(temp + k);
                surveyed += k;
            } else {
                break;
            }
        }

        while (minEQ + surveyed <= N) {
            surveyed += minEQ;
            pq.add(minEQ);
        }

        pq.add(N - surveyed);

        lc = new ArrayList<>(pq);

        long sol = 0;
        for (int i = 0; i < lc.size(); i++) {
            sol += Math.round(lc.get(i) / N_D * 100.0);
        }

        return sol;
    }

    private int getMinZeroEqual() {
        int p = 1;
        while (p < N) {
            double temp = (p / N_D * 100.0);
            if (Math.round(temp) == temp) {
                return p;
            }
            ++p;
        }
        return N + 1;
    }

    private int requiredForOptimization(int input2) {

        if (map.containsKey(input2))
            return map.get(input2);

        double temp = (double) input2 / N_D * 100.0;
        if (Math.round(temp) > temp) {
            map.put(input2, N + 1);
            return N + 1;
        }

        int p = 0;
        int equal = Integer.MAX_VALUE;
        int exceed = Integer.MAX_VALUE;

        int input = input2;
        int add = 0;
        if (input2 == 0) {
            p = 1;
            while (p < N && exceed > N) {
                temp = (p / N_D * 100.0);
                if (Math.round(temp) == temp && equal > N) {
                    equal = p;
                }
                if (Math.round(temp) > temp) {
                    map.put(0, p);
                    return p;
                }
                ++p;
            }
            map.put(0, equal);
            return equal;
        }

        if (Math.round(temp) < temp) {
            while (input2 + p < N && exceed > N) {
                temp = ((input2 + p) / N_D * 100.0);
                if (Math.round(temp) >= temp) {
                    map.put(input2, p);
                    return p;
                }
                ++p;
            }
        }

        while (input2 + p < N && exceed > N) {
            temp = ((input2 + p) / N_D * 100.0);
            if (Math.round(temp) > temp) {
                break;
            }
            ++p;
        }
        map.put(input2, p + add);
        return p + add;
    }

    private boolean isOptimized(int i) {
        double temp = (double) i / N_D * 100.0;
        return Math.round(temp) > temp;
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