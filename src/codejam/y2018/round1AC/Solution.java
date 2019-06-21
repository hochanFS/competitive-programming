package codejam.y2018.round1AC;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Intends to solve a problem (#3) for Round 1A in Code Jam Competition.
 * @author Hochan Lee
 */

public class Solution {
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter out = new PrintWriter(System.out);
        try {
            int T = in.nextInt();
            for (int i = 1; i <= T; i++) {
                int N = in.nextInt();
                int constraint = in.nextInt(); // == P

                Rectangle[] rectangles = new Rectangle[N];
                double sumInitArea = 0;
                double maxPossibleAddedParameter = 0;

                for (int j = 0; j < N; j++) {
                    rectangles[j] = new Rectangle(in.nextDouble(), in.nextDouble());
                    sumInitArea += 2 * (rectangles[j].w + rectangles[j].h);
                    maxPossibleAddedParameter += rectangles[j].maxCut;
                }

                constraint -= sumInitArea; // Sum of minCut should be less than constraint.
                if (constraint > maxPossibleAddedParameter) {
                    // if the maximum possible parameter falls short of P, return the max value.
                    out.printf("Case #%d: %.6f\n", i, maxPossibleAddedParameter + sumInitArea);
                    continue;
                }
                boolean doesMeetConstraint = false;

                // I will consier one cookie at a time and store the maximum parameter increase for each of the given constraint for given set of cookies.
                // memoize[i][j] would represent the value of the maximum parameter increase we can get from cutting--or not cutting--\
                // the first cookie through the i(th) cookie while the minimum parameter increase is at most j.
                // I need to consider all combinations:
                // i = 0 ~ N (add one cookie at a time until all cookies are considered)
                // j = 0 ~ constraint (we need to keep up with this exercise until we exceed the constraint)

                double[][] memoize = new double[N + 1][constraint + 1];
                Arrays.fill(memoize[0], 0);
                for (int r = 1; r <= N; r++) {
                    int constraintUsage = (int) Math.ceil(rectangles[r - 1].minCut);
                    for (int c = 0; c <= Math.min(constraintUsage, constraint); c++) {
                        memoize[r][c] = memoize[r - 1][c];
                    }
                    for (int c = constraintUsage; c <= constraint; c++) {
                        memoize[r][c] = Math.max(memoize[r - 1][c], Math.max(memoize[r - 1][c],
                                memoize[r - 1][c - constraintUsage] + rectangles[r - 1].maxCut));
                    }
                    if (memoize[r][constraint] >= constraint) {
                        // If minCut stays below the constraint, and the maxCut stays above the constraint,
                        // there must be a way to cut through the rectangle in half such that the sum of parameters equal to the desired level.
                        doesMeetConstraint = true;
                        break;
                    }
                }
                if (doesMeetConstraint)
                    out.printf("Case #%d: %.6f\n", i, sumInitArea + constraint);
                else {
                    out.printf("Case #%d: %.6f\n", i, sumInitArea + memoize[N][constraint]);
                }
            }
            out.close();

        }
        catch (IOException ie) {
            ie.printStackTrace();
        }


    }

    public static class Rectangle
    {
        double w, h, minCut, maxCut;
        Rectangle(double W, double H)
        {
            w = W;
            h = H;
            minCut = 2 * Math.min(w, h);
            maxCut = 2 * Math.sqrt(w * w + h * h);
        }
    }

    /**
     * IO class helps me write input/output code very quickly.
     * I would be able to skip writing the type conversion code such as (e.g. Integer.parseInt(x)).
     * This is adopted from another Code Jam competitor from 2018 solution
     * Credit goes to: electronicsaddict
     */

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