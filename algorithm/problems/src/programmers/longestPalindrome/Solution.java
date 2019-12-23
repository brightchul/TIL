package programmers.longestPalindrome;
/*
코딩테스트 연습 > 연습문제 > 가장 긴 팰린드롬
https://programmers.co.kr/learn/courses/30/lessons/12904
"abcdcba" 7
"abacde" 3
"aabcazaza" 5
"aab" 2
"abba" 4
 */


public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution("abcdcba"));    // 7
        System.out.println(sol.solution("abacde"));     // 3
        System.out.println(sol.solution("aabcazaza"));  // 5
        System.out.println(sol.solution("aab"));        // 2
        System.out.println(sol.solution("abba"));       // 4
    }
    public int solution(String s) {
        char[] sArr = s.toCharArray();  // charAt대신 char배열로 사용
        float last = sArr.length-1;
        float result = 0f;

        // 첫번째와 마지막 번째 인덱스값은 적용할 필요가 없다.
        for(float i=0.5f; i < last; i+= 0.5f) {
            int left = 0, right = 0;
            float tempLen = 0f;

            for(int j=1;;j++) {
                left = (int)Math.ceil(i-j);
                right = (int)i+j;

                if(left < 0 || right == sArr.length) break;
                if(sArr[left] != sArr[right]) break;
                tempLen = j;
            }
            // 짝수개일 때는 0.5를 빼줘야 제대로 나옴.
            result = Math.max(result, i%1>0 ? tempLen - 0.5f : tempLen);
        }
        return (int)(result*2+1);
    }
}
