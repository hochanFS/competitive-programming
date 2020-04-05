package codejam.y2020.qualification.n1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Solution {
    public static void main(String[] args) {

        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int[][] matrix = new int[N][N];
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        matrix[j][k] = in.nextInt();
                    }
                }
                int k = calculateTrace(matrix);
                int[] duplicates = getDuplicateRowCol(matrix);
                out.printf("Case #%d: %d %d %d\n", i, k, duplicates[0], duplicates[1]);
            }
            out.close();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

   private static int calculateTrace(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }
        return sum;
   }

   private static int[] getDuplicateRowCol(int[][] matrix) {
        Set<Integer> rowSet = new HashSet<>();
        int duplicateRows = 0;
        int duplicateCols = 0;
        List<Set<Integer>> colSetList = new ArrayList<>();
        Set<Integer> colSet;
        boolean[] rowCounted = new boolean[matrix.length];
        boolean[] counted = new boolean[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rowSet.clear();
            for (int j = 0; j < matrix.length; j++) {
                if (i == 0) {
                    colSetList.add(new HashSet<>());
                }
                colSet = colSetList.get(j);
                int val = matrix[i][j];
                if (rowSet.contains(val) && !rowCounted[i]) {
                    duplicateRows++;
                    rowCounted[i] = true;
                }
                if (colSet.contains(val) && !counted[j]) {
                    duplicateCols++;
                    counted[j] = true;
                }
                colSet.add(val);
                rowSet.add(val);
            }
        }
        return new int[]{duplicateRows, duplicateCols};
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