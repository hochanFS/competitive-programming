package codejam.y2019.qualificationround1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static String divideIntoTwoDigits(String x)
    {
        char[] inputs = x.toCharArray();
        int len = inputs.length;
        StringBuilder solutions1Builder = new StringBuilder();
        StringBuilder solutions2Builder = new StringBuilder();
        boolean toggled = false;

        for (int i = 0; i < len; i++)
        {
            if (inputs[i] == '4')
            {
                solutions1Builder.append('2');
                solutions2Builder.append('2');
                toggled = true;
                continue;
            }
            solutions1Builder.append(inputs[i]);
            if (toggled)
                solutions2Builder.append('0');
        }
        StringBuilder solution = new StringBuilder();
        solution.append(solutions1Builder).append(' ').append(solutions2Builder);
        return solution.toString();
    }

    public static void main(String[] args) {
        List<String> solution = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());
            String[] solutions = new String[n + 1];
            String x;
            int i = 1;
            while ((x = br.readLine()) != null)
            {
                solutions[i] = divideIntoTwoDigits(x);
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
