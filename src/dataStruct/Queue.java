package src.dataStruct;

public class Queue<T> {
    private T[] elements;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        elements = (T[]) new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(T element) {
        if (isFull()) return;
        rear = (rear + 1) % capacity;
        elements[rear] = element;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T element = elements[front];
        elements[front] = null;
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    public T peek() {
        if (isEmpty()) return null;
        return elements[front];
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
