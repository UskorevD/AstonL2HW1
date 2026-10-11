public class CustomHashMap<K, V> {
    private static final int DEFAULT_SIZE = 16;

    private Node<K, V>[] buckets;
    private int size;
    private Node<K, V> nullKeyNode;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_SIZE];
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int getIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    public void put(K key, V value) {
        if (key == null) {
            if (nullKeyNode == null) {
                nullKeyNode = new Node<>(null, value, null);
                size++;
            } else {
                nullKeyNode.value = value;
            }
            return;
        }

        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value, buckets[index]);
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        if (key == null) {
            return nullKeyNode == null ? null : nullKeyNode.value;
        }

        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) {
            if (nullKeyNode == null) {
                return null;
            }

            V value = nullKeyNode.value;
            nullKeyNode = null;
            size--;
            return value;
        }

        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    public void display() {
        System.out.println("=== CustomHashMap ===");

        if (nullKeyNode != null) {
            System.out.println("[null key] -> " + nullKeyNode.value);
        }

        boolean isEmpty = nullKeyNode == null;

        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> current = buckets[i];

            if (current == null) {
                continue;
            }

            isEmpty = false;
            System.out.print("Bucket " + i + ": ");

            while (current != null) {
                System.out.print("[" + current.key + " -> " + current.value + "]");
                current = current.next;

                if (current != null) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
        }

        if (isEmpty) {
            System.out.println("(пусто)");
        }

        System.out.println("Количество записей: " + size);

        System.out.println("====================");
    }

    public int size() {
        return size;
    }
}
