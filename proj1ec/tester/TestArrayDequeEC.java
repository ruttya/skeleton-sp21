package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void addTest() {
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        int n = 10;
        for (int i = 0; i < n; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                ads1.addLast(i);
            } else {
                sad1.addFirst(i);
                ads1.addFirst(i);
            }
        }
        for (int i = 0; i < n; i++) {
            assertEquals("Should be equal.", ads1.get(i), sad1.get(i));
        }
    }

    @Test
    public void removeTest() {
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        int n = 10;
        for (int i = 0; i < n; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                ads1.addLast(i);
            } else {
                sad1.addFirst(i);
                ads1.addFirst(i);
            }
        }
        for (int i = 0; i < n; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            Integer a;
            Integer s;
            if (numberBetweenZeroAndOne < 0.5) {
                a = ads1.removeLast();
                s = sad1.removeLast();
            } else {
                a = ads1.removeFirst();
                s = sad1.removeFirst();
            }
            assertEquals("Should be equal.", a, s);
        }
    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();

        assertTrue("A newly initialized LLDeque should be empty", sad1.isEmpty());
        sad1.addFirst(1);
        assertEquals(1, sad1.size());
        assertFalse("ad1 should now contain 1 item", sad1.isEmpty());

        sad1.addLast(2);
        assertEquals(2, sad1.size());
        sad1.addLast(3);
        assertEquals(3, sad1.size());
        System.out.println("Printing out deque: ");
        sad1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", sad1.isEmpty());

        sad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", sad1.isEmpty());

        sad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal",sad1.isEmpty());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigArrayDequeTest() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        for (int i = 0; i < 1000000; i++) {
            sad1.addLast(i);
        }
        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) sad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) sad1.removeLast(), 0.0);
        }
    }

    //ruttya created Tests below

    //test case from gradescope.com
    /*d003) AD-basic: Random addFirst/removeLast/isEmpty tests. (0/8.483)
     */
    @Test
    public void d003Test() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        assertTrue("Should be true when student is initialized", student.isEmpty());
        student.addFirst(1);
        assertEquals("1st item of student should be 1", 1, (int) student.get(0));
        student.addFirst(2);
        assertEquals("1st item of student should be 2", 2, (int) student.get(0));
        student.addFirst(3);
        assertEquals("1st item of student should be 3", 3, (int) student.get(0));
        assertEquals("should be 1", 1, (int) student.removeLast());

        StudentArrayDeque<Integer> ad1 = new StudentArrayDeque<>();
        ad1.addFirst(0); //0
        ad1.addFirst(1); //1 0
        assertEquals("should be 0", 0, (int) ad1.removeLast()); //1
        assertEquals("should be 1", 1, (int) ad1.removeLast()); //null
    }

    /*d004) AD-basic: Random addLast/removeLast/isEmpty tests. (0/8.483)*/
    @Test
    public void d004Test() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
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
        StudentArrayDeque<Integer> ad1 = new StudentArrayDeque<>();
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
