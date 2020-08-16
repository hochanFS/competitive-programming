package hackercup.y2020.round1;

import java.io.*;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class A3 {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\perimetric_chapter_3_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\r1_c_official_output2.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] line1 = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(line1[0]);
                int K = Integer.parseInt(line1[1]);
                long[] lengths = new long[N];
                long[] widths = new long[N];
                long[] heights = new long[N];
                String[] line2 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    lengths[j] = Long.parseLong(line2[j]);
                String[] line3 = br.readLine().trim().split("\\s+");
                long aL = Long.parseLong(line3[0]), bL = Long.parseLong(line3[1]), cL = Long.parseLong(line3[2]), dL = Long.parseLong(line3[3]);
                for (int j = K; j < N; j++)
                    lengths[j] = (aL * lengths[j - 2] + bL * lengths[j - 1] + cL) % dL + 1;
                String[] line4 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    widths[j] = Long.parseLong(line4[j]);
                String[] line5 = br.readLine().trim().split("\\s+");
                long aw = Long.parseLong(line5[0]), bw = Long.parseLong(line5[1]), cw = Long.parseLong(line5[2]), dw = Long.parseLong(line5[3]);
                for (int j = K; j < N; j++)
                    widths[j] = (aw * widths[j - 2] + bw * widths[j - 1] + cw) % dw + 1;
                String[] line6 = br.readLine().trim().split("\\s+");
                for (int j = 0; j < K; j++)
                    heights[j] = Long.parseLong(line6[j]);
                String[] line7 = br.readLine().trim().split("\\s+");
                long aH = Long.parseLong(line7[0]), bH = Long.parseLong(line7[1]), cH = Long.parseLong(line7[2]), dH = Long.parseLong(line7[3]);
                for (int j = K; j < N; j++)
                    heights[j] = (aH * heights[j - 2] + bH * heights[j - 1] + cH) % dH + 1;
//                if (i == 4) {
//                    System.out.println("Case " + i);
//                    System.out.println("lengths: " + Arrays.toString(lengths));
//                    System.out.println("heights: " + Arrays.toString(heights));
//                    System.out.println("widths: " + Arrays.toString(widths));
//                    printWriter.printf("Case #%d: %d\n", i, solve(N, lengths, heights, widths));
//                    System.out.println("\n\n");
//                }
                printWriter.printf("Case #%d: %d\n", i, solve(N, lengths, heights, widths));

            }
            printWriter.close();

        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static long solve(int n, long[] lengths, long[] heights, long[] widths) {
        long[] parameters = new long[n];
        TreeMap<Long, Point> lefts = new TreeMap<>();
        TreeMap<Long, Point> heightMap = new TreeMap<>();
        lefts.put(lengths[0], new Point(lengths[0], lengths[0] + widths[0], heights[0]));
        parameters[0] = (2 * (widths[0] + heights[0]) + MOD) % MOD;
        long ret = 1L;
        heightMap.put(lengths[0], new Point(lengths[0], lengths[0] + widths[0], heights[0]));

        for (int i = 1; i < n; i++) {
            long pl = lengths[i], pr = pl + widths[i], nl = pl, nr = pr, ph = heights[i];
//            System.out.println("pl = " + pl + ", pr = " + pr + ", h = " + heights[i]);

//            if (i == 7) System.out.println(heightMap);
            long delta = 2 * (widths[i] + heights[i]) + MOD;
//            if (i == 7) System.out.println(delta);

            Long ll = lefts.floorKey(nl), rl = lefts.floorKey(nr);
            if (ll == null) ll = 0L;
            if (rl == null) rl = (long) Integer.MAX_VALUE;
            rl += 1;
            List<Long> deleteList = new ArrayList<>();
            List<Long> yDeleteList = new ArrayList<>();
            List<Point> yAddList = new ArrayList<>();
            for (long key: lefts.subMap(ll, rl).keySet()) {

                Point p = lefts.get(key);
                if (p.r >= pl && p.l <= pr) {
                    nl = Math.min(nl, p.l);
                    nr = Math.max(nr, p.r);
                    delta -= 2 * (Math.min(p.r, pr) - Math.max(p.l, pl));;
                    deleteList.add(key);
                }
            }
//            if (i == 7) System.out.println(delta);

            Long llh = heightMap.floorKey(pl), rlh = heightMap.floorKey(pr);
            if (llh == null) llh = 0L;
            if (rlh == null) rlh = (long) Integer.MAX_VALUE;
            rlh += 1;
            long pre = 0L, pxr = -1;
            for (long key: heightMap.subMap(llh, rlh).keySet()) {
                Point p = heightMap.get(key);
                if (p.r >= pl && p.l <= pr) {
                    if (pxr < p.l) {
                        delta -= pre; pre = 0;
                    }
                    delta -= Math.abs(p.h - pre);
                    pre = p.h;
                    pxr = p.r;
                    yDeleteList.add(key);
                    if (key < pl) yAddList.add(new Point(key, pl, p.h));
                    if (p.r > pr) yAddList.add(new Point(pr, p.r, p.h));
                }
            }
            delta -= pre;
//            if(i == 7) System.out.println(delta);

            for (long delete: deleteList) {
                lefts.remove(delete);
            }

            for (long delete: yDeleteList) {
                heightMap.remove(delete);
            }

            for (Point p: yAddList) {
                heightMap.put(p.l, p);
            }
            lefts.put(nl, new Point(nl, nr, ph));
            heightMap.put(pl, new Point(pl, pr, ph));

            delta = delta < 0 ? delta + MOD * (-delta / MOD + 1) : delta;
            parameters[i] = (parameters[i - 1] + delta) % MOD;
        }

        for (int i = 0; i < n; i++) {
            ret *= parameters[i];
            ret %= MOD;
        }
//        System.out.println("P: " + Arrays.toString(parameters));
        return ret;
    }

    private static class Point implements Comparable<Point> {
        long l, r, h;

        public Point(long l, long r, long h) {
            this.l = l;
            this.r = r;
            this.h = h;
        }

        @Override
        public int compareTo(Point o) {
            if (this.l != o.l) {
                return Long.compare(this.l, o.l);
            }
            return Long.compare(this.r, o.r);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "l=" + l +
                    ", r=" + r +
                    ", h=" + h +
                    '}';
        }
    }
}