package programmers.numberBaseball;
/*
https://programmers.co.kr/learn/courses/30/lessons/42841
코딩테스트 연습 > 완전탐색 > 숫자 야구
[[123, 1, 1], [356, 1, 0], [327, 2, 0], [489, 0, 1]]        // 2

 */
public class Solution {
    public static void main(String[] args) {
        int[][] case1 =  new int[][] { {123, 1, 1},  {356, 1, 0},  {327, 2, 0},  {489, 0, 1}};
        System.out.println(new Solution().solution(case1));
    }
    public int solution(int[][] baseball) {
        return checkCase(baseball);
    }
    public int checkCase(int[][] baseball) {
        int ret = 0;
        for(int i=123; i <= 987; i++) {
            int i1 = i % 10, i10 = i / 10 % 10, i100 = i / 100;
            if(i1*i10*i100 == 0) continue;
            if(i100 == i10 || i100 == i1 || i10 == i1) continue;
            int count = 0;
            for(int j=0; j<baseball.length; j++) {
                int strike = 0, ball = 0;
                int b1 = baseball[j][0] % 10, b10 = baseball[j][0] / 10 % 10, b100 = baseball[j][0] / 100;

                if(i100 == b100) strike++;
                else if(i100 == b10 || i100 == b1) ball++;

                if(i10 == b10) strike++;
                else if(i10 == b100 || i10 == b1) ball++;

                if(i1 == b1) strike++;
                else if(i1 == b100 || i1 == b10) ball++;

                if(strike == baseball[j][1] && ball == baseball[j][2]) count++;
            }
            if(count == baseball.length) ret++;
        }
        return ret;
    }
}
