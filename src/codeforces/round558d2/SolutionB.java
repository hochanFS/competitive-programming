package codeforces.round558d2;

import java.util.Scanner;

public class SolutionB {
    public static int MAX_U = 100000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] input = new int[n];
        for (int i = 0; i < n; i++) {
            input[i] = scanner.nextInt() - 1;
        }
        System.out.println(getLargestPossibleStreak(n, input));

    }

    public static int getLargestPossibleStreak(int n, int[] input) {
        if (n <= 3)
            return n;
        int[] numOfU = new int[MAX_U + 1];
        int[] numOfCount = new int[n + 1];
        int maxStreak = 1;
        int max = 0;
        for (int i = 0; i < n; i++) {
            int val = input[i];
            int count = numOfCount[numOfU[val]];
            numOfCount[numOfU[val]] = Math.max(count - 1, 0);
            numOfU[val]++;
            numOfCount[numOfU[val]]++;
            int index = numOfU[val];
            count = numOfCount[index];
            max = Math.max(max, index);
            boolean isStreak = false;
            if (count == i + 1)
                isStreak = true;
            if (count == 1 && index == i + 1)
                isStreak = true;
            if (numOfCount[max] * max == i && numOfCount[1] == 1)
                isStreak = true;
            if (numOfCount[max - 1] * (max - 1) == i + 1 - max && numOfCount[max] == 1)
                isStreak = true;
            if (isStreak)
                maxStreak = i + 1;
        }
        return maxStreak;
    }
}
