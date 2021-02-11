package programmers.steppingStones;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();

    @Test
    public void solution() {
        assertEquals(s.solution(25, new int[]{2, 14, 11, 21, 17}, 2), 4);
        assertEquals(s.solution(25, new int[]{2, 14, 11, 21, 17, 24}, 3), 4);
        assertEquals(s.solution(18, new int[]{2, 8, 9, 10, 11, 12, 13}, 6), 9);
        assertEquals(s.solution(9, new int[]{2,4,5}, 2), 4);
    }
}