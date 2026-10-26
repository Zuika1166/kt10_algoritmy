import java.io.BufferedInputStream;

public class Task3 {
    private static int randomState = 123456789;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        int target = in.nextInt() - 1;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = in.nextInt();
        }
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int pivot = values[left + Math.floorMod(nextRandom(), right - left + 1)];
            int smaller = left;
            int current = left;
            int greater = right;
            while (current <= greater) {
                if (values[current] < pivot) {
                    swap(values, smaller++, current++);
                } else if (values[current] > pivot) {
                    swap(values, current, greater--);
                } else {
                    current++;
                }
            }
            if (target < smaller) {
                right = smaller - 1;
            } else if (target > greater) {
                left = greater + 1;
            } else {
                break;
            }
        }
        System.out.println(values[target]);
    }

    private static int nextRandom() {
        randomState ^= randomState << 13;
        randomState ^= randomState >>> 17;
        randomState ^= randomState << 5;
        return randomState;
    }

    private static void swap(int[] values, int first, int second) {
        int value = values[first];
        values[first] = values[second];
        values[second] = value;
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

