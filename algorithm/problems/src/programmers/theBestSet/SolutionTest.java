package programmers.theBestSet;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        assertArrayEquals(s.solution(2,9), new int[]{4,5});
        assertArrayEquals(s.solution(2,1), new int[]{-1});
        assertArrayEquals(s.solution(2,8), new int[]{4,4});
    }
}