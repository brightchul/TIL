package programmers.compression3;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();

    @Test
    public void solution() {
        assertArrayEquals(s.solution("KAKAO"), new int[]{11, 1, 27, 15});
        assertArrayEquals(s.solution("TOBEORNOTTOBEORTOBEORNOT"), new int[]{20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34});
        assertArrayEquals(s.solution("ABABABABABABABAB"), new int[]{1, 2, 27, 29, 28, 31, 30});
    }

    @Test
    public void initMap() {
        assertTrue(checkInitMap());
    }
    public boolean checkInitMap() {
        Map<String, Integer> map = s.initMap();
        if(map.size() != 26) return false;
        char A = 65;
        for(int i = 1; i<= 26; i++) {
            String one = Character.toString(A+i-1);
            if(map.get(one) != i) return false;
        }
        return true;
    }
}