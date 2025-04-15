package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        /* returns the maximum element in the deque as governed by
        the previously given Comparator. If the MaxArrayDeque is
        empty, simply return null.
         */
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        /* returns the maximum element in the deque as governed by
        the parameter Comparator c. If the MaxArrayDeque is empty,
        simply return null.
         */
        if (isEmpty()) {
            return null;
        }

        T max = get(0);
        for (T element : this) {
            //java也有这种用法啊
            if (c.compare(element, max) > 0) {
                max = element;
            }
        }
        return max;
    }

}
