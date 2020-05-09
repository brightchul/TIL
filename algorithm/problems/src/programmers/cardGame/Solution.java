package programmers.cardGame;

public class Solution {
    public static int[][] cache;

    public int solution(int[] left, int[] right) {
        cache = new int[left.length+1][right.length+1];
        return run(left, right, 0, 0);
    }
    public int run(int[] left, int[] right, int lIdx, int rIdx) {
        if(lIdx == left.length || rIdx == right.length) return 0;
        if(cache[lIdx][rIdx] > 0) return cache[lIdx][rIdx];

        if(left[lIdx] > right[rIdx]) {
            int score = right[rIdx];
            return cache[lIdx][rIdx] = score + run(left, right, lIdx, rIdx+1);

        } else {
            int leftScore = run(left, right, lIdx+1, rIdx);
            int bothScore = run(left, right, lIdx+1, rIdx+1);
            return cache[lIdx][rIdx] = Math.max(leftScore, bothScore);
        }
    }
}
