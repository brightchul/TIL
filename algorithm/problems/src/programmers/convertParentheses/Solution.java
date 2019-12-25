package programmers.convertParentheses;
import java.util.HashMap;

/*
코딩테스트 연습 > 2020 KAKAO BLIND RECRUITMENT > 괄호 변환
https://programmers.co.kr/learn/courses/30/lessons/60058

예로 x1`는 x1[1:x1.length-1] 의 괄호를 반전 시킨 문자열이다.

x1x2x3o4일경우
(((o4)+x3`)+x2`)+x1` 이런식으로 되어야 함.

x1o2x3o4일경우
(o2+(o4)+x3`)+x1`
 */

public class Solution {
    HashMap<Character, Character> pMap= new HashMap<>();

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution("(()())()").equals("(()())()"));
        System.out.println(sol.solution(")(").equals("()"));
        System.out.println(sol.solution("()))((()").equals("()(())()"));
        System.out.println(sol.solution(")()()()(").equals("(((())))"));
        System.out.println(sol.solution(")(()").equals("(())"));
        System.out.println(sol.solution("))((()").equals("(())()"));
        System.out.println(sol.solution(")(())(").equals("(()())"));
    }
    public Solution() {
        pMap.put('(', ')');
        pMap.put(')', '(');
    }
    public String solution(String str) {
        return sol(str, 0);
    }
    public String sol(String str, int idx) {
        int correctCnt = 0, stackCnt = 0;
        StringBuilder ret = new StringBuilder();
        StringBuilder tempRet = new StringBuilder();
        for(int i=idx; i<str.length(); i++) {
            if(str.charAt(i) == '(') {
                correctCnt++;
                stackCnt++;
            } else {
                correctCnt--;
                if(stackCnt > 0) stackCnt--;
            }
            tempRet.append(str.charAt(i));

            if(correctCnt == 0) {
                if(stackCnt == 0) {
                    ret.append(tempRet.toString());
                    tempRet = new StringBuilder();
                }else{
                    ret.append('(').append(sol(str, i+1)).append(')')
                       .append(subReverse(tempRet.toString()));
                    break;
                }
            }
        }
        return ret.toString();
    }
    public String subReverse(String str) {
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<str.length()-1; i++) sb.append(pMap.get(str.charAt(i)));
        return sb.toString();
    }
}