package codejam.y2019.qualificationround2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static String getPath(String x, int N)
    {
        // No need to use a graph. The constraint that we can only go from W to E and from N to S
        // enables me to simply replace S with E and E with S
        char[] inputs = x.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.length; i++)
        {
            char temp = inputs[i] == 'S' ? 'E' : 'S';
            sb.append(temp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        List<String> solution = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());
            String[] solutions = new String[n + 1];
            String x;
            int N;
            int i = 1;
            while ((x = br.readLine()) != null)
            {
                N = Integer.parseInt(x);
                x = br.readLine();
                solutions[i] = getPath(x, N);
                i++;
            }
            for (int j = 1; j < n + 1; j++)
            {
                System.out.println("Case #" + j + ": " + solutions[j]);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
