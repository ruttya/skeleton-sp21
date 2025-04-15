package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    Comparator<Integer> c = Integer::compareTo;
    /*deepseek提供的两种比较器*/
    // 场景1：按字符串长度比较
    Comparator<String> lengthComp = Comparator.comparingInt(String::length);

    // 场景2：字符串等长的前提下，按字母顺序比较
    Comparator<String> alphabeticComp = String::compareTo;

    @Test
    public void maxIntTest() {
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(c);
        mad1.addFirst(1);
        mad1.addFirst(2);
        mad1.addFirst(3);
        assertEquals("Max item should be 3. ", 3, (int) mad1.max());
    }

    @Test
    public void maxStrTest() {
        MaxArrayDeque<String> mad1 = new MaxArrayDeque<>(lengthComp);
        mad1.addLast("front");
        mad1.addLast("middle");
        mad1.addLast("mjddle");
        mad1.addLast("end");
        assertEquals("longest item should be \"middle\". ", "middle", mad1.max());
        assertEquals("max alpha item should be \"mjddle\". ", "mjddle", mad1.max(alphabeticComp));
    }
}
