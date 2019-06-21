package codeforces.round558d2;

import java.util.Scanner;

public class SolutionA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println(getMaxDivision(n, m));
    }

    public static int getMaxDivision(int n, int m) {
        if (m <= 1)
            return 1;
        if (m <= n / 2)
            return m;
        else
            return n - m;
    }
}
