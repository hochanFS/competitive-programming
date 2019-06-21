package codejam.y2019.round1CP3;

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
                int R = in.nextInt();
                int C = in.nextInt();

                Game game = new Game(R, C, in, out);
                out.printf("Case #%d: %d\n", i, game.solve());
            }
            out.close();

        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    static class Game {
        private int R;
        private int C;
        // gameBoard: stores the game board initial configuration
        private char[][] gameBoard;
        // memo: stores the Grundy number for the given board of (r1 to r2) * (c1 to c2)
        private int[][][][] memo;

        public Game(int r, int c, In in, PrintWriter out) throws IOException {

            this.R = r;
            this.C = c;
            this.gameBoard = new char[r][c];
            this.memo = new int[r + 1][r + 1][c + 1][c + 1];

            for (int[][][] m1 : memo) {
                for (int[][] m2: m1) {
                    for (int[] m3 : m2) {
                        // initialize the memo to identify whether the memo is updated.
                        // the default value of int (0) cannot be used, since Grundy number can also be 0.
                        Arrays.fill(m3, -1);
                    }
                }
            }
            for (int row = 0; row < R; row++) {
                String line = in.next();
                gameBoard[row] = line.toCharArray();
            }
        }

        /**public void printGameBoard(PrintWriter out) {
            for (int r = 0; r < R; r++) {
                out.println(Arrays.toString(gameBoard[r]));
            }
        }*/

        private List<Integer> getValidRows(int r1, int r2, int c1 , int c2) {
            List<Integer> validRows = new ArrayList<>();
            for (int row = r1; row <= r2; row++) {
                boolean valid = true;
                for (int col = c1; col <= c2; col++) {
                    valid &= gameBoard[row][col] == '.';
                        //valid = false;
                }
                if (valid) {
                    validRows.add(row);
                }
            }
            return validRows;
        }

        private List<Integer> getValidCols(int r1, int r2, int c1 , int c2) {
            List<Integer> validCols = new ArrayList<>();
            for (int col = c1; col <= c2; col++) {
                boolean valid = true;
                for (int row = r1; row <= r2; row++) {
                    valid &= gameBoard[row][col] == '.';
                    //valid = false;
                }
                if (valid) {
                    validCols.add(col);
                }
            }
            return validCols;
        }

        private int grundy(int r1, int r2, int c1, int c2) {
            if (r1 > r2 || c1 > c2)
                return 0;
            if (memo[r1][r2][c1][c2] != -1)
                return memo[r1][r2][c1][c2];

            List<Integer> validRows = getValidRows(r1, r2, c1, c2);
            List<Integer> validCols = getValidCols(r1, r2, c1, c2);
            Set<Integer> possibleNextGrundy = new HashSet<>();
            //if (r1 == r2 && c1 == c2 && !validRows.isEmpty())
            //    return memo[r1][r2][c1][c2] = 1;
            for (int r : validRows) {
                possibleNextGrundy.add(grundy(r1, r - 1, c1, c2) ^ grundy(r + 1, r2, c1, c2));
            }
            for (int c : validCols) {
                possibleNextGrundy.add(grundy(r1, r2, c1, c - 1) ^ grundy(r1, r2, c + 1, c2));
            }
            for (int grundy = 0; ; grundy++) {
                if (!possibleNextGrundy.contains(grundy))
                    return memo[r1][r2][c1][c2] = grundy;
            }
        }

        public int solve() {
            List<Integer> validRows = getValidRows(0, R - 1, 0, C - 1);
            List<Integer> validCols = getValidCols(0, R - 1, 0, C - 1);
            int count = 0;
            for (int r : validRows) {
                if ((grundy(0, r - 1, 0, C - 1) ^ grundy(r + 1, R - 1, 0, C - 1)) == 0)
                    count += C;
            }
            for (int c : validCols) {
                if ((grundy(0, R - 1, 0, c - 1) ^ grundy(0, R - 1, c + 1, C - 1)) == 0)
                    count += R;
            }
            return count;
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