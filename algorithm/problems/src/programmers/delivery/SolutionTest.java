package programmers.delivery;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution sol = new Solution();
        int[][] arr1 = new int[][]{{1,2,1},{2,3,3},{5,2,2},{1,4,2},{5,3,1},{5,4,2}};
        assertEquals( sol.solution(5, arr1, 3), 4);
        assertEquals(sol.solution(5, arr1, 4), 5);
        assertEquals(sol.solution(5, arr1, 1), 2);
        assertEquals(sol.solution(5, arr1, 0), 1);

        int[][] arr2 = new int[][] {{1, 2, 1},{1, 3, 2}, {2, 3, 2}, {3, 4, 3}, {3, 5, 2}, {3, 5, 3}, {5, 6, 1}};
        assertEquals(sol.solution(6, arr2, 4), 4);
        assertEquals(sol.solution(6, arr2, 5), 6);
        assertEquals(sol.solution(6, arr2, 2), 3);
        assertEquals(sol.solution(6, arr2, 1), 2);
        assertEquals(sol.solution(6, arr2, 0), 1);

        int[][] arr3 = new int[][] {{1,2,2},{1,2,3},{1,2,1},{1,3,3},{1,3,2},{2,4,4},{2,5,4},{2,5,1},{3,4,3},{4,5,2},{4,5,1}};
        assertEquals(sol.solution(5, arr3, 3), 5);
        assertEquals(sol.solution(5, arr3, 2), 4);

        int[][] arr4 = new int[][] {{1,2,3},{1,3,1},{2,3,1},{2,4,1}};
        assertEquals(sol.solution(4, arr4, 3), 4);
    }

    @Test
    public void solution2() {
        Solution2 sol = new Solution2();
        int[][] arr1 = new int[][]{{1,2,1},{2,3,3},{5,2,2},{1,4,2},{5,3,1},{5,4,2}};
        assertEquals( sol.solution(5, arr1, 3), 4);
        assertEquals(sol.solution(5, arr1, 4), 5);
        assertEquals(sol.solution(5, arr1, 1), 2);
        assertEquals(sol.solution(5, arr1, 0), 1);

        int[][] arr2 = new int[][] {{1, 2, 1},{1, 3, 2}, {2, 3, 2}, {3, 4, 3}, {3, 5, 2}, {3, 5, 3}, {5, 6, 1}};
        assertEquals(sol.solution(6, arr2, 4), 4);
        assertEquals(sol.solution(6, arr2, 5), 6);
        assertEquals(sol.solution(6, arr2, 2), 3);
        assertEquals(sol.solution(6, arr2, 1), 2);
        assertEquals(sol.solution(6, arr2, 0), 1);

        int[][] arr3 = new int[][] {{1,2,2},{1,2,3},{1,2,1},{1,3,3},{1,3,2},{2,4,4},{2,5,4},{2,5,1},{3,4,3},{4,5,2},{4,5,1}};
        assertEquals(sol.solution(5, arr3, 3), 5);
        assertEquals(sol.solution(5, arr3, 2), 4);

        int[][] arr4 = new int[][] {{1,2,3},{1,3,1},{2,3,1},{2,4,1}};
        assertEquals(sol.solution(4, arr4, 3), 4);
    }
}