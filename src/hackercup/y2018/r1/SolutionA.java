package hackercup.y2018.r1;

import java.io.*;

public class SolutionA {
    public static long MAX = 1000000007L;
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\r1\\inputA.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2018\\r1\\letitflow.out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T ; i++) {
                int N = Integer.parseInt(br.readLine().trim());
                char[][] ground = new char[3][N];

                for (int j = 0; j < 3; j++) {
                    ground[j] = br.readLine().trim().toCharArray();
                }
                if (N % 2 == 1 || ground[0][0] == '#' || ground[1][0] == '#'
                        || ground[1][N - 1] == '#' || ground[2][N - 1] == '#') {
                    printWriter.printf("Case #%d: %d\n", i, 0);
                    continue;
                }
                long solution = 1L;
                for (int j = 1; j < N - 1; j += 2) {
                    int temp = 0;
                    if (ground[1][j] == '#' || ground[1][j + 1] == '#') {
                        solution = 0;
                        break;
                    }
                    if (ground[0][j] != '#' && ground[0][j + 1] != '#') {
                        temp += 1;
                    }
                    if (ground[2][j] != '#' && ground[2][j + 1] != '#') {
                        temp += 1;
                    }
                    if (temp == 0) {
                        solution = 0;
                        break;
                    }
                    solution *= temp;
                    if (solution >= MAX) {
                        solution %= MAX;
                    }
                }
                printWriter.printf("Case #%d: %d\n", i, solution);
            }

            printWriter.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
}
