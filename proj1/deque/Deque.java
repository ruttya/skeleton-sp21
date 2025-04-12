package deque;

public interface Deque<T> {
    public void addFirst(T item);// Adds an item of type T to the front of the deque. You can assume that item is never null.
    public void addLast(T item);// Adds an item of type T to the back of the deque. You can assume that item is never null.
    default boolean isEmpty(){
        return size()==0;
    }// Returns true if deque is empty, false otherwise.
    public int size();// Returns the number of items in the deque.
    public void printDeque();// Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
    public T removeFirst();//Removes and returns the item at the front of the deque. If no such item exists, returns null.
    public T removeLast();//Removes and returns the item at the back of the deque. If no such item exists, returns null.
    public T get(int index);

}
