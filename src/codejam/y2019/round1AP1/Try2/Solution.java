package codejam.y2019.round1AP1.Try2;

import java.io.*;
import java.util.*;

public class Solution
{
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                String[] lines = new String[N];
                for (int j = 0; j < N; j++) {
                    lines[j] = in.next();
                }
                out.printf("Case #%d: %d\n", i, getNumber(lines));
            }
            out.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public static int getNumber(String[] lines) {
        // reverse the lines input
        List<String> reversedLines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            sb = new StringBuilder();
            String temp = lines[i];
            for (int j = temp.length() -1; j >= 0; j--) {
                sb.append(temp.charAt(j));
            }
            reversedLines.add(sb.toString());
        }


        Collections.sort(reversedLines);
        Set<String> possibleCommonSuffices = new TreeSet<>(
                SORT_SET
        );

        for (int i = 1; i < reversedLines.size(); i++) {
            sb = new StringBuilder();
            String left = reversedLines.get(i - 1);
            //System.out.println("left: " + left);
            String right = reversedLines.get(i);
            int k = 0;
            while (k < left.length() && k < right.length() && left.charAt(k) == right.charAt(k)) {
                sb.append(left.charAt(k));
                possibleCommonSuffices.add(sb.toString());
                k++;
            }
        }
        //System.out.println(possibleCommonSuffices);

        LinkedList<String> reversedLines3 = new LinkedList<>(reversedLines);
        int count = 0;
        for (String x : possibleCommonSuffices) {
            boolean foundFirst = false;
            String first = "1";
            String second = "1";
            for (String y : reversedLines3) {
                if (y.startsWith(x)) {
                    if (!foundFirst) {
                        first = y;
                        foundFirst = true;
                        continue;
                    }
                    if (foundFirst) {
                        second = y;
                        count += 2;
                        reversedLines3.remove(first);
                        reversedLines3.remove(second);
                        break;
                    }
                }

            }

        }



        return count;
    }

    public static Comparator<String> SORT_SET = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() != o2.length())
                return o2.length() - o1.length();
            else return o1.compareTo(o2);
        }
    };



    //@
    static class In {
        BufferedReader br;
        StringTokenizer st;

        public In(InputStream i) {
            br = new BufferedReader(new InputStreamReader(i));
            st = new StringTokenizer("");
        }

        public String next() throws IOException {
            if(st.hasMoreTokens())
                return st.nextToken();
            else
                st = new StringTokenizer(br.readLine());
            return next();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        //#
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
        //$
    }
}
