package baekjoon.p1725;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {
    int[] arr = {2, 1, 4, 5, 1, 3, 3};
    int[] arr2 = {4, 7, 6, 8, 5, 9, 1, 0};

    @Test
    public void solution() {
        assertEquals(8, Main.solution(0, arr.length - 1, arr));
        assertEquals(25, Main.solution(0, arr2.length - 1, arr2));
    }

    @Test
    public void solution2() {
        assertEquals(8, Main.solution2(arr));
        assertEquals(25, Main.solution2(arr2));
    }
}