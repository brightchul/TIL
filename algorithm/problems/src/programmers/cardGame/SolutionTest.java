package programmers.cardGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        int[] left = {3,2,5};
        int[] right = {2,4,1};
        assertEquals(s.solution(left, right), 7);
    }
}