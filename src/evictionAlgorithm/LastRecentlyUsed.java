package src.evictionAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class LastRecentlyUsed {

    public class LRUCache<K,V> {
        private final int capacity;
        private final Deque deque;
        private final Map<K,Node> map;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            deque = new Deque();
            map = new HashMap<>();
        }

        public V get(K key) {
            if (!map.containsKey(key)) return null;
            Node node = map.get(key);
            deque.moveToHead(node);
            return node.value;
        }

        public void put(K key, V value) {
            if (map.containsKey(key)) {
                deque.remove(map.get(key));
            }
            Node node = new Node(key, value);
            deque.addToHead(node);
            map.put(key, node);
            if (map.size() > capacity) {
                Node del = deque.removeLast();
                map.remove(del.key);
            }
        }

        private class Deque {
            Node head;
            Node tail;
            int size;

            public Deque() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.prev = head;
                size = 0;
            }

            void addToHead(Node node) {
                node.next = head.next;
                head.next.prev = node;
                head.next = node;
                node.prev = head;
                size++;
            }

            void remove(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            Node removeLast() {
                if (tail.prev == head) return null;
                Node node = tail.prev;
                remove(node);
                return node;
            }

            void moveToHead(Node node) {
                remove(node);
                addToHead(node);
            }
        }

        private class Node {
            K key;
            V value;
            Node prev;
            Node next;

            Node(){}

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }

            Node(K key, V value, Node prev, Node next) {
                this.key = key;
                this.value = value;
                this.prev = prev;
                this.next = next;
            }
        }
    }
}
