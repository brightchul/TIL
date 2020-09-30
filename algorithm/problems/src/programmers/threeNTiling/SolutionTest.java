package programmers.threeNTiling;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    Solution s = new Solution();

    @Test
    public void solution() {
        assertEquals(11, s.solution(4));
        assertEquals(41, s.solution(6));
    }
}