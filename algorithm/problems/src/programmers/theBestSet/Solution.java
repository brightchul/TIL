package programmers.theBestSet;

import java.util.Arrays;

public class Solution {
    public int[] solution(int n, int s) {
        if(s/n == 0) return new int[]{-1};
        int[] ret = new int[n];
        int idx = 0;
        for(int i=n; i>0; i--) {
            int one = s / i;
            ret[idx++] = one;
            s -= one;
        }
        return ret;
    }
    public int[] solution2(int n, int s) {
        if(s/n == 0) return new int[]{-1};
        int[] ret = new int[n];
        Arrays.fill(ret, s/n);
        for(int i=s%n, idx = n-1; i > 0; i--) {
            ret[idx--]++;
        }
        return ret;
    }
}
