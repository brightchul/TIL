package programmers.findingPrimeNumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    Solution s = new Solution();

    @Test
    public void solution() {
        assertEquals(s.solution("17"),3);
        assertEquals(s.solution("011"),2);
        assertEquals(s.solution("1276543"),1336);
        assertEquals(s.solution("999999"),0);
    }
}
