package programmers.budget;
/*
https://programmers.co.kr/learn/courses/30/lessons/43237
코딩테스트 연습 > 이분탐색 > 예산

효율성 케이스2에서 int 범위를 넘어가므로 이부분을 감안해줘야 함.
 */
class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(new int[]{120, 110, 140, 150}, 485) == 127);
        System.out.println(s.solution(new int[]{12, 13, 14, 15}, 53) == 14);
        System.out.println(s.solution(new int[]{12, 13, 14, 15}, 48) == 12);
        System.out.println(s.solution(new int[]{1, 13, 14, 15}, 43) == 15);
        System.out.println(s.solution(new int[]{5, 5, 5, 5, 15, 30}, 62) == 27);
    }
    public int solution(int[] budgets, int M) {
        int len = budgets.length;
        int min = 0, max = 0, mid;
        long temp, sum = 0;

        for(int i=0;i<len; i++) {
            sum += budgets[i];
            max = Math.max(max, budgets[i]);
        }
        if(sum <= M) return max;

        while(true) {
            if(max - min == 1) break;
            temp = 0;
            mid = (min + max) / 2;
            for(int i=0; i<len; i++)
                temp += Math.min(mid, budgets[i]);
            if(temp > M) max = mid;
            else min = mid;
        }
        temp = 0;
        for(int i=0; i<len; i++)
            temp += Math.min(max, budgets[i]);
        return temp <= M ? max : min;
    }
}
