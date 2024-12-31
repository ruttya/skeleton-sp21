package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node p,Node n) {
            item = i;
            prev =p;
            next = n;
        }
    }
    /* The first item (if it exists) is at sentinel.next. */
    private Node sentinel;
    private int size;
    public LinkedListDeque(){
        sentinel=new Node(null,null,null);
        size=0;
    }
    public LinkedListDeque(T x){
        sentinel=new Node(null,null,null);
        sentinel.next=new Node(x,sentinel,null);
        sentinel.prev=sentinel.next;
        size=1;
    }
    public void addFirst(T x){
        Node p=sentinel.next;
        sentinel.next = new Node(x, sentinel,sentinel.next);
        size = size + 1;
    }
    public void addLast(T x){
        size = size + 1;
        Node p = sentinel;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new Node(x, p,null);
    }
    public boolean isEmpty(){
        return sentinel.next==null;
    }
    public int size(){
        int n=0;
        if (sentinel.next==null){
            return n;
        }
        Node p =sentinel;
        while (p.next!=null){
            n++;
            p = p.next;
        }
        return n;
    }
    public void printDeque(){
        Node p =sentinel;
        while (p.next !=null){
            p = p.next;
            System.out.print(p.item+" ");
        }
        System.out.println("");
    }
    public T removeFirst(){
        Node p=sentinel;
        if(p.next==null){
            return null;
        }else{
            p=p.next;
            sentinel=new Node(null, sentinel,p.next);
            size=size-1;
            return p.item;
        }
    }
    public T removeLast(){
        Node p=sentinel;
        if(p.next==null){
            return null;
        }else{
            p=p.prev;
            Node x=p;
            if (p==null){
                return removeFirst();
            }else{
                p=p.prev;
                p=new Node(p.item,sentinel,sentinel.next);
                size=size-1;
                return x.item;
            }
        }
    }
    public T get(int index){
        if (index>=size){
            return null;
        }else {
            Node p=sentinel;
            for(int i=0;i<index;i++){
                p=p.next;
            }
            return p.item;
        }
    }
    private Node getNode(int index){
        if (index>=size){
            return null;
        }else if (index==0){
            return sentinel.next;
        }
        else return getNode(index-1).next;
    }
    public T getRecursive(int index){
            return getNode(index-1).next.item;
    }
    /** todo:The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     * Iterator is in lecture 11 (2/12)
     */
    public Iterator<T> iterator(){
        return newIterator();
    }
    public Iterator<T> newIterator(){
        Iterator<T> res=newIterator();
        return res;
    }
    public boolean equals(Object o){
        while (o instanceof LinkedListDeque){
            if (size != ((LinkedListDeque<?>) o).size()) return false;
            else {
                for (int i = 0; i < size; i++) {
                    if (get(i) != ((LinkedListDeque<?>) o).get(i))
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}
