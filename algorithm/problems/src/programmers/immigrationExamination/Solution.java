package programmers.immigrationExamination;
/*
https://programmers.co.kr/learn/courses/30/lessons/43238
코딩테스트 연습 > 이분탐색 > 입국심사

6, {7,10} // 28
6, {6,10} // 24
6, {8,10} // 30
6, {4,10} // 20
11,{ 3,4,10} // 18
5, {1,1,10} // 3
10,{ 3, 8, 3, 6, 9, 2, 4}//8

 */
public class Solution {
    public static void main(String[] args){
        Solution sol = new Solution();
        System.out.println(sol.solution(6, new int[]{7,10}));
        System.out.println(sol.solution(6, new int[]{6,10}));
        System.out.println(sol.solution(6, new int[]{8,10}));
        System.out.println(sol.solution(6, new int[]{4,10}));
        System.out.println(sol.solution(11, new int[]{3,4,10}));
        System.out.println(sol.solution(5, new int[]{1,1,10}));
        System.out.println(sol.solution(10, new int[]{3, 8, 3, 6, 9, 2, 4}));
    }
    public long solution(int n1, int[] times1) {
        long n = (long)n1;
        long[] times = new long[times1.length];
        for(int i=0; i<times1.length; i++) times[i] = (long)times1[i];

        long min = 0, max = getMax(times) * n, mid = 0;

        while(true) {
            long mans = 0;
            mid = (min + max) / 2;
            for(int i=0; i<times.length; i++) mans +=  mid/times[i];

            if(max - min == 1) {
                mid = max;
                break;
            }
            if(mans < n) min = mid;
            else max = mid;
        }
        return mid;
    }
    public long getMax(long[] times) {
        long result = -1;
        for(int i=0; i<times.length; i++) {
            result = Math.max(result, times[i]);
        }
        return result;
    }
}
