package codejam.y2018.round1A1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Intends to solve a problem (#1) for Round 1A in Code Jam Competition.
 *
 * @author: hochanlee
 */

public class Solution {
    private int R;
    private int C;
    private byte[][] matrix;
    private int nChocolate;
    private byte[] perfMatrix; // one-dimensional array for speed
    private int H;
    private int V;

    private int[] cumRowSum;
    private int[] cumColSum;

    // constructor
    public Solution(int R, int C, int H, int V, BufferedReader br) {
        matrix = new byte[R][C]; // used byte matrix to reduce memory
        this.R = R;
        this.C = C;
        this.H = H;
        this.V = V;
        this.nChocolate = 0;
        cumRowSum = new int[R + 1];
        cumColSum = new int[C + 1];

        try {
            for (int i = 0; i < R; i++) {
                String row = br.readLine();
                for (int j = 0; j < C; j++) {
                    if (row.charAt(j) == '@') {
                        this.nChocolate++;
                        matrix[i][j] = (byte) 1;
                        cumRowSum[i + 1] = cumRowSum[i + 1] == 0 ? cumRowSum[i] + 1 : cumRowSum[i + 1] + 1;
                    } else {
                        matrix[i][j] = 0;
                        cumRowSum[i + 1] = cumRowSum[i + 1] == 0 ? cumRowSum[i] : cumRowSum[i + 1];
                    }
                }
            }

            for (int j = 0; j < C; j++) {
                for (int i = 0; i < R; i++) {
                    if (matrix[i][j] == (byte) 1)
                        cumColSum[j + 1] = cumColSum[j + 1] == 0 ? cumColSum[j] + 1 : cumColSum[j + 1] + 1;
                    else
                        cumColSum[j + 1] = cumColSum[j + 1] == 0 ? cumColSum[j] : cumColSum[j + 1];
                }
            }


            perfMatrix = new byte[this.R * this.C];
            for (int i = 0; i < this.R; i++) {
                for (int j = 0; j < this.C; j++) {
                    perfMatrix[i * this.C + j] = matrix[i][j];
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public int countChocolate(int startRow, int endRow, int startCol, int endCol) {
        int count = 0;
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                if (perfMatrix[i * C + j] == (byte) 1)
                    count++;
            }
        }
        return count;
    }

    public boolean isDivisible() {
        if (nChocolate == 0)
            return true;

        if (R <= H || C <= V || nChocolate % ((H + 1) * (V + 1)) != 0)
            return false;

        int currentR = 1;
        int currentC = 1;
        int[] rowCuts = new int[H + 2];
        int[] colCuts = new int[V + 2];
        int numRowChoco = nChocolate / (H + 1);
        int numColChoco = nChocolate / (V + 1);
        int individualChoclate = numColChoco / (H + 1);

        for (int i = 0; i < R + 1; i++) {
            if (cumRowSum[i] - cumRowSum[rowCuts[currentR - 1]] > numRowChoco)
                return false;
            if (cumRowSum[i] - cumRowSum[rowCuts[currentR - 1]] == numRowChoco)
                rowCuts[currentR++] = i;
        }


        for (int i = 0; i < C + 1; i++) {
            if (cumColSum[i] - cumColSum[colCuts[currentC - 1]] > numColChoco)
                return false;
            if (cumColSum[i] - cumColSum[colCuts[currentC - 1]] == numColChoco)
                colCuts[currentC++] = i;
        }

        for (int i = 0; i <= H; i++) {
            for (int j = 0; j <= V; j++) {
                if (countChocolate(rowCuts[i], rowCuts[i + 1], colCuts[j], colCuts[j + 1]) != individualChoclate)
                    return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < R; i++)
            sb.append(Arrays.toString(matrix[i])).append('\n');
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());
            Solution[] mySolutions = new Solution[T + 1];
            for (int i = 1; i <= T; i++) {
                String line = br.readLine();
                String[] items = line.split("\\p{javaWhitespace}+");
                mySolutions[i] = new Solution(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2]), Integer.parseInt(items[3]), br);

            }

            for (int i = 1; i <= T; i++) {
                String solPrint;
                if (mySolutions[i].isDivisible())
                    solPrint = "POSSIBLE";
                else
                    solPrint = "IMPOSSIBLE";
                StringBuilder sb = new StringBuilder();
                sb.append("Case #").append(i).append(": ").append(solPrint);
                System.out.println(sb);
            }

            br.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }
}
