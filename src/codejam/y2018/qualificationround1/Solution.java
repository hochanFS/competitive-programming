package codejam.y2018.qualificationround1;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    private char[] sequence;

    Solution(String s)
    {
        sequence = s.toCharArray();
    }

    public int getMinimumNumberOfAttempts(int D)
    {
        int N = sequence.length;
        int[] cPositions = new int[N + 1];
        int count = 0;
        int countShoot = 0;
        int shoot = 1;

        int j = 0;
        for (int i = 0; i < N; i++) {
            if (sequence[i] == 'C'){
                cPositions[j++] = i;
                shoot = 1 << j;
            }
            if (sequence[i] == 'S') {
                count += shoot;
                countShoot += 1;
            }
        }

        if (count <= D)
            return 0;
        if (countShoot > D)
            return -1;

        cPositions[j] = N - 1;
        int maxShoot, nShootRightToJ, temp, x;
        int numChange = 0;

        //System.out.println(Arrays.toString(sequence));
        while(j > 0)
        {
            //System.out.println("Count: " + count);
            --j;
            temp = cPositions[j];
            nShootRightToJ = cPositions[j + 1] - temp;
            maxShoot = 1 << j;
            //maxShoot >>= 1;
            //System.out.println("MaxShoot: " + maxShoot);
            if (count - maxShoot * nShootRightToJ > D)
            {
                numChange += nShootRightToJ;
                count -= maxShoot * nShootRightToJ;
                cPositions[j] = cPositions[j + 1] - 1;
                continue;
            }

            x = (count - D) / maxShoot;
            //System.out.println("x = " + x + ".." + (D >= count - maxShoot * x));
            if (D >= count - maxShoot * x)
            {
                numChange += x;
                break;
            }
            numChange += (x + 1);
            break;
        }
        return numChange;
    }


    public static void main(String[] args) {
        List<Integer> defenseAbilities = new ArrayList<>();
        List<String> sequences = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());
            String x;
            while ((x = br.readLine()) != null)
            {
                String[] inputs = x.split("\\p{javaWhitespace}+");
                defenseAbilities.add(Integer.parseInt(inputs[0]));
                sequences.add(inputs[1]);
            }
            int sol = 0;
            for (int i = 0; i < defenseAbilities.size(); i++)
            {
                Solution su = new Solution(sequences.get(i));
                sol = su.getMinimumNumberOfAttempts(defenseAbilities.get(i));
                String solution = sol == -1 ? "IMPOSSIBLE" : sol + "";
                System.out.println("Case #" + (i + 1) + ": " + solution);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
6
3 CSCSS
 */