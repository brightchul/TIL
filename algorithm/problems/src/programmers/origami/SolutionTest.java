package programmers.origami;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();

    @Test
    public void solution() {
        assertArrayEquals(s.solution(1), new int[]{0});
        assertArrayEquals(s.solution(2), new int[]{0,0,1});
        assertArrayEquals(s.solution(3), new int[]{0,0,1,0,0,1,1});
    }
}