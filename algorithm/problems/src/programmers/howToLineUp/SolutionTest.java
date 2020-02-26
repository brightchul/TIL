package programmers.howToLineUp;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();

    @Test
    public void solution() {
        assertEquals(str(s.solution(3,5)), "[3, 1, 2]");
        assertEquals(str(s.solution(3,1)), "[1, 2, 3]");
        assertEquals(str(s.solution(4,22)), "[4, 2, 3, 1]");
        assertEquals(str(s.solution(4,24)), "[4, 3, 2, 1]");
        assertEquals(str(s.solution(4,18)), "[3, 4, 2, 1]");
        assertEquals(str(s.solution(20,1)), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
    }
    public String str(int[] arr) {
        return Arrays.toString(arr);
    }
}