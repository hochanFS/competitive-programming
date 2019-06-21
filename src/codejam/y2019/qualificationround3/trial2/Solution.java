package codejam.y2019.qualificationround3.trial2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Solution {

    public static int gcd(int a, int b)
    {
        if (a == 0)
            return b;

        return gcd(b%a, a);
    }

    public static String decypher(String code, int L)
    {
        String[] inputs = code.split("\\p{javaWhitespace}+");
        BigInteger[] primeMultiplied = new BigInteger[L];
        for (int i = 0; i < L; i++)
            primeMultiplied[i] = new BigInteger(inputs[i]);

        Set<BigInteger> uniquePrimes = new TreeSet<>();

        int k = 1;
        BigInteger temp;
        BigInteger[] primes = new BigInteger[L + 1];
        for (int i = 1; i < L; i++)
        {
            if (primeMultiplied[i - 1].equals(primeMultiplied[i])) {
                if (primes[i - 1] != null) {
                    temp = primeMultiplied[i].divide(primes[i - 1]);
                    primes[i] = temp;

                    uniquePrimes.add(temp);
                    continue;
                }
                k = k == -1 ? i : k;
                continue;
            }

            temp = primeMultiplied[i - 1].gcd(primeMultiplied[i]);
            primes[i] = temp;
            uniquePrimes.add(temp);

            if (k != -1)
            {
                for (int x = i; x >= k; x--)
                {
                    temp = primeMultiplied[x - 1].divide(primes[x]);
                    primes[x - 1] = temp;
                    uniquePrimes.add(temp);
                }
                k = -1;
            }
        }
        primes[L] = primeMultiplied[L - 1].divide(primes[L - 1]);
        uniquePrimes.add(primes[L]);
        Map<BigInteger, Character> map = new TreeMap<>();

        Character c = 'A';

        for (BigInteger bi : uniquePrimes)
            map.put(bi, c++);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < L + 1; i++)
        {
            sb.append(map.get(primes[i]));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());
            String[] solutions = new String[n + 1];
            String x;
            BigInteger N;
            int L;
            int i = 1;
            while ((x = br.readLine()) != null)
            {
                String[] items = x.split("\\p{javaWhitespace}+");
                L = Integer.parseInt(items[1]);
                x = br.readLine();
                solutions[i] = decypher(x, L);
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
