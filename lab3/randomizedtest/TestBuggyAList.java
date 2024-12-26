package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing alist=new AListNoResizing<>();
        BuggyAList blist=new BuggyAList<>();
        alist.addLast(4);
        blist.addLast(4);
        alist.addLast(5);
        blist.addLast(5);
        alist.addLast(6);
        blist.addLast(6);
        assertEquals(alist.removeLast(),blist.removeLast());
        assertEquals(alist.removeLast(),blist.removeLast());
        assertEquals(alist.removeLast(),blist.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B=new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber==1 && L.size()>0) {
                assertEquals(L.getLast(),B.getLast());
                System.out.println("getLast("+L.getLast()+")");
            } else if (operationNumber==2 && L.size()>0) {
                int a=L.removeLast();
                int b=B.removeLast();
                assertEquals(a,b);
                System.out.println("removeLast("+a+")");
            }
            else if (operationNumber == 3) {
                // size
                assertEquals(L.size(),B.size());
                System.out.println("size: " + L.size());
            }
        }
    }
}
