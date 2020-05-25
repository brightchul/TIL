package programmers.visitingLength;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s1 = new Solution();
        assertEquals(7, s1.solution("ULURRDLLU"));
        assertEquals(7, s1.solution("LULLLLLLU"));
        assertEquals(1, s1.solution("LR"));
    }

}