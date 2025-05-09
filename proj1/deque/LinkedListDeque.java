package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
            //此处直接照搬SLList.java
        }
    }

    private Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(0, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /*评分机让删掉这个……
        public LinkedListDeque(LinkedListDeque other) {
            size = other.size;
            sentinel = other.sentinel;
            Node<T> p = sentinel;
            Node<T> otherp = other.sentinel;
            for (int i = 0; i < size; i++) {
                p.next = otherp.next;
                p = p.next;
                otherp = otherp.next;
            }
        }
    */
    public void addFirst(T item) {
        Node<T> p = new Node<>(item, sentinel, sentinel.next);
        sentinel.next.prev = p;
        sentinel.next = p;
        size++;
    }

    public void addLast(T item) {
        Node<T> p = new Node<>(item, sentinel.prev, sentinel);
        sentinel.prev.next = p;
        sentinel.prev = p;
        size++;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        /*Prints the items in the deque from first to last,
        separated by a space. Once all the items have been
        printed, print out a new line.*/
        Node<T> p = sentinel;
        for (int i = 0; i < size; i++) {
            p = p.next;
            System.out.print(p.item + " ");
        }
        System.out.println("");
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> p = sentinel.next; //p=head
        sentinel.next.next.prev = sentinel;
        sentinel.next = p.next;
        size--;
        return p.item;
    }

    //Removes and returns the item at the front of the deque. \
    //If no such item exists, returns null.
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> p = sentinel.prev; //p=last
        sentinel.prev = p.prev;
        sentinel.prev.next = sentinel;
        size--;
        return p.item;
    }

    //Removes and returns the item at the back of the deque.
    // If no such item exists, returns null.
    public T get(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        Node<T> p = sentinel.next;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return p.item;
            }
            p = p.next;
        }
        return null;
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node<T> pos;

        //style check判错，说下方构造函数不需要public
        LinkedListDequeIterator() {
            pos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return pos != sentinel;
        }

        @Override
        public T next() {
            T node = pos.item;
            pos = pos.next;
            return node;
        }
    }

    //equals(). use the 'instance of' keywords for this
    public boolean equals(Object o) {
        //style check 判错，使用deepseek简化版本，此写法在o为null时自动返回false
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> os = (Deque<T>) o;
        if (size != os.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            //style check 判错，使用deepseek简化版本
            if (!get(i).equals(os.get(i))) {
                return false;
            }
        }
        return true;
    }

    public T getRecursive(int index) {
        //使用递归实现的get()
        if (isEmpty() || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node<T> p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(p.next, index - 1);
    }

}
