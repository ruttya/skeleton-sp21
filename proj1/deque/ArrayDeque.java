package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int head;
    //为了和空队列区分，tail=末尾节点索引+1
    private int tail;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        head = 0;
        tail = 0;
        size = 0;
    }

    /*评分机让删掉这个……
        public ArrayDeque(ArrayDeque other) {
            size = other.size;
            items = (T[]) new Object[size];
            for (int i = 0; i < size; i++) {
                items[i] = (T) other.items[i];
            }
            head = 0;
            tail = Math.max(0, size - 1);
        }
    */
    /*resize 是private属性，测试临时改成public*/
    public void resize(int capacity) {
        if (capacity < size) {
            System.out.println("capacity is smaller than size, can't make resize.");
            return;
        }
        T[] a = (T[]) new Object[capacity];
        if (head < tail) {
            System.arraycopy(items, head, a, 0, size);
        }
        if (head >= tail) {
            System.arraycopy(items, 0, a, 0, tail);
            head = head + capacity - items.length;
            System.arraycopy(items, items.length - capacity + head, a, head, capacity - head);
        }
        items = a;
    }

    public void addFirst(T item) {
        // Adds an item of type T to the front of the deque. You can assume that item is never null.
        if (size == items.length) {
            resize((int) (size * 1.5 + 1));
        }
        head = (head + items.length - 1) % items.length;
        items[head] = item;
        size++;
    }

    public void addLast(T item) {
        // Adds an item of type T to the back of the deque. You can assume that item is never null.
        if (size == items.length - 1) {
            resize((int) (size * 1.5 + 1));
        }
        items[tail] = item;
        tail = (tail + 1) % items.length;
        size++;
    }

    public int size() {
        // Returns the number of items in the deque.
        return size;
    }

    public void printDeque() {
        // Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
        for (int i = 0; i < size; i++) {
            System.out.print(items[(i + head) % items.length] + " ");
        }
        System.out.println("");
    }

    public T removeFirst() {
        //Removes and returns the item at the front of the deque. If no such item exists, returns null.
        if (isEmpty()) {
            return null;
        }
        if (2 * size < items.length) {
            resize(size + 1);
        }
        T item = items[head];
        head = (head + 1) % items.length;
        size--;
        return item;
    }

    public T removeLast() {
        //Removes and returns the item at the back of the deque. If no such item exists, returns null.
        if (isEmpty()) {
            return null;
        }
        if (2 * size < items.length) {
            resize(size + 1);
        }
        T item = items[(tail + items.length - 1) % items.length];
        tail = (tail + items.length - 1) % items.length;
        size--;
        return item;
    }

    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        return items[(head + index) % items.length];
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if ((o instanceof Deque) == false) {
            return false;
        }
        ArrayDeque<T> os = (ArrayDeque<T>) o;
        if (size != os.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (get(i).equals(os.get(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;
        private int remain;//当前队列剩余元素数,(<=size)，想法来自deepseek

        public ArrayDequeIterator() {
            pos = head;
            remain = size;
        }

        @Override
        public boolean hasNext() {
            return remain > 0;
            /*自己写的版本
            if (head<tail && pos>=tail)return false;
            if (head>tail && pos>=tail && pos<head)return false;
            return true;
           */
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T item = items[pos];
            pos = (pos + 1) % items.length;
            remain--;
            return item;
        }
    }
}
