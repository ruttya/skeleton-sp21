package deque;

import java.util.Comparator;

//此文件不要求提交，未debug
public class MaxLinkedListDeque<T> extends LinkedListDeque<T> {
    private Comparator<T> comparator;

    public MaxLinkedListDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        /* returns the maximum element in the deque as governed
        by the previously given Comparator. If the MaxArrayDeque
        is empty, simply return null. */
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        /* returns the maximum element in the deque as
        governed by the parameter Comparator c.
        If the MaxArrayDeque is empty, simply return null.
         */
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (T elem : this) {
            if (c.compare(elem, max) > 0) {
                max = elem;
            }
        }
        return max;
    }
}
