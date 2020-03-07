package programmers.nightWork;

import java.util.Arrays;

public class Solution {
    public long solution(int n, int[] works) {
        long total = arrSum(works);
        if(n >= total) return 0;

        long result = 0;
        double len = (double)works.length;

        Arrays.sort(works);
        for(int one : works) {
            int average = (int)((total-n) / len--);
            total -= one;
            if(one > average) {
                n -= (one-average);
                one = average;
            }
            result += one * one;
        }
        return result;
    }
    public long arrSum(int[] arr) {
        long ret = 0;
        for(int one : arr) ret += one;
        return ret;
    }
}
