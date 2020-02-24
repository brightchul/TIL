package programmers.moreSpicy;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution sol = new Solution();
    @Test
    public void solution() {
        assertEquals(sol.solution(new int[]{1, 2, 3, 9, 10, 12}, 7), 2);
        assertEquals(sol.solution(new int[]{0, 0, 0, 0, 0, 0, 0, 0}, 10), -1);
        assertEquals(sol.solution(new int[]{10}, 20), -1);
        assertEquals(sol.solution(new int[]{10, 20}, 15), 1);
        assertEquals(sol.solution(new int[]{1}, 0), 0);
        assertEquals(sol.solution(new int[]{1, 2}, 3), 1);
        assertEquals(sol.solution(new int[]{0,0,1}, 2), 2);
        assertEquals(sol.solution(new int[]{0,0,1}, 3), -1);
        assertEquals(sol.solution(new int[]{1, 2, 3}, 11), 2);
    }
}