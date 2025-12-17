package MiniProject;
import java.util.Arrays;

class PriorityQueues<T extends Comparable<T>> {

    private T[] heap;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public PriorityQueues() {
        this.capacity = 11;
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public PriorityQueues(int initialCapacity) {
        this.capacity = initialCapacity;
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            @SuppressWarnings("unchecked")
            T[] newHeap = (T[]) new Comparable[capacity];
            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
    }

    public void add(T value) {
        ensureCapacity();
        heap[size] = value;
        heapUp(size);
        size++;
    }

    public boolean offer(T value) {
        add(value);
        return true;
    }

    public T peek() {
        if (size == 0){
            throw new RuntimeException("Queue is empty");
        }
        return heap[0];
    }

    public T poll() {
        if (size == 0){
            throw new RuntimeException("Queue is empty");
        }
        T root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        if (size > 0){
            heapDown(0);
        }
        return root;
    }

    private void heapUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) < 0) {
                T temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                index = parent;
            } else{
                 break;
            }
        }
    }

    private void heapDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0){
                smallest = left;
            }

            if (right < size && heap[right].compareTo(heap[smallest]) < 0){
                smallest = right;
            }

            if (smallest == index){
                 break;
            }

            T temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;
            index = smallest;
        }
    }

    public boolean remove(T value) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(value)) {
                index = i;
                break;
            }
        }

        if (index == -1){
            return false;
        }

        heap[index] = heap[size - 1];
        size--;
        if (index < size) {
            heapUp(index);
            heapDown(index);
        }

        return true;
    }

    public void clear() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
    if (size == 0) {
        return "[]";
    }

    String result = "[";

    for (int i = 0; i < size; i++) {
        result += heap[i];
        if (i < size - 1) {
            result += ", ";
        }
    }

        result += "]";
        return result;
    }

    public Iterators<T> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterators<T> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (!hasNext()){
                throw new RuntimeException("No more elements");
            }
            return heap[cursor++];
        }
    }

    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) Arrays.copyOf(heap, size);
    }
}

interface Iterators<T> {
    boolean hasNext();
    T next();
}
