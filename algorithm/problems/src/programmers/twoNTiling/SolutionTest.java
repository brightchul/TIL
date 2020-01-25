package programmers.twoNTiling;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class SolutionTest {
    Solution s = new Solution();
    Solution2 s2 = new Solution2();
    Solution3 s3 = new Solution3();

    @Test
    public void solution1() {
        assertEquals(s.solution(4), 5);
        assertEquals(s.solution(8), 34);
        assertEquals(s.solution(12), 233);
        assertEquals(s.solution(52), 316290802);
    }
    @Test
    public void combination() {
        assertEquals(s.combination(10, 1), BigInteger.valueOf(10));
        assertEquals(s.combination(9, 2), BigInteger.valueOf(36));
        assertEquals(s.combination(7, 4), BigInteger.valueOf(35));
        assertEquals(s.combination(4, 4), BigInteger.valueOf(1));
        assertEquals(s.combination(4, 0), BigInteger.valueOf(1));
    }
    @Test
    public void solution2() {
        assertEquals(s2.solution(4), 5);
        assertEquals(s2.solution(8), 34);
        assertEquals(s2.solution(12), 233);
        assertEquals(s2.solution(52), 316290802);
    }
    @Test
    public void solution3() {
        assertEquals(s3.solution(4), 5);
        assertEquals(s3.solution(8), 34);
        assertEquals(s3.solution(12), 233);
        assertEquals(s3.solution(52), 316290802);
    }

}