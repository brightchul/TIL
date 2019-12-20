package programmers.mockTest1;

import java.util.ArrayList;
import java.util.Arrays;

/*
https://programmers.co.kr/learn/courses/30/lessons/42840
코딩테스트 연습 > 완전탐색 > 모의고사

[1,2,3,4,5]	[1]
[1,3,2,4,2]	[1,2,3]

 */
public class Solution {
    final int[] man1 = new int[]{ 1, 2, 3, 4, 5};
    final int[] man2 = new int[]{ 2, 1, 2, 3, 2, 4, 2, 5};
    final int[] man3 = new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

    public static void main(String[] args) {
        int[] case1 = new int[]{1,2,3,4,5};
        int[] case2 = new int[]{1,3,2,4,2};
        Solution sol = new Solution();
        sol.showArr(sol.solution(case1));
        sol.showArr(sol.solution(case2));
    }

    public int[] solution(int[] answer) {
        int[] ret = new int[]{0,0,0};
        int len1 = man1.length, len2 = man2.length, len3 = man3.length, aLen = answer.length;
        for(int i=0; i<aLen; i++) {
            if(answer[i] == man1[i%len1]) ret[0]++;
            if(answer[i] == man2[i%len2]) ret[1]++;
            if(answer[i] == man3[i%len3]) ret[2]++;
        }
        return getMaxMan(ret);
    }

    public int[] getMaxMan(int[] scores) {
        int max = 0;
        int maxCount = 0;

        for(int i=0; i<scores.length; i++) {
            if(max == scores[i]) maxCount++;
            else if(max < scores[i]){
                maxCount = 1;
                max = scores[i];
            }
        }

        int[] ret = new int[maxCount];
        for(int i=0, j=0; i<scores.length; i++)
            if(max == scores[i]) ret[j++] = i+1;

        return ret;
    }

    public void showArr(int[] arr) {
        for(int i=0; i<arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}
