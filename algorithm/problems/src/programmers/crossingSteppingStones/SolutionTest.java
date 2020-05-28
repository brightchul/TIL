package programmers.crossingSteppingStones;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s= new Solution();
        {
            int[] arr = {2, 4, 5, 3, 2, 1, 4, 2, 5, 1};
            assertEquals(3, s.solution(arr, 3));
        }
        {
            int[] arr = {1,1,1,1, 2, 4, 5, 3, 2, 1, 4, 2, 5, 1,1,1,1,1,1,1,1,1,1,1,1};
            assertEquals(1, s.solution(arr, 3));
        }
        {
            int[] arr = {1,1000000000, 2, 3, 1, 2, 3};
            assertEquals(3, s.solution(arr, 5));
        }
        {
            int[] arr = {1,1000000000, 2, 3, 1, 2, 3};
            assertEquals(3, s.solution(arr, 3));
        }

    }

    @Test
    public void solution2() {
        Solution2 s = new Solution2();
        int[] arr = {2, 4, 5, 3, 2, 1, 4, 2, 5, 1};
        assertEquals(3, s.solution(arr, 3));
    }
}