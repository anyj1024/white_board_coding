package src.multiThreads;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LastRecentlyUsedByReentrantLock<K, V> {
    private final int capacity;
    private final ConcurrentHashMap<K, Node> map;
    private final ReentrantLock lock;
    private final Node head;
    private final Node tail;

    public LastRecentlyUsedByReentrantLock(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantLock();
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node node = map.get(key);
        if (node == null) {
            return null;
        }

        lock.lock();
        try {
            moveToHead(node);
        } finally {
            lock.unlock();
        }
        return node.value;
    }

    public void put(K key, V value) {
        Node node = map.get(key);
        if (node != null) {
            lock.lock();
            try {
                node.value = value;
                moveToHead(node);
            } finally {
                lock.unlock();
            }
        } else {
            Node newNode = new Node(key, value);
            if (map.size() >= capacity) {
                lock.lock();
                try {
                    Node tail = removeTail();
                    map.remove(tail.key);
                    addToHead(newNode);
                } finally {
                    lock.unlock();
                }
            } else {
                lock.lock();
                try {
                    addToHead(newNode);
                } finally {
                    lock.unlock();
                }
            }
            map.put(key, newNode);
        }
    }

    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }

    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node() {}

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
