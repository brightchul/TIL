package programmers.openChattingRoom;
/*
코딩테스트 연습 > 2019 KAKAO BLIND RECRUITMENT > 오픈채팅방
https://programmers.co.kr/learn/courses/30/lessons/42888
 */
import java.util.*;

class Solution {
    private Map<String, String> map = new HashMap<>();

    public String[] solution(String[] record) {
        final int len = record.length;
        String[][] recordArr = new String[record.length][];
        int count = 0;
        for(int i=0; i<len; i++) {
            recordArr[i] = record[i].split(" ");
            if(record[i].charAt(0) != 'C') count++;
            if(recordArr[i].length > 2)
                map.put(recordArr[i][1], recordArr[i][2]);
        }

        String[] resultArr = new String[count];

        for(int i=0, j=0; i<count;j++) {
            if(recordArr[j][0].charAt(0) != 'C')
                resultArr[i++] = makeMSG(recordArr[j]);
        }
        return resultArr;
    }
    public String makeMSG(String[] arr) {
        char oneChar = arr[0].charAt(0);
        if(oneChar == 'C') return null;
        if(oneChar == 'E') return map.get(arr[1]) + "님이 들어왔습니다.";
        else return map.get(arr[1]) + "님이 나갔습니다.";
    }
}