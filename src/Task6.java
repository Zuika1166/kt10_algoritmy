import java.io.BufferedInputStream;
import java.util.Arrays;

public class Task6 {
    private static int randomState = 987654321;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        int k = in.nextInt();
        long[] x = new long[n];
        long[] y = new long[n];
        long[] distance = new long[n];
        int[] number = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextLong();
            y[i] = in.nextLong();
            distance[i] = x[i] * x[i] + y[i] * y[i];
            number[i] = i + 1;
        }
        selectFirstK(x, y, distance, number, k);
        Point[] result = new Point[k];
        for (int i = 0; i < k; i++) {
            result[i] = new Point(x[i], y[i], distance[i], number[i]);
        }
        Arrays.sort(result);
        StringBuilder output = new StringBuilder();
        for (Point point : result) {
            output.append(point.x).append(' ').append(point.y).append(' ').append(point.number).append('\n');
        }
        System.out.print(output);
    }

    private static void selectFirstK(long[] x, long[] y, long[] distance, int[] number, int k) {
        int target = k - 1;
        int left = 0;
        int right = x.length - 1;
        while (left < right) {
            int pivotIndex = left + Math.floorMod(nextRandom(), right - left + 1);
            long pivotX = x[pivotIndex];
            long pivotY = y[pivotIndex];
            long pivotDistance = distance[pivotIndex];
            int pivotNumber = number[pivotIndex];
            int smaller = left;
            int current = left;
            int greater = right;
            while (current <= greater) {
                int comparison = compare(x[current], y[current], distance[current], number[current], pivotX, pivotY, pivotDistance, pivotNumber);
                if (comparison < 0) {
                    swap(x, y, distance, number, smaller++, current++);
                } else if (comparison > 0) {
                    swap(x, y, distance, number, current, greater--);
                } else {
                    current++;
                }
            }
            if (target < smaller) {
                right = smaller - 1;
            } else if (target > greater) {
                left = greater + 1;
            } else {
                return;
            }
        }
    }

    private static int nextRandom() {
        randomState ^= randomState << 13;
        randomState ^= randomState >>> 17;
        randomState ^= randomState << 5;
        return randomState;
    }

    private static int compare(long x1, long y1, long distance1, int number1, long x2, long y2, long distance2, int number2) {
        int comparison = Long.compare(distance1, distance2);
        if (comparison != 0) {
            return comparison;
        }
        comparison = Long.compare(x1, x2);
        if (comparison != 0) {
            return comparison;
        }
        comparison = Long.compare(y1, y2);
        if (comparison != 0) {
            return comparison;
        }
        return Integer.compare(number1, number2);
    }

    private static void swap(long[] x, long[] y, long[] distance, int[] number, int first, int second) {
        long value = x[first];
        x[first] = x[second];
        x[second] = value;
        value = y[first];
        y[first] = y[second];
        y[second] = value;
        value = distance[first];
        distance[first] = distance[second];
        distance[second] = value;
        int index = number[first];
        number[first] = number[second];
        number[second] = index;
    }

    private static class Point implements Comparable<Point> {
        private final long x;
        private final long y;
        private final long distance;
        private final int number;

        Point(long x, long y, long distance, int number) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.number = number;
        }

        @Override
        public int compareTo(Point other) {
            return compare(x, y, distance, number, other.x, other.y, other.distance, other.number);
        }
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
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result * sign;
        }

        int nextInt() throws Exception {
            return (int) nextLong();
        }
    }
}

