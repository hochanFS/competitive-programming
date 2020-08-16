package hackercup.y2020.round1;

import java.io.*;
import java.util.*;

/**
 * @ author Hochan Lee (nyhochan.lee@gmail.com)
 */

public class A1 {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        String fileName = "C:\\hackercup\\input\\perimetric_chapter_1_input.txt"; //renamed
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fileWriter = new FileWriter("C:\\hackercup\\output\\r1_a_official_out.txt"); //renamed
            PrintWriter printWriter = new PrintWriter(fileWriter);

            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                String[] line1 = br.readLine().trim().split("\\s+");
                int N = Integer.parseInt(line1[0]);
                int K = Integer.parseInt(line1[1]);
                int W = Integer.parseInt(line1[2]);
                long[] lengths = new long[N];
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
                    heights[j] = Long.parseLong(line4[j]);
                String[] line5 = br.readLine().trim().split("\\s+");
                long aH = Long.parseLong(line5[0]), bH = Long.parseLong(line5[1]), cH = Long.parseLong(line5[2]), dH = Long.parseLong(line5[3]);
                for (int j = K; j < N; j++)
                    heights[j] = (aH * heights[j - 2] + bH * heights[j - 1] + cH) % dH + 1;
                printWriter.printf("Case #%d: %d\n", i, solve(N, W, lengths, heights));
//                System.out.println("BRUTEFORCE SOL: " + Arrays.toString(bruteForce(N, W, lengths, heights)));
//                if (i == 6) {
//                    System.out.println("lengths: " + Arrays.toString(lengths));
//                    System.out.println("heights: " + Arrays.toString(heights));
//                    printWriter.printf("Case #%d: %d\n", i, solve(N, W, lengths, heights));
//                }
            }
            printWriter.close();

        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static long solve(int n, int w, long[] lengths, long[] heights) {
//        System.out.println("width = " + w);
        long ret = 1L;
        long[] parameters = new long[n];
        int[] right = new int[n];

        parameters[0] = 2 * w + 2 * heights[0];
        right[0] = 0;

        for (int i = 1; i < n; i++) {
            // step1. find the relevant end point
            int j = i;
            while (j - 1 >= 0 && lengths[j - 1] + w >= lengths[i])
                j--;

            // step2. calculate the parameters from j to i using Line Sweep algorithm (which should be feasible given w <= 20)
            TreeMap<Long, Long> collectiveHeights = new TreeMap<>();
            collectiveHeights.put(0L, 1L);

            // step 2 - 1. define point
            List<Point> adj = new ArrayList<>();
            for (int k = j; k <= i; k++) {
                adj.add(new Point(lengths[k], heights[k], true, k));
                adj.add(new Point(lengths[k] + w, heights[k], false, k));
            }
            if (j - 1 >= 0 && lengths[j - 1] + w >= lengths[j] && right[j - 1] != -1) {
                for (int k = right[j - 1]; k < j; k++) {
                    adj.add(new Point(lengths[k], heights[k], true, k));
                    adj.add(new Point(lengths[k] + w, heights[k], false, k));
                }
            }

            Collections.sort(adj);
            List<List<Long>> pHeights = new ArrayList<>();
            long rx = i == n - 1 ? Integer.MAX_VALUE: lengths[i + 1]; int rindex = -1;

            long x = -1, y = -1, prevY = -1, firsty = -1;
            for (Point p: adj) {
                if (p.isStart) increment(collectiveHeights, p.y);
                else decrement(collectiveHeights, p.y);
                long currentMax = collectiveHeights.lastKey();
                if (p.x >= rx && rindex == -1) {
//                    if (p.y > ry) rindex = p.index; // RIGHT
//                    ry = Math.max(ry, p.y); // RIGHT
                    rindex = p.index;
                }
                if (x != p.x) {
                    if (x != -1 && y != prevY) {
                        pHeights.add(List.of(x, y));
                    }
                    x = p.x;
                }
                if (currentMax != y) {
                    y = currentMax;
                }
            }
            pHeights.add(List.of(x, y));
//            if (i == 7) System.out.println("heights2 " + pHeights);

            long para = 2 * (lengths[i] + w - lengths[j]);

            long pre = 0;
            for (List<Long> p: pHeights) {
                if (p.get(0) < lengths[j]) continue;
                if (firsty == -1) firsty = p.get(1);
                para += Math.abs(p.get(1) - pre);
                pre = p.get(1);
            }

//            System.out.println(para);
            right[i] = rindex;
//            if (i == 7) System.out.println(para + " " + right[j] + " " + j);

            long prevMax = 0;
            if (j != i && j - 1 >= 0 && right[j - 1] != -1) {
                for (int k = right[j - 1]; k < j; k++)
                    prevMax = Math.max(prevMax, heights[k]);
            }
//            if (i == 7) System.out.println("firsty = " + firsty + ", prevMax = " + prevMax);
//            if (j == i || j - 1 < 0 || right[j - 1] == -1 || lengths[right[j - 1]] + w < lengths[j]) prevMax = firsty;
//            if (i == 7) System.out.println("prevMax = " + prevMax);

            // step3. merge
            if (j != i && j - 1 >= 0) {
                long xdiff = lengths[j - 1] + w - lengths[j];
                if (xdiff >= 0) {
                    para -= 2 * xdiff;
                    if (right[j - 1] != -1) para -= 2 * Math.min(firsty, prevMax);
                    else para -= 2 * firsty;
//                    else para -= 2* heights[right[j - 1]];
                }
//                if (lengths[j] == lengths[i] - w) {
//                    para -= 2 * heights[j];
//                }
            }
//            if (i == 7) System.out.println("rights: " + Arrays.toString(right));
            // step4. record
            parameters[i] = j - 1 >= 0 ? (parameters[j - 1] + para + MOD) % MOD : para % MOD;
        }

        for (int i = 0; i < n; i++) {
            ret *= parameters[i];
            ret %= MOD;
        }
//        System.out.println("right: " + Arrays.toString(right));
//        System.out.println(Arrays.toString(parameters));

        return ret;
    }

    private static class Point implements Comparable<Point> {
        long x, y;
        boolean isStart;
        int index;

        public Point(long x, long y, boolean isStart, int index) {
            this.x = x;
            this.y = y;
            this.isStart = isStart;
            this.index = index;
        }

        @Override
        public int compareTo(Point o) {
            if (this.x != o.x) {
                return Long.compare(this.x, o.x);
            }
            if (this.isStart != o.isStart)
                return this.isStart ? 1: -1;
            return Long.compare(this.y, o.y);
        }
    }

    private static void decrement(TreeMap<Long, Long> map, long key) {
        long val = map.getOrDefault(key, 0L);
        if (val == 1)
            map.remove(key);
        if (val > 1)
            map.put(key, val - 1);
    }

    private static void increment(TreeMap<Long, Long> map, long key) {
        map.put(key, map.getOrDefault(key, 0L) + 1);
    }
}

