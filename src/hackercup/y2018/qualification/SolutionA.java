package hackercup.y2018.qualification;


import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */
public class SolutionA {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2018\\qualification\\tourist.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter("src\\hackercup\\y2018\\qualification\\outputA.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                String[] integers = br.readLine().split("\\s+");
                int N = Integer.parseInt(integers[0]);
                int K = Integer.parseInt(integers[1]);
                long V = Long.parseLong(integers[2]);
                String[] attractions = new String[N];
                for (int j = 0; j < N; j++) {
                    attractions[j] = br.readLine().trim();
                }
                Set<Integer> solIndices = chooseNextAttractions(N, K, V);
                StringBuilder sb = new StringBuilder();
                for (int k : solIndices) {
                    sb.append(attractions[k] + " ");
                }
                printWriter.printf("Case #%d: %s\n", i, sb.toString().trim());
            }
            printWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static Set<Integer> chooseNextAttractions(int N, int K, long V) {
        int netVisit = (int) ((V - 1) % N);
        Set<Integer> solIndices = new TreeSet<>();
        int start = netVisit * K % N;
        for (int i = 0; i < K; i++) {
            solIndices.add((start + i) % N);
        }
        return solIndices;
    }
}
