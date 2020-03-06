package programmers.englishWordChain;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s = new Solution();
        String[] input1 = {"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"};
        int[] answer1 = {3,3};
        assertArrayEquals(s.solution(3, input1), answer1);

        String[] input2 = {"hello", "observe", "effect", "take", "either", "recognize", "encourage", "ensure", "establish", "hang", "gather", "refer", "reference", "estimate", "executive"};
        int[] answer2 = {0,0};
        assertArrayEquals(s.solution(5, input2), answer2);

        String[] input3 = {"hello", "one", "even", "never", "now", "world", "draw"};
        int[] answer3 = {1,3};
        assertArrayEquals(s.solution(2, input3), answer3);
    }
}