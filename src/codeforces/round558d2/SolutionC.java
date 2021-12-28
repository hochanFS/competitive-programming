package codeforces.round558d2;

import java.util.*;

public class SolutionC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numPoles = scanner.nextInt();
        Coordinate[] poles = new Coordinate[numPoles];
        for (int i = 0; i < numPoles; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            poles[i] = new Coordinate(x, y);
        }

        Set<Line> lines = new TreeSet<>();
        for (int i = 0; i < numPoles; i++) {
            for (int j = i + 1; j < numPoles; j++) {
                lines.add(new Line(poles[i], poles[j]));
            }
        }

        List<Line> wires = new ArrayList<>(lines);
        int numLines = wires.size();

        List<Long> setOfSameSlopes = new ArrayList<>();
        long count = 1L;
        for (int i = 1; i < numLines; i++) {
            if (wires.get(i - 1).a == wires.get(i).a && wires.get(i - 1).b == wires.get(i).b) {
                count++;
                if (i == numLines - 1 || (wires.get(i).a != wires.get(i + 1).a || wires.get(i).b != wires.get(i + 1).b)) {
                    setOfSameSlopes.add(count);
                    count = 1L;
                }
            }
        }

        long nL = (long) numLines;
        long numIntersects = (nL) * (nL - 1) / 2;
        for (long sameSlope : setOfSameSlopes) {
            numIntersects -= sameSlope * (sameSlope - 1) / 2;
        }

        System.out.println(numIntersects);

    }

    public static class Coordinate {
        public int x;
        public int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Line implements Comparable<Line> {
        public int a;
        public int b;
        public int c;

        public Line(Coordinate c1, Coordinate c2) {
            a = c2.y - c1.y;
            b = c2.x - c1.x;
            c = c1.y * c2.x - c1.x * c2.y;
            if (a == 0 || b == 0) {
                if (a == 0) {
                    b = 1;
                    c = c1.y;
                } else {
                    a = 1;
                    c = c1.x;
                }
            } else {
                int gcd = gcd(a, gcd(b, c));
                a /= gcd;
                b /= gcd;
                c /= gcd;
                if (a < 0) {
                    a *= -1;
                    b *= -1;
                    c *= -1;
                } else if (a == 0 && b < 0) {
                    b *= -1;
                    c *= -1;
                }
            }
        }

        @Override
        public int compareTo(Line o) {
            if (this.a != o.a)
                return Integer.compare(this.a, o.a);
            if (this.b != o.b)
                return Integer.compare(this.b, o.b);
            return Integer.compare(this.c, o.c);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Line l = (Line) obj;
            if (this.a == l.a && this.b == l.b && this.c == l.c)
                return true;
            return false;
        }

        public int gcd(int a, int b) {
            if (a == 0)
                return b;
            return gcd(b % a, a);
        }
    }
}
