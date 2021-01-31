package programmers.poppingBalloons;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();
    @Test
    public void solution() {
        assertEquals(s.solution(new int[]{9,-1,-5}), 3);
        assertEquals(s.solution(new int[]{-16,27,65,-2,58,-92,-71,-68,-61,-33}), 6);
    }
}