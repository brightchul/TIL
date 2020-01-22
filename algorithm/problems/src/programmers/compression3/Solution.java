package programmers.compression3;

import java.util.*;

/*
https://programmers.co.kr/learn/courses/30/lessons/17684?language=java
코딩테스트 연습 > 2018 KAKAO BLIND RECRUITMENT > [3차] 압축
KAKAO                       [11, 1, 27, 15]
TOBEORNOTTOBEORTOBEORNOT    [20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34]
ABABABABABABABAB            [1, 2, 27, 29, 28, 31, 30]
 */
public class Solution {
    private Map<String, Integer> map;
    private String[] arr;
    public int[] solution(String msg) {
        map = initMap();
        arr = msg.split("");
        List<Integer> resultList = new ArrayList<>();
        int idx= 0;
        int lastIndex = 26;
        while(idx < arr.length-1) {
            String one = findLongWord(idx);
            idx += one.length();
            resultList.add(map.get(one));
            if(idx >= arr.length) break;
            map.put(one+arr[idx], ++lastIndex);
        }
        if(idx == arr.length-1) resultList.add(map.get(arr[idx]));
        return convertListToArr(resultList);
    }
    public String findLongWord(int idx) {
        String one = arr[idx];
        String pre = one;
        while(idx < arr.length) {
            if(map.get(one) == null) break;
            pre = one;
            if(++idx >= arr.length) break;
            one += arr[idx];
        }
        return pre;
    }
    public int[] convertListToArr(List<Integer> list) {
        int[] ret = new int[list.size()];
        for(int i=0; i<ret.length; i++) ret[i] = list.get(i);
        return ret;
    }
    public Map<String, Integer> initMap() {
        Map<String, Integer> ret = new HashMap<>();
        char alphbet = 65;
        for(int i=1; i<27; i++, alphbet++) {
            ret.put(alphbet+"", i);
        }
        return ret;
    }
}
