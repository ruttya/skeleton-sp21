package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int head;
    private int tail;
    private int size;
    public ArrayDeque(){
        items=(T[])new Object[8];
        head=0;
        tail=0;
        size=0;
    }
    public ArrayDeque(ArrayDeque other){
        size=other.size;
        items=(T[])new Object[size];
        for (int i=0;i<size;i++){
            items[i]=(T) other.items[i];
        }
        head=0;
        tail=Math.max(0,size-1);
    }
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (head<tail){
            System.arraycopy(items, 0, a, 0, size);
        }
        if (head>tail){
            System.arraycopy(items, 0, a, 0, tail+1);
            head=head+capacity-items.length;
            System.arraycopy(items, head, a, head, capacity-head+1);
        }
        items = a;
    }
    public void addFirst(T item){
        // Adds an item of type T to the front of the deque. You can assume that item is never null.
        if (size == items.length) {
            resize((int)(size*1.01));
        }
        head=(head+items.length-1)%items.length;
        items[head]=item;
        size++;
    }
    public void addLast(T item){
        // Adds an item of type T to the back of the deque. You can assume that item is never null.
        if (size == items.length) {
            resize((int)(size*1.01));
        }
        items[tail] = item;
        tail=(tail+1)%items.length;
        size++;
    }
    public int size(){
        // Returns the number of items in the deque.
        return size;
    }
    public void printDeque() {
        // Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
        for (int i=0;i<size;i++){
            System.out.print(items[(i+head)%items.length]+" ");
        }
        System.out.println("");
    }
    public T removeFirst(){
        //Removes and returns the item at the front of the deque. If no such item exists, returns null.
        if (isEmpty())return null;
        T item=items[head];
        head=(head+1)%items.length;
        size--;
        return item;
    }
    public T removeLast(){
        //Removes and returns the item at the back of the deque. If no such item exists, returns null.
        if (isEmpty())return null;
        T item = items[tail];
        tail=(tail+items.length-1)%items.length;
        size--;
        return item;
    }
    public T get(int index){
        return items[(head+index)%items.length];
    }
/*
    public boolean equals(Object o) {
        if (o==null)return false;
        //use the 'instance of' keywords for this
        if (o instanceof ArrayDeque && size==((ArrayDeque<?>) o).size){
            for (int i=0;i<size;i++){
                if(items[(head+items.length)%items.length].equals(o.items[(head+o.items.length)%o.items.length])==false){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

 */
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T>{
        private int pos;
        private int remain;//当前队列剩余元素数,(<=size)，想法来自deepseek
        public ArrayDequeIterator() {
            pos=head;
            remain=size;
        }

        @Override
        public boolean hasNext() {
            return remain>0;
            /*自己写的版本
            if (head<tail && pos>=tail)return false;
            if (head>tail && pos>=tail && pos<head)return false;
            return true;
           */
        }

        @Override
        public T next() {
            if (!hasNext()) return null;
            T item=items[pos];
            pos=(pos+1)%items.length;
            remain--;
            return item;
        }
    }
}
