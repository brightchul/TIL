package programmers.nightWork;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        assertEquals(
                s.solution(4, new int[]{4,3,3}), 12);
        assertEquals(s.solution(1, new int[]{2,1,2}), 6);
        assertEquals(
                s.solution(3, new int[]{1,1}), 0);
        assertEquals(s.solution(3, new int[]{1,2,1,2,1,2}), 6);
        assertEquals(s.solution(0, new int[]{1,2,1,2,1,2}), 15);
        assertEquals(s.solution(0, new int[]{1,5,1}), 27);
        assertEquals(
                s.solution(5, new int[]{1,2,5,6}), 23);

    }
}