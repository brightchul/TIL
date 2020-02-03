package programmers.ponketmon;
/*
코딩테스트 연습 > 찾아라 프로그래밍 마에스터 > 폰켓몬
https://programmers.co.kr/learn/courses/30/lessons/1845
 */
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(new int[]{3,1,2,3}));     // 2
        System.out.println(s.solution(new int[]{3,3,3,2,2,4})); // 3
        System.out.println(s.solution(new int[]{3,3,3,2,2,2})); // 2
    }
    public int solution(int[] nums) {
        HashMap<Integer, Integer> countMap = new HashMap<>();

        // 카운팅
        for(int i=0; i<nums.length; i++) {
            if(countMap.containsKey(nums[i])) countMap.put(nums[i], countMap.get(nums[i])+1);
            else countMap.put(nums[i], 1);
        }
        int count = countMap.size();
        int half = nums.length/2;
        // length체크
        return Math.min(count, half);
    }
}
