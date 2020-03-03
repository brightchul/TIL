package programmers.bestAlbum;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution sol = new Solution();
        String[] jArr = {"classic", "pop", "classic", "classic", "pop"};
        int[] aArr = {500, 600, 150, 800, 2500};
        int[] answer = {4, 1, 3, 0};
        assertArrayEquals(sol.solution(jArr, aArr), answer);
    }
}