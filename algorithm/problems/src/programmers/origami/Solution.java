package programmers.origami;

public class Solution {
    private static int idx = 0;
    public int[] solution(int n) {
        idx = 0;
        int len = (int)(Math.pow(2, n)-1);
        int[] arr = new int[len];
        _solution(0, 1, n, arr);
        return arr;
    }
    private void _solution(int curValue, int curHeight, int totalHeight, int[] arr) {
        if(curHeight > totalHeight) return;
        _solution(0, curHeight+1, totalHeight, arr);
        arr[idx++] = curValue;
        _solution(1, curHeight+1, totalHeight, arr);
    }
}
