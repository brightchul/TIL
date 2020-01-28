package programmers.network;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();

    @Test
    public void solution() {
        assertEquals(s.solution(3, new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}), 2);
        assertEquals(s.solution(3, new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}}), 1);
    }
}