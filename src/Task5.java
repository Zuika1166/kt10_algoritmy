import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;

public class Task5 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int capacity = in.nextInt();
        int q = in.nextInt();
        Cache cache = new Cache(capacity);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < q; i++) {
            String command = in.next();
            int key = in.nextInt();
            if (command.charAt(0) == 'G') {
                output.append(cache.get(key)).append('\n');
            } else {
                int value = in.nextInt();
                cache.put(key, value);
            }
        }
        System.out.print(output);
    }

    private static class Cache {
        private final int capacity;
        private final Map<Integer, Node> nodes = new HashMap<>();
        private final Map<Integer, Bucket> buckets = new HashMap<>();
        private int minimumFrequency;

        Cache(int capacity) {
            this.capacity = capacity;
        }

        int get(int key) {
            Node node = nodes.get(key);
            if (node == null) {
                return -1;
            }
            increaseFrequency(node);
            return node.value;
        }

        void put(int key, int value) {
            Node node = nodes.get(key);
            if (node != null) {
                node.value = value;
                increaseFrequency(node);
                return;
            }
            if (nodes.size() == capacity) {
                Bucket bucket = buckets.get(minimumFrequency);
                Node removed = bucket.removeLast();
                nodes.remove(removed.key);
            }
            Node added = new Node(key, value);
            buckets.computeIfAbsent(1, ignored -> new Bucket()).addFirst(added);
            nodes.put(key, added);
            minimumFrequency = 1;
        }

        private void increaseFrequency(Node node) {
            Bucket oldBucket = buckets.get(node.frequency);
            oldBucket.remove(node);
            if (node.frequency == minimumFrequency && oldBucket.isEmpty()) {
                minimumFrequency++;
            }
            node.frequency++;
            buckets.computeIfAbsent(node.frequency, ignored -> new Bucket()).addFirst(node);
        }
    }

    private static class Node {
        private final int key;
        private int value;
        private int frequency = 1;
        private Node previous;
        private Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class Bucket {
        private Node first;
        private Node last;

        void addFirst(Node node) {
            node.previous = null;
            node.next = first;
            if (first != null) {
                first.previous = node;
            } else {
                last = node;
            }
            first = node;
        }

        void remove(Node node) {
            if (node.previous != null) {
                node.previous.next = node.next;
            } else {
                first = node.next;
            }
            if (node.next != null) {
                node.next.previous = node.previous;
            } else {
                last = node.previous;
            }
            node.previous = null;
            node.next = null;
        }

        Node removeLast() {
            Node node = last;
            remove(node);
            return node;
        }

        boolean isEmpty() {
            return first == null;
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
            int result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result;
        }
    }
}

