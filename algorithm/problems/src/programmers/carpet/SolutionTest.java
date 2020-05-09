package programmers.carpet;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        assertArrayEquals(s.solution(10, 2), new int[]{4,3});
        assertArrayEquals(s.solution(8,1), new int[]{3,3});
        assertArrayEquals(s.solution(24,24), new int[]{8,6});
        assertArrayEquals(s.solution(18, 6), new int[]{8,3});
    }
}