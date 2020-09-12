package programmers.terrainMovement;

import org.junit.Test;

import static org.junit.Assert.*;

public class Solution2Test {

    Solution2 s = new Solution2();

    @Test
    public void solution() {
        int[][] a = {{1, 4, 8, 10}, {5, 5, 5, 5}, {10, 10, 10, 10}, {10, 10, 10, 20}};
        assertEquals(s.solution(a, 3), 15);
    }
    @Test
    public void solution2() {
        int[][] a = {{10, 11, 10, 11}, {2, 21, 20, 10}, {1, 20, 21, 11}, {2, 1, 2, 1}};
        assertEquals(s.solution(a, 1), 18);
    }
}