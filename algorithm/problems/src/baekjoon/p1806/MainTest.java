package baekjoon.p1806;

import org.junit.Test;

import static org.junit.Assert.*;


public class MainTest {

    int[] case1 = {1,1,1,1,1,1,1,1,1,8};
    int[] case2 = {5,1,3,5,10,7,4,9,2,8};

    @Test
    public void run() {

        assertEquals(Main.run(case1, 9), 2);
        assertEquals(Main.run(case1, 8), 1);
        assertEquals(Main.run(case1, 1), 1);
        assertEquals(Main.run(case1, 3), 1);    // 8이 3보다 크거나 같으므로 1
        assertEquals(Main.run(case1, 100), 0);
        assertEquals(Main.run(case2, 15), 2);

    }

    @Test
    public void run2() {
        assertEquals(Main.run2(case1, 9), 2);
        assertEquals(Main.run2(case1, 8), 1);
        assertEquals(Main.run2(case1, 1), 1);
        assertEquals(Main.run2(case1, 3), 1);
        assertEquals(Main.run2(case1, 100), 0);
        assertEquals(Main.run2(case2, 15), 2);
    }
}