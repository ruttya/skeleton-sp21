package flik;

import org.junit.*;

import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void test(){
        Integer a=0;
        for (Integer b=0;b<500;b++,a++){
            assertTrue("a not same as b: ",Flik.isSameNumber(a,b));
        }
        System.out.println("a="+a);
    }
}
