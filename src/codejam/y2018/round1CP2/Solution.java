package codejam.y2018.round1CP2;

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
                if (N < 0) {
                    throw new RuntimeException("Failed reading N");
                }

                Demand[] demands = new Demand[N];
                for (int j = 0; j < N; j++) {
                    demands[j] = new Demand(j, N);
                }

                for (int j = 0; j < N; j++) {
                    int numLikes = in.nextInt();
                    if (numLikes < 0)
                        throw new RuntimeException("Failed reading numLikes");

                    if (numLikes == 0) {
                        out.println(-1);
                        out.flush();
                        continue;
                    }

                    List<Demand> wants = new ArrayList<>();
                    for (int k = 0; k < numLikes; k++) {
                        int like = in.nextInt();
                        demands[like].increment();
                        wants.add(demands[like]);
                    }
                    Collections.shuffle(wants);

                    PriorityQueue<Demand> pq = new PriorityQueue<>(wants);

                    boolean sold = false;
                    while (!pq.isEmpty()) {
                        Demand d = pq.poll();
                        if (!d.sold) {
                            out.println(d.id);
                            out.flush();
                            d.sell();
                            sold = true;
                            break;
                        }
                    }

                    if (!sold) {
                        out.println(-1);
                        out.flush();
                    }
                }

            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static class Demand implements Comparable<Demand> {
        int id, demand, trials;
        int reflectionPoint;
        boolean sold;

        Demand(int id, int N) {
            this.id = id;
            this.demand = 0;
            this.sold = false;
            this.trials = 0;
            this.reflectionPoint = N / 11;
        }

        public void increment() {
            if (!sold)
                demand++;
        }

        public void sell() {
            this.sold = true;
        }

        public void update() {
            this.trials++;
        }

        @Override
        public int compareTo(Demand o) {
            if (this.sold != o.sold) {
                return this.sold ? 1 : -1;
            }
            if (this.demand >= this.reflectionPoint || o.demand >= o.reflectionPoint)
                return Integer.compare(o.demand, this.demand);
            if (this.demand != o.demand)
                return Integer.compare(this.demand, o.demand);
            return 0;
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
