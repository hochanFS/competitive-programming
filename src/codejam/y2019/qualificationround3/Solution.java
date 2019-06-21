package codejam.y2019.qualificationround3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Solution {

    public static BigInteger sqrt(BigInteger val) {
        BigInteger half = BigInteger.ZERO.setBit(val.bitLength() / 2);
        BigInteger cur = half;

        while (true) {
            BigInteger tmp = half.add(val.divide(half)).shiftRight(1);

            if (tmp.equals(half) || tmp.equals(cur))
                return tmp;

            cur = half;
            half = tmp;
        }
    }

    public static int gcd(int a, int b)
    {
        if (a == 0)
            return b;

        return gcd(b%a, a);
    }

    public static String decypherSmall(String code, int L)
    {
        String[] inputs = code.split("\\p{javaWhitespace}+");
        Integer[] primeMultiplied = new Integer[L];
        for (int i = 0; i < L; i++)
            primeMultiplied[i] = Integer.parseInt(inputs[i]);

        Set<Integer> uniquePrimes = new TreeSet<>();

        Integer[] primes = new Integer[L + 1];
        for (int i = 1; i < L; i++)
        {
            Integer temp = gcd(primeMultiplied[i - 1], (primeMultiplied[i]));
            if (temp.equals(primeMultiplied[i])) {
                temp = (int) Math.sqrt((double) temp);
                /*if (temp * temp != primeMultiplied[i])
                {
                    primes[i] = primeMultiplied[i];
                    primes[i - 1] = 1;
                    uniquePrimes.add(1);
                    temp = primeMultiplied[i];
                }*/
            }

            primes[i] = temp;
            uniquePrimes.add(temp);
        }
        primes[0] = primeMultiplied[0] / primes[1];
        //System.out.println(primes[0]);
        uniquePrimes.add(primes[0]);
        primes[L] = primeMultiplied[L - 1] / primes[L - 1];
        //System.out.println(primes[L]);
        uniquePrimes.add(primes[L]);
        Map<Integer, Character> map = new TreeMap<>();

        Character c = 'A';
        //System.out.println(uniquePrimes.size());

        for (Integer bi : uniquePrimes)
            map.put(bi, c++);
        //System.out.println(map); //TODO: comment it out
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < L + 1; i++)
        {
            sb.append(map.get(primes[i]));
        }

        return sb.toString();
    }

    public static String decypher(String code, int L)
    {
        String[] inputs = code.split("\\p{javaWhitespace}+");
        BigInteger[] primeMultiplied = new BigInteger[L];
        for (int i = 0; i < L; i++)
            primeMultiplied[i] = new BigInteger(inputs[i]);

        Set<BigInteger> uniquePrimes = new TreeSet<>();

        BigInteger[] primes = new BigInteger[L + 1];
        for (int i = 1; i < L; i++)
        {
            BigInteger temp = primeMultiplied[i - 1].gcd(primeMultiplied[i]);
            if (temp.equals(primeMultiplied[i])) {
                temp = sqrt(temp);
            }

            primes[i] = temp;
            uniquePrimes.add(temp);
        }
        primes[0] = primeMultiplied[0].divide(primes[1]);
        //System.out.println(primes[0]);
        uniquePrimes.add(primes[0]);
        primes[L] = primeMultiplied[L - 1].divide(primes[L - 1]);
        //System.out.println(primes[L]);
        uniquePrimes.add(primes[L]);
        Map<BigInteger, Character> map = new TreeMap<>();

        Character c = 'A';
        //System.out.println(uniquePrimes.size());

        for (BigInteger bi : uniquePrimes)
            map.put(bi, c++);
        //System.out.println(map); //TODO: comment it out
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < L + 1; i++)
        {
            sb.append(map.get(primes[i]));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Trying");
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
                //System.out.println(items[0]);
                N = new BigInteger(items[0]);
                L = Integer.parseInt(items[1]);
                x = br.readLine();
                if (N.compareTo(new BigInteger("32768")) < 0)
                    solutions[i] = decypherSmall(x, L);
                else
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
