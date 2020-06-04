package baekjoon.p1912;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void solution() {
        {
            int[] arr = {10,-4,3,1,5,6,-35,12,21,-1};
            assertEquals(33, Main.solution(arr));
        }
        {
            int[] arr = {2,1,-4,3,4,-4,6,5,-5,1};
            assertEquals(14, Main.solution(arr));
        }
        {
            int[] arr = {-1,-2,-3,-4,-5};
            assertEquals(-1, Main.solution(arr));
        }
    }
}