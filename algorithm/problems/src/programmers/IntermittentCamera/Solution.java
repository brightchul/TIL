package programmers.IntermittentCamera;
/*
코딩테스트 연습 > 탐욕법(Greedy) > 단속카메라
https://programmers.co.kr/learn/courses/30/lessons/42884?language=java

 */
import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(new int[][]{{-20,15}, {-14,-5}, {-18,-13}, {-5,-3}})==2);   // 2
        System.out.println(s.solution(new int[][]{{-18,-13}, {-5,-3}})==2);   // 2
        System.out.println(s.solution(new int[][]{{-18,-13}, {-13,-3}, {-3,10}})==2);   // 2
        System.out.println(s.solution(new int[][]{{-18,-3}, {-13,-2}, {-3,10}})==1);   // 1
        System.out.println(s.solution(new int[][]{{-18,-3}, {-13,-2}, {-2,10}})==2);   // 2
        System.out.println(s.solution(new int[][]{{-20,15}})==1);   // 1
        System.out.println(s.solution(new int[][]{{-2,-1}, {1,2},{-3,0}})==2); //2
        System.out.println(s.solution(new int[][]{{0,1}, {0,1}, {1,2}})==1); //1
        System.out.println(s.solution(new int[][]{{0,1}, {2,3}, {4,5}, {6,7}})==4); //4
        System.out.println(s.solution(new int[][]{{-20,-15}, {-14,-5}, {-18,-13}, {-5,-3}})==2); //2
        System.out.println(s.solution(new int[][]{{-20,15}, {-14,-5}, {-18,-13}, {-5,-3}})==2); //2
        System.out.println(s.solution(new int[][]{{-20,15}, {-20,-15}, {-14,-5}, {-18,-13}, {-5,-3}})==2); //2
    }
    public int solution(int[][] routes) {
        Arrays.sort(routes, (ints, t1) -> ints[0] - t1[0]);

        int [] one;
        ArrayList<int[]> list = new ArrayList<>();
        int count = 0, outValue = Integer.MAX_VALUE;

        for(int i=0; i<routes.length; i++) {
            one = routes[i];
            if(outValue < one[0]) {
                list.clear();
                count++;
                outValue = one[1];
            } else {
                outValue = Math.min(one[1], outValue);
            }
            list.add(one);
        }
        return list.size() > 0 ? count+1 : count;
    }
}