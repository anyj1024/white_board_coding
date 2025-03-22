package src.dataStruct;

public class BitMap {
    private byte[] bits;
    private int size;

    public BitMap(int size) {
        this.size = size;
        this.bits = new byte[(size + 7) / 8];
    }

    public void set(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds.");
        }
        bits[index / 8] |= (1 << (index % 8));
    }

    private void clear(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds.");
        }
        bits[index / 8] &= ~(1 << (index % 8));
    }

    private boolean get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds.");
        }
        return (bits[index / 8 ] & (1 << (index % 8))) != 0;
    }

    public int getSize() {
        return size;
    }
}
