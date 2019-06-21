package hackercup.y2018.r1;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author lewin
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("platform_parkour.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("platfom_parkour_out.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        PlatformParkour solver = new PlatformParkour();
        solver.solve(1, in, out);
        out.close();
    }

    static class PlatformParkour {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            Scheduler scheduler = new Scheduler(in, out, () -> new Task() {
                int n, m;
                long h1, h2, w, x, y, z;
                int[] a, b, u, d;

                public void read(InputReader in) {
                    n = in.nextInt();
                    m = in.nextInt();
                    h1 = in.nextInt();
                    h2 = in.nextInt();
                    w = in.nextInt();
                    x = in.nextInt();
                    y = in.nextInt();
                    z = in.nextInt();
                    a = new int[m];
                    b = new int[m];
                    u = new int[m];
                    d = new int[m];
                    for (int i = 0; i < m; i++) {
                        a[i] = in.nextInt() - 1;
                        b[i] = in.nextInt() - 1;
                        u[i] = in.nextInt();
                        d[i] = in.nextInt();
                    }
                }

                public int INF = 1 << 29;

                public int ans;
                public int[] mu1, md1;
                public int[] h;

                public void solve() {
                    h = new int[n];
                    h[0] = (int) h1;
                    h[1] = (int) h2;
                    for (int i = 2; i < n; i++) {
                        h[i] = (int) ((w * h[i - 2] + x * h[i - 1] + y) % z);
                    }

                    for (int i = 0; i < n; i++) h[i] *= 2;
                    for (int i = 0; i < m; i++) {
                        u[i] *= 2;
                        d[i] *= 2;
                    }
                    mu1 = new int[n];
                    Arrays.fill(mu1, INF);
                    md1 = new int[n];
                    Arrays.fill(md1, INF);

                    for (int i = 0; i < m; i++) {
                        if (a[i] < b[i]) {
                            for (int j = a[i]; j < b[i]; j++) {
                                mu1[j] = Math.min(mu1[j], u[i]);
                                md1[j] = Math.min(md1[j], d[i]);
                            }
                        } else {
                            for (int j = a[i]; j > b[i]; j--) {
                                mu1[j - 1] = Math.min(mu1[j - 1], d[i]);
                                md1[j - 1] = Math.min(md1[j - 1], u[i]);
                            }
                        }
                    }

                    int lo = 0, hi = 20000000;
                    while (lo < hi) {
                        int mid = (lo + hi) / 2;
                        if (can(mid)) hi = mid;
                        else lo = mid + 1;
                    }
                    ans = lo;
                }

                public boolean can(int x) {
                    int lo = Math.max(0, h[0] - x), hi = h[0] + x;
                    for (int i = 1; i < n; i++) {
                        lo -= md1[i - 1];
                        hi += mu1[i - 1];
                        int clo = Math.max(0, h[i] - x), chi = h[i] + x;
                        lo = Math.max(lo, clo);
                        hi = Math.min(hi, chi);
                        if (lo > hi) return false;
                    }
                    return true;
                }


                public void write(OutputWriter out, int testNumber) {
                    out.println("Case #" + testNumber + ": " + (ans / 2) + (ans % 2 == 1 ? ".5" : ""));
                }
            }, 16);
        }

    }

    static interface Task {
        public void read(InputReader in);

        public void solve();

        public void write(OutputWriter out, int testNumber);

    }

    static interface TaskFactory {
        public Task newTask();

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (this.numChars == -1) {
                throw new InputMismatchException();
            } else {
                if (this.curChar >= this.numChars) {
                    this.curChar = 0;

                    try {
                        this.numChars = this.stream.read(this.buf);
                    } catch (IOException var2) {
                        throw new InputMismatchException();
                    }

                    if (this.numChars <= 0) {
                        return -1;
                    }
                }

                return this.buf[this.curChar++];
            }
        }

        public int nextInt() {
            int c;
            for (c = this.read(); isSpaceChar(c); c = this.read()) {
                ;
            }

            byte sgn = 1;
            if (c == 45) {
                sgn = -1;
                c = this.read();
            }

            int res = 0;

            while (c >= 48 && c <= 57) {
                res *= 10;
                res += c - 48;
                c = this.read();
                if (isSpaceChar(c)) {
                    return res * sgn;
                }
            }

            throw new InputMismatchException();
        }

        public static boolean isSpaceChar(int c) {
            return c == 32 || c == 10 || c == 13 || c == 9 || c == -1;
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void println(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

    }

    static class Scheduler {
        private final AtomicInteger testsRemaining;
        private final AtomicInteger threadsRemaining;

        public Scheduler(InputReader in, OutputWriter out, TaskFactory factory, int numParallel) {
            try {
                testsRemaining = new AtomicInteger(in.nextInt());
                threadsRemaining = new AtomicInteger(numParallel);
                Task[] tasks = new Task[testsRemaining.get()];
                for (int i = 0; i < tasks.length; i++) {
                    tasks[i] = factory.newTask();
                }
                for (Task task : tasks) {
                    task.read(in);
                    new Thread(() -> {
                        boolean freeThread = false;
                        synchronized (this) {
                            do {
                                try {
                                    wait(10);
                                } catch (InterruptedException ignored) {
                                }
                                if (threadsRemaining.get() != 0) {
                                    synchronized (threadsRemaining) {
                                        if (threadsRemaining.get() != 0) {
                                            threadsRemaining.decrementAndGet();
                                            freeThread = true;
                                        }
                                    }
                                }
                            } while (!freeThread);
                        }
                        task.solve();
                        System.err.println(testsRemaining.decrementAndGet());
                        threadsRemaining.incrementAndGet();
                    }).start();
                }
                synchronized (this) {
                    while (testsRemaining.get() > 0) {
                        wait(10);
                    }
                }
                for (int i = 0; i < tasks.length; i++) {
                    tasks[i].write(out, i + 1);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

