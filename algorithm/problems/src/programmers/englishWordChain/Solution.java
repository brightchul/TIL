package programmers.englishWordChain;
/*
코딩테스트 연습 > 서머코딩/윈터코딩(~2018) > 영어 끝말잇기
https://programmers.co.kr/learn/courses/30/lessons/12981
 */
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int[] solution(int n, String[] words) {
        List<String> list = new ArrayList<>();
        list.add(words[0]);

        for(int i=1; i<words.length; i++) {
            String preWord = words[i-1];
            String curWord = words[i];
            if(preWord.charAt(preWord.length()-1) != curWord.charAt(0) || list.indexOf(curWord) != -1)
                return new int[]{i%n+1,i/n+1};
            list.add(curWord);
        }
        return new int[]{0,0};
    }
}
