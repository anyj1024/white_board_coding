package src.dataStruct;

public class Stack<T> {
    private T[] arr;
    private int top;
    private int capacity;

    public Stack(int capacity) {
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
        top = -1;
    }

    public void push(T t) {
        if (top == capacity - 1) {
            return;
        }
        arr[++top] = t;
    }

    public T pop() {
        if (top == -1) {
            return null;
        }
        return arr[top--];
    }

    public T peek() {
        if (top == -1) {
            return null;
        }
        return arr[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
