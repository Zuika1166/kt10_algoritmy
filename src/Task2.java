import java.io.BufferedInputStream;

public class Task2 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int q = in.nextInt();
        int[] values = new int[q];
        int[] minimums = new int[q];
        int[] maximums = new int[q];
        int size = 0;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < q; i++) {
            String command = in.next();
            if (command.charAt(0) == 'P' && command.length() == 4) {
                int value = in.nextInt();
                values[size] = value;
                minimums[size] = size == 0 ? value : Math.min(value, minimums[size - 1]);
                maximums[size] = size == 0 ? value : Math.max(value, maximums[size - 1]);
                size++;
            } else if (command.charAt(0) == 'P') {
                size--;
            } else if (command.charAt(1) == 'I') {
                output.append(minimums[size - 1]).append('\n');
            } else {
                output.append(maximums[size - 1]).append('\n');
            }
        }
        System.out.print(output);
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

        String next() throws Exception {
            int c;
            do {
                c = read();
            } while (c <= 32);
            StringBuilder result = new StringBuilder();
            while (c > 32) {
                result.append((char) c);
                c = read();
            }
            return result.toString();
        }

        int nextInt() throws Exception {
            int c;
            do {
                c = read();
            } while (c <= 32);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result * sign;
        }
    }
}

