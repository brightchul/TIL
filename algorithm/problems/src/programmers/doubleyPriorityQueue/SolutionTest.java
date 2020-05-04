package programmers.doubleyPriorityQueue;

import org.junit.Test;

import java.util.Arrays;

public class SolutionTest {

    @Test
    public void solution() {
        Solution sol = new Solution();
        String[] t1 = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};
        String[] t2 = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
        int[] a1 = {0,0};
        int[] a2 = {333, -45};
        System.out.println(Arrays.toString(sol.solution(t1)));
        System.out.println(Arrays.toString(sol.solution(t2)));
        System.out.println();
    }
}