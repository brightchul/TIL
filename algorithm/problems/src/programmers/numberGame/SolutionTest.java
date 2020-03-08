package programmers.numberGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        assertEquals(s.solution(new int[]{5,1,3,7}, new int[]{2, 2, 6, 8}),3);
        assertEquals(s.solution(new int[]{2,2,2,2}, new int[]{1,1,1,1}),0);
        assertEquals(s.solution(new int[]{3,4,5,6,7,8}, new int[]{2, 3, 4, 5, 6, 7}),4);
    }
    @Test
    public void solution2() {
        Solution s = new Solution();
        assertEquals(s.solution2(new int[]{5,1,3,7}, new int[]{2, 2, 6, 8}),3);
        assertEquals(s.solution2(new int[]{2,2,2,2}, new int[]{1,1,1,1}),0);
        assertEquals(s.solution2(new int[]{3,4,5,6,7,8}, new int[]{2, 3, 4, 5, 6, 7}),4);
    }
}