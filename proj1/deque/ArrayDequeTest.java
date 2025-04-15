package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class ArrayDequeTest {

    public ArrayDeque addNums(ArrayDeque orig, int num) {
        for (int i = 1; i <= num; i++) {
            orig.addLast(i);
        }
        return orig;
    }

    @Test
    public void addLastTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.printDeque();
    }

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1 = addNums(ad1, 7);
        /*resize()是private方法，测试临时改成public*/
        //ad1.resize(20);
        ad1.printDeque();
    }

    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1 = addNums(ad1, 7);
        ad1.addLast(8);
        ad1.addFirst(101);
        ad1.addFirst(102);
        ad1.printDeque();
    }

    @Test
    public void removeTest() {
        //测试非常规形状队列的remove方法
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1 = addNums(ad1, 10);
        ad1.addFirst(101);
        ad1.addFirst(102);
        ad1.removeLast();
        ad1.removeFirst();
        ad1.printDeque();
    }

    @Test
    public void resizeSmaller() {
        //测试常规形状队列使用resize()缩小容量
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1 = addNums(ad1, 10);
        ad1.addLast(11);
        /*resize()是private方法，测试临时改成public*/
        //ad1.resize(12);
        ad1.printDeque();

        //测试非常规形状队列使用resize()缩小容量
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ad2 = addNums(ad2, 7);
        ad2.addFirst(101);
        ad2.addFirst(102);
        /*resize()是private方法，测试临时改成public*/
        //ad2.resize(20);//手动扩容为非常规形态
        /*resize()是private方法，测试临时改成public*/
        //ad2.resize(12);
        ad2.printDeque();
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ad1 = addNums(ad1, 3);
        ad2 = addNums(ad2, 3);
        assertTrue("Should be true as ad1 equals ad2", ad1.equals(ad2));
        ad2.removeLast();
        assertFalse("Should be false as ad2 is modified", ad1.equals(ad2));
        ad1.removeLast();
        ad2.removeLast();
        ad1.removeLast();
        ad2.removeLast();
        ad1.removeLast();
        assertTrue("Should be true as ad1 and ad2 are empty", ad1.equals(ad2));

        //比较索引不同的队列
        ArrayDeque<Integer> ad3 = new ArrayDeque<>();
        ArrayDeque<Integer> ad4 = new ArrayDeque<>();
        /*ad3: 1 2 3 n n n n n */
        ad3 = addNums(ad3, 3);
        /*ad4: 3 n n n n n 1 2 */
        ad4.addLast(3);
        ad4.addFirst(2);
        ad4.addFirst(1);
        assertTrue("Should be true as ad3 equals ad4", ad3.equals(ad4));
    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> ad1 = new ArrayDeque<>();

        assertTrue("A newly initialized LLDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");
        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());
        ad1.addLast("back");
        assertEquals(3, ad1.size());
        System.out.println("Printing out deque: ");
        ad1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        // System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String> lld1 = new ArrayDeque<String>();
        ArrayDeque<Double> lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigArrayDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }
        lld1.printDeque();
        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    //ruttya created Tests below
    @Test
    /*copy method test*/
    public void copyLLDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        assertEquals("Should have the same value", null, lld2.get(0));

        ArrayDeque<Integer> lld3 = new ArrayDeque<>();
        lld3.addLast(1);
        lld3.addLast(2);
        lld3.addLast(3);
        ArrayDeque<Integer> lld4 = new ArrayDeque<>();
        lld4.addLast(1);
        lld4.addLast(2);
        lld4.addLast(3);
        for (int i = 0; i < lld4.size(); i++) {
            //System.out.println(lld3.get(i)+","+lld4.get(i));
            assertEquals("Should have the same value", lld3.get(i), lld4.get(i));
        }
    }

    //test case from gradescope.com
    /*d003) AD-basic: Random addFirst/removeLast/isEmpty tests. (0/8.483)
     */
    @Test
    public void d003Test() {
        ArrayDeque<Integer> student = new ArrayDeque<>();
        assertTrue("Should be true when student is initialized", student.isEmpty());
        student.addFirst(1);
        assertEquals("1st item of student should be 1", 1, (int) student.get(0));
        student.addFirst(2);
        assertEquals("1st item of student should be 2", 2, (int) student.get(0));
        student.addFirst(3);
        assertEquals("1st item of student should be 3", 3, (int) student.get(0));
        assertEquals("should be 1", 1, (int) student.removeLast());
    }

    /*d004) AD-basic: Random addLast/removeLast/isEmpty tests. (0/8.483)*/
    @Test
    public void d004Test() {
        ArrayDeque<Integer> student = new ArrayDeque<>();
        student.addLast(0);
        student.addLast(1);
        student.addLast(2);
        student.addLast(3);
        student.addLast(4);
        student.addLast(5);
        student.isEmpty();
        student.addLast(7);
        student.addLast(8);
        assertEquals("should be 8", 8, (int) student.removeLast());
    }

    /*
     * d011) AD-basic: get. (0/8.483)*/
    @Test
    public void d011Test() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(0); //0
        ad1.addLast(1); //0 1
        ad1.get(1);//      ==> 1
        ad1.addLast(3); //0 1 3
        ad1.get(2); //      ==> 3
        ad1.get(1); //      ==> 1
        assertEquals("should be 3", 3, (int) ad1.removeLast()); //0 1
        ad1.addFirst(7); //7 0 1
        assertEquals("should be 1", 1, (int) ad1.removeLast()); //7 0
        ad1.addLast(9); //7 0 9
        assertEquals("should be 7", 7, (int) ad1.removeFirst()); //0 9
        assertEquals("should be 9", 9, (int) ad1.removeLast()); //0
        assertEquals("should be 0", 0, (int) ad1.removeLast()); //null
        ad1.addFirst(13); //13
        assertEquals("should be 13", 13, (int) ad1.removeLast()); // null
        ad1.addFirst(15); //15
        ad1.get(0); //      ==> 15
        ad1.addFirst(17); //17 15
        assertEquals("should be 15", 15, (int) ad1.removeLast()); //17
        ad1.addLast(19); //17 19
        ad1.addLast(20); //17 19 20
        assertEquals("should be 17", 17, (int) ad1.removeFirst()); //19 20
    }
}