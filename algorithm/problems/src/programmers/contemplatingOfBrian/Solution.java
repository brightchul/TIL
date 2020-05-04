package programmers.contemplatingOfBrian;

import java.util.*;

/*
TODO : 브라이언의 고민
https://programmers.co.kr/learn/courses/30/lessons/1830

HaEaLaLaObWORLDb	HELLO WORLD
SpIpGpOpNpGJqOqA	SIGONG JOA
AxAxAxAoBoBoB	invalid

1. 특정 단어를 선택하여 글자 사이마다 같은 기호를 넣는다.
2. 특정 단어를 선택하여 단어 앞뒤에 같은 기호를 넣는다.
3. 1번 2번 둘다 적용가능
4. 공백이 있어서는 안된다.

// 소문자가 있으면 반드시 패턴이 있는 것이므로 가장 앞에 있으면 규칙2
// 앞에 있지 않으면 규칙 1이다.
// 한 문장에선 특정 단어로 들어가는 것들이 중복되지 않는다.
// 여러개의 답이 나올수 있다. 완벽하게 할 필요는 없음..
// 영문 대문자는 원래 문구, 소문자는 특수기호
 */
public class Solution {
    final String INV = "invalid";

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution("AsCsWsQsQsEEEEEEEEeEeEe"));
        System.out.println(s.solution("AxAxAxAoBoBoB"));
        System.out.println(s.solution("ABaCDEa"));
        System.out.println(s.solution("HaEaLaLaObWORLDb"));
        System.out.println(s.solution("AxAxAxAoBoBoB"));
        System.out.println(s.solution("aAkBkCDEFGa"));
    }

    public String solution(String sentence) {
        String answer = "";
        return answer;
    }
}