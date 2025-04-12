package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        this.comparator=c;
    }
//此文件不要求提交，搁置……
    public T max() {
        //returns the maximum element in the deque as governed by the previously given Comparator. If the MaxArrayDeque is empty, simply return null.

        return null;
    }
    public T max(Comparator<T> c){
        //returns the maximum element in the deque as governed by the parameter Comparator c. If the MaxArrayDeque is empty, simply return null.
        return null;
    }

}
