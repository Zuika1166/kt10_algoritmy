import java.io.BufferedInputStream;
import java.util.Arrays;

public class Task1 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        long[] requests = new long[n];
        for (int i = 0; i < n; i++) {
            long left = in.nextLong();
            long right = in.nextLong();
            requests[i] = (right << 32) | left;
        }
        Arrays.sort(requests);
        long lastEnd = -1;
        int answer = 0;
        for (long request : requests) {
            long left = request & 0xffffffffL;
            long right = request >>> 32;
            if (left >= lastEnd) {
                answer++;
                lastEnd = right;
            }
        }
        System.out.println(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws Exception {
            if (position >= length) {
                length = input.read(buffer);
                position = 0;
                if (length <= 0) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        long nextLong() throws Exception {
            int c;
            do {
                c = read();
            } while (c <= 32);
            long result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result;
        }

        int nextInt() throws Exception {
            return (int) nextLong();
        }
    }
}

