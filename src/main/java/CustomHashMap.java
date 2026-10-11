import java.util.Objects;

public class CustomHashMap<K, V> {
    private static final int DEFAULT_SIZE = 16;
    private static final float LOAD_FACTOR = 0.75f;

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

        if ((float) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
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

        System.out.println("====================");
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = (Node<K, V>[]) new Node[oldBuckets.length * 2];

        for (Node<K, V> head : oldBuckets) {
            Node<K, V> current = head;

            while (current != null) {
                Node<K, V> next = current.next;

                int index = getIndex(current.key);

                current.next = buckets[index];
                buckets[index] = current;

                current = next;
            }
        }
    }

    public int capacity() {
        return buckets.length;
    }

    public boolean containsKey(K key) {
        if (key == null) {
            return nullKeyNode != null;
        }

        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CustomHashMap<?, ?> other)) {
            return false;
        }

        if (size != other.size) {
            return false;
        }

        if (nullKeyNode != null) {
            if (other.nullKeyNode == null
                    || !Objects.equals(
                    nullKeyNode.value, other.nullKeyNode.value)) {
                return false;
            }
        } else if (other.nullKeyNode != null) {
            return false;
        }

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;

            while (current != null) {
                Node<?, ?> otherNode = other.findNode(current.key);

                if (otherNode == null
                        || !Objects.equals(
                        current.value, otherNode.value)) {
                    return false;
                }

                current = current.next;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;

        if (nullKeyNode != null) {
            result += Objects.hashCode(nullKeyNode.value);
        }

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;

            while (current != null) {
                result += current.key.hashCode()
                        ^ Objects.hashCode(current.value);

                current = current.next;
            }
        }

        return result;
    }

    private Node<K, V> findNode(Object key) {
        if (key == null) {
            return nullKeyNode;
        }

        int index = Math.floorMod(key.hashCode(), buckets.length);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }
            current = current.next;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_SIZE];
        nullKeyNode = null;
        size = 0;
    }
}
