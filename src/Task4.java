import java.io.BufferedInputStream;
import java.util.Arrays;

public class Task4 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        long[] orders = new long[n];
        for (int i = 0; i < n; i++) {
            long deadline = in.nextLong();
            long profit = in.nextLong();
            orders[i] = (profit << 32) | deadline;
        }
        Arrays.sort(orders);
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        long answer = 0;
        for (int i = n - 1; i >= 0; i--) {
            long order = orders[i];
            long profit = order >>> 32;
            int deadline = (int) (order & 0xffffffffL);
            int slot = find(parent, Math.min(n, deadline));
            if (slot > 0) {
                answer += profit;
                parent[slot] = find(parent, slot - 1);
            }
        }
        System.out.println(answer);
    }

    private static int find(int[] parent, int vertex) {
        int root = vertex;
        while (parent[root] != root) {
            root = parent[root];
        }
        while (vertex != root) {
            int next = parent[vertex];
            parent[vertex] = root;
            vertex = next;
        }
        return root;
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

