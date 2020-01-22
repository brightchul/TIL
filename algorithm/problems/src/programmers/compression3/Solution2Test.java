package programmers.compression3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Solution2Test {
    Solution2 s = new Solution2();
    @Test
    public void solution() {
        assertArrayEquals(s.solution("KAKAO"), new int[]{11, 1, 27, 15});
        assertArrayEquals(s.solution("TOBEORNOTTOBEORTOBEORNOT"), new int[]{20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34});
        assertArrayEquals(s.solution("ABABABABABABABAB"), new int[]{1, 2, 27, 29, 28, 31, 30});
    }
}