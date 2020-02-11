package programmers.scale;

import java.util.Arrays;

/*
코딩테스트 연습 > 탐욕법(Greedy) > 저울
https://programmers.co.kr/learn/courses/30/lessons/42886
 */
public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(new int[]{3,1,6,2,7,30,1}));
    }
    public int solution(int[] weight) {
        Arrays.sort(weight);
        int acc = 1;
        for(int i=0; i<weight.length; i++) {
            if(weight[i] > acc) {
                break;
            }
            acc+= weight[i];
        }
        return acc;
    }
}
