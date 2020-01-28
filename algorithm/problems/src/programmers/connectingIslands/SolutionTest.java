package programmers.connectingIslands;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();
    @Test
    public void solution() {
        assertEquals(s.solution(4, new int[][]	{{0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}}),4);
    }
}