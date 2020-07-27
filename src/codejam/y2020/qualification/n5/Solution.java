package codejam.y2020.qualification.n5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int K = in.nextInt();
                if (K == N + 1 || K == N * N - 1) {
                    out.printf("Case #%d: IMPOSSIBLE\n", i);
                    continue;
                }
                int[][] values = constructMatrix(N, K);
                int[][] matrix = solveMatrix(values, N);
                int trial = 0;
                while (trial < 100) {
                    if (matrix[N - 1][N - 2] != 0)
                        break;
                    matrix = solveMatrix(values, N);
                    trial++;
                }
                if (matrix[N - 1][N - 2] == 0) {
                    out.printf("Case #%d: IMPOSSIBLE\n", i); // not sure if this is necessary
                }
                else {
                    out.printf("Case #%d: POSSIBLE\n", i);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < N; j++) {
                        for (int k = 0; k < N; k++) {
                            sb.append(matrix[j][k]);
                            if (k != N - 1)
                                sb.append(' ');
                        }
                        sb.append('\n');
                    }
                    out.print(sb.toString());
                }
            }
            out.close();


        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static int[][] constructMatrix(int N, int K) {
        int minValue = K / N;
        int remainder = K % N;

        // construct matrix and its diagonal values
        int[][] matrix = new int[N][N];
        if (remainder != 1 && remainder != N - 1) {
            for (int i = 0; i < N; i++) {
                if (i < remainder) {
                    matrix[i][i] = minValue + 1;
                } else {
                    matrix[i][i] = minValue;
                }
            }
        }
        if (remainder == 1 && minValue != N - 1) {
            matrix[N-1][N-1] = minValue - 1;
            matrix[N-2][N-2] = minValue + 2;
            for (int i = 0; i < N-2; i++) {
               matrix[i][i] = minValue;
            }
        } else if (remainder == 1 && minValue == N - 1) {
            matrix[N-1][N-1] = minValue + 1;
            matrix[N-2][N-2] = minValue + 1;
            matrix[N-3][N-3] = minValue - 1;
            for (int i = 0; i < N-3; i++) {
                matrix[i][i] = minValue;
            }
        }
        else if (remainder == N - 1 && minValue != N - 1) {
            matrix[N-1][N-1] = minValue;
            matrix[N-2][N-2] = minValue;
            matrix[N-3][N-3] = minValue + 2;
            for (int i = 0; i < N-3; i++) {
                matrix[i][i] = minValue + 1;
            }
        }
        else if (remainder == N - 1 && minValue == N - 1) {
            matrix[N-1][N-1] = minValue - 1;
            matrix[N-2][N-2] = minValue - 2;
            for (int i = 0; i < N-2; i++) {
                matrix[i][i] = minValue + 1;
            }
        }
        return matrix;
    }

    private static int[][] solveMatrix(int[][] matrix, int N) {
        Set<Integer> rowSet = new HashSet<>();
        List<Set<Integer>> colSetList = new ArrayList<>();
        int[] candidates = IntStream.range(1, N + 1).toArray();
        for (int i = 0; i < N; i++) {
            colSetList.add(new HashSet<>());
            colSetList.get(i).add(matrix[i][i]);
        }

        for (int i = 0; i < N; i++) {
            shuffleArray(candidates);
            rowSet.clear();
            rowSet.add(matrix[i][i]);
            colSetList.get(i).add(matrix[i][i]);
            dfsRow(matrix, candidates, rowSet, colSetList, i, 0);
        }
        return matrix;
    }

    private static boolean dfsRow(int[][] matrix, int[] candidates, Set<Integer> rowSet, List<Set<Integer>> colSet, int skip, int i) {
        if (i == skip)
            return dfsRow(matrix, candidates, rowSet, colSet, skip, i + 1);
        if (i == matrix.length)
            return true;
        for (int val : candidates) {
            if (isValid(val, rowSet, colSet.get(i))) {
                matrix[skip][i] = val;
                rowSet.add(val);
                colSet.get(i).add(val);
                if (!dfsRow(matrix, candidates, rowSet, colSet, skip, i + 1)) {
                    rowSet.remove(val);
                    colSet.get(i).remove(val);
                    matrix[skip][i] = 0;
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValid(int val, Set<Integer> row, Set<Integer> col) {
        return !(row.contains(val) || col.contains(val));
    }

    public static void shuffleArray(int[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
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