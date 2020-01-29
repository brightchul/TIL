package programmers.theFarestNode;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();
    @Test
    public void solution() {
        int[][] arr = new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};
        assertEquals(s.solution(6, arr), 3);
    }
}