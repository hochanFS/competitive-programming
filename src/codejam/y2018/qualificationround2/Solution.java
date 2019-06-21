package codejam.y2018.qualificationround2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {

    public static int identifyBadIndex(int[] inputs1, int[] inputs2, int N)
    {
        if (inputs2 == null)
            return -1;

        Arrays.sort(inputs1); // N / 2 + N % 2
        Arrays.sort(inputs2); // N / 2

        if (inputs1[0] < inputs2[0])
            return 1;

        for (int i = 1; i < N / 2; i ++)
        {
            if (inputs1[i] > inputs2[i])
                return i * 2 + 1;
            if (inputs2[i - 1] > inputs1[i])
                return i * 2;
        }

        if (N % 2 == 1 && inputs1[N / 2 + N % 2] < inputs2[N / 2])
        {
            return N;
        }

        return -1;
    }


    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());

            String line;
            int[] solutions = new int[n];
            int index = 0;

            while ((line = br.readLine()) != null)
            {
                int N = Integer.parseInt(line);

                int[] inputs1 = new int[N / 2 + N % 2];
                int[] inputs2;
                if (N < 2)
                    inputs2 = null;
                else
                    inputs2 = new int[N / 2];

                line = br.readLine();
                String[] items = line.split("\\p{javaWhitespace}+");

                for (int i = 0; i < N; i += 2)
                {
                    inputs1[i] = Integer.parseInt(items[i]);
                }

                for (int i = 1; i < N; i += 2)
                {
                    inputs2[i] = Integer.parseInt(items[i]);
                }

                solutions[index++] = identifyBadIndex(inputs1, inputs2, N);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
