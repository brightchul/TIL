package programmers.GPS;

import org.junit.Test;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        {
            int n = 7;
            int m = 10;
            int[][] list = {{1, 2},{1, 3},{2, 3},{2, 4},{3, 4},{3, 5},{4, 6},{5, 6},{5, 7},{6, 7}};
            int k = 6;
            int[] log = {1, 2, 3, 3, 6, 7};
            System.out.println(s.solution(n, m, list,k, log));
        }
        {
            int n = 7;
            int m = 10;
            int[][] list = {{1, 2},{1, 3},{2, 3},{2, 4},{3, 4},{3, 5},{4, 6},{5, 6},{5, 7},{6, 7}};
            int k = 6;
            int[] log = {1, 2, 4, 6, 5, 7};
            System.out.println(s.solution(n, m, list,k, log));
        }{
            //7, 10, [[1, 2], [1, 3], [2, 3], [2, 4], [3, 4], [3, 5], [4, 6], [5, 6], [5, 7], [6, 7]], 7, [1, 1, 1, 1, 1, 1, 7]
            int n = 7;
            int m = 10;
            int[][] list = {{1, 2}, {1, 3}, {2, 3}, {2, 4}, {3, 4}, {3, 5}, {4, 6}, {5, 6}, {5, 7}, {6, 7}};
            int k = 7;
            int[] log = {1, 1, 1, 1, 1, 1, 7};
            System.out.println(s.solution(n, m, list,k, log));
        }
    }
}