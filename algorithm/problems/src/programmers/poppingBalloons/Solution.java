package programmers.poppingBalloons;

public class Solution {
    public int solution(int[] a) {
        int[] leftArr = new int[a.length];
        int[] rightArr = new int[a.length];
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;

        for (int i = 0; i < a.length; i++) {
            if (left > a[i]) {
                left = a[i];
            }
            leftArr[i] = left;
        }

        for (int i = a.length - 1; i > -1; i--) {
            if (right > a[i]) {
                right = a[i];
            }
            rightArr[i] = right;
        }

        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (leftArr[i] >= a[i] || rightArr[i] >= a[i]) count++;
        }
        return count;
    }
}
