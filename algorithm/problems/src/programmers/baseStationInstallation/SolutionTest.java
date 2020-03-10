package programmers.baseStationInstallation;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        assertEquals(s.solution(11, new int[]{4, 11}, 1), 3);
        assertEquals(s.solution(16, new int[]{9}, 2), 3);
        assertEquals(s.solution(10, new int[]{1}, 10), 0);
        assertEquals(s.solution(10, new int[]{1}, 9), 0);
        assertEquals(s.solution(10, new int[]{5}, 5), 0);
        assertEquals(s.solution(10, new int[]{1, 10}, 3), 1);
        assertEquals(s.solution(10, new int[]{1, 10}, 2), 1);
        assertEquals(s.solution(10, new int[]{1, 10}, 1), 2);
        assertEquals(s.solution(10, new int[]{1}, 8), 1);

    }
}