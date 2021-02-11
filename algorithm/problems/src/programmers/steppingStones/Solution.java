package programmers.steppingStones;
// https://programmers.co.kr/learn/courses/30/lessons/43236

import java.util.Arrays;

class Solution {
    public int solution(int distance, int[] rocks, int n) {
        Arrays.sort(rocks);

        int left = 0;
        int right = distance;

        while (left <= right) {
            int prev = 0;
            int mid = (left + right) / 2;
            int cnt = 0;

            for (int rock : rocks) {
                if (rock - prev < mid) {
                    cnt++;
                } else {
                    prev = rock;
                }
            }
            if (distance - prev < mid) cnt++;

            if (cnt > n) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }
}