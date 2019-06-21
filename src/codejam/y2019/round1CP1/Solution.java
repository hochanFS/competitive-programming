package codejam.y2019.round1CP1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.io.PrintWriter;

/**
 * @author Hochan Lee
 */

public class Solution {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();

            for (int i = 1; i <= T; i++) {
                Set<String> strings = new TreeSet<>();
                int A = in.nextInt();
                int maxLen = 1;
                for (int j = 0; j < A; j++) {
                    String s = in.next().trim();
                    strings.add(s);
                    maxLen = Math.max(maxLen, s.length());
                }

                List<String> stringList = new LinkedList<>();
                Set<Character> charList = new HashSet<>();

                for (String s: strings) {
                    charList.add(s.charAt(0));
                    StringBuilder sb = new StringBuilder(s);
                    while (sb.length() < 500) {
                        sb.append(sb);
                    }
                    sb.setLength(500);
                    stringList.add(sb.toString());
                }

                int j = 0;
                boolean solvable = true;
                StringBuilder sb = new StringBuilder();
                while (j < Math.min(maxLen * 2, 501) && !stringList.isEmpty()) {
                    if (charList.size() >= 3) {
                        solvable = false;
                        break;
                    }
                    //System.out.println(stringList);
                    //System.out.println(charList);
                    Set<Character> temp = charList;
                    if (temp.contains('R') && temp.contains('S')) {
                        sb.append('R');
                        removeIf(stringList, j, 'S');
                        charList = new HashSet<>();
                        for (String s : stringList) {
                            charList.add(s.charAt(j + 1));
                        }
                        j++;
                        continue;
                    }
                    if (temp.contains('S') && temp.contains('P')) {
                        sb.append('S');
                        removeIf(stringList, j, 'P');
                        charList = new HashSet<>();
                        for (String s : stringList) {
                            charList.add(s.charAt(j + 1));
                        }
                        j++;
                        continue;
                    }
                    if (temp.contains('R') && temp.contains('P')) {
                        sb.append('P');
                        removeIf(stringList, j, 'R');
                        charList = new HashSet<>();
                        for (String s : stringList) {
                            charList.add(s.charAt(j + 1));
                        }
                        j++;
                        continue;
                    }
                    if (temp.contains('P')) {
                        sb.append('S');
                        stringList = new LinkedList<>();
                        break;
                    }
                    if (temp.contains('R')) {
                        sb.append('P');
                        stringList = new LinkedList<>();
                        break;
                    }

                    if (temp.contains('S')) {
                        sb.append('R');
                        stringList = new LinkedList<>();
                        break;
                    }

                }

                if (!stringList.isEmpty())
                    solvable = false;

                if (!solvable) {
                    out.printf("Case #%d: IMPOSSIBLE\n", i);
                }
                else {
                    out.printf("Case #%d: ", i);
                    out.println(sb.toString());
                }
            }
            out.close();

        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void removeIf(List<String> list, int pos, Character c) {
        List<Integer> removeIndices = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).charAt(pos) == c)
                removeIndices.add(i);
        }
        Collections.sort(removeIndices, Collections.reverseOrder());

        for (int i : removeIndices) {
            list.remove(i);
        }

        //System.out.println(list);
    }

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