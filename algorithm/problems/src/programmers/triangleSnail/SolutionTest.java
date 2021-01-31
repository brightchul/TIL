package programmers.triangleSnail;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SolutionTest {

    Solution s = new Solution();

    @Test
    public void solution() {
        assertArrayEquals(s.solution(4), new int[]{1, 2, 9, 3, 10, 8, 4, 5, 6, 7});
        assertArrayEquals(s.solution(6), new int[]{1, 2, 15, 3, 16, 14, 4, 17, 21, 13, 5, 18, 19, 20, 12, 6, 7, 8, 9, 10, 11});
    }
}