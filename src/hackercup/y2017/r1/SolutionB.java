package hackercup.y2017.r1;

import java.io.*;
import java.util.*;

/**
 * @author Hochan Lee (hochan.lee@financescript.com)
 */

public class SolutionB {
    public static void main(String[] args) {
        String fileName = "src\\hackercup\\y2017\\r1\\inputB.txt";
        String output = "src\\hackercup\\y2017\\r1\\sampleOutputB.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int T = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= T; i++) {
                String[] integers = br.readLine().split("\\s+");
                int N = Integer.parseInt(integers[0]);
                long R = Long.parseLong(integers[1]);
                SolutionB solutionB = new SolutionB(N, R, br);
                printWriter.printf("Case #%d: %d\n", i, solutionB.getSolution());
                System.out.printf("Case #%d done!\n", i);
            }
            printWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private int N;
    private long R;
    private HashSet<Point> zombieLocations;
    private TreeSet<Long> uniqueHeights;

    SolutionB(int N, long R, BufferedReader br) throws IOException {
        zombieLocations = new HashSet<>();
        uniqueHeights = new TreeSet<>();
        this.N = N;
        this.R = R;
        for (int i = 1; i <= N; i++) {
            String[] coordinates = br.readLine().trim().split("\\s+");
            long X = Long.parseLong(coordinates[0]);
            long Y = Long.parseLong(coordinates[1]);
            zombieLocations.add(new Point(X, Y));
            uniqueHeights.add(Y);
        }
    }

    public int getSolution() {
        ArrayList<Point> sortedZombies = new ArrayList<>(zombieLocations);
        int max = 0;
        for (int i = 0; i < N; i++) {
            Point zombie = sortedZombies.get(i);
            long zX = zombie.X;
            for (long zY : uniqueHeights) {
                if (zY < zombie.Y || zY - zombie.Y > R) {
                    continue;
                }
                Set<Point> temp = new HashSet<>(zombieLocations);
                int outerCount = 0;
                for (Point anotherZombie : zombieLocations) {
                    if (zX <= anotherZombie.X + R && zY <= anotherZombie.Y + R &&
                            zX >= anotherZombie.X && zY >= anotherZombie.Y) {
                        outerCount++;
                        temp.remove(anotherZombie);
                    }
                    max = Math.max(max, outerCount + solve(temp));
                }
            }
        }
        return max;
    }

    public int solve(Set<Point> zombies) {
        ArrayList<Point> zombieArray = new ArrayList<>(zombies);
        int max = 0;
        for (int i = 0; i < zombieArray.size(); i++) {
            Point zombie = zombieArray.get(i);
            Set<Point> temp = new TreeSet<>(zombies);
            long zX = zombie.X;
            for (long zY : uniqueHeights) {
                if (zY < zombie.Y || zY - zombie.Y > R) {
                    continue;
                }
                int innerCount = 0;
                for (Point anotherZombie : temp) {
                    if (zX <= anotherZombie.X + R && zY <= anotherZombie.Y + R &&
                            zX >= anotherZombie.X && zY >= anotherZombie.Y) {
                        innerCount++;
                    }
                }
                max = Math.max(max, innerCount);
            }
        }
        return max;
    }

    public int getSolution2() {
        for (int i = 0; i < N; i++) {
            //TODO: add revised code
        }
        return 0;
    }

    public class Point implements Comparable<Point> {
        long X;
        long Y;

        public Point(long x, long y) {
            this.X = x;
            this.Y = y;
        }

        @Override
        public int compareTo(Point o) {
            if (this.X != o.X) {
                return Long.compare(this.X, o.X);
            }
            if (this.Y != o.Y) {
                return Long.compare(this.Y, o.Y);
            }
            return 0;
        }
    }
}
