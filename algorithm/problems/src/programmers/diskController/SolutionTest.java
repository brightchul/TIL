package programmers.diskController;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        int[][] arr = {{0, 3}, {1, 9}, {2, 6}};
        Solution sol = new Solution();
        assertEquals(sol.solution(arr), 9);
    }
    @Test
    public void solution1() {
        Solution sol = new Solution();
        int[][] ar1 = {{0,3}, {6, 3}};
        assertEquals(sol.solution(ar1), 3);
        int[][] ar2 = {{24, 10}, {18, 39}, {34, 20}, {37, 5}, {47, 22}, {20, 47}, {15, 2}, {15, 34}, {35, 43}, {26, 1}};
        assertEquals(sol.solution(ar2), 74);
    }
}