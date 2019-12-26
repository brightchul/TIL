package programmers.cache1;
import java.util.LinkedList;

/*
https://programmers.co.kr/learn/courses/30/lessons/17680
코딩테스트 연습 > 2018 KAKAO BLIND RECRUITMENT > [1차] 캐시

 */
public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(3, new String[]{"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"}));
        System.out.println(s.solution(3, new String[]{"Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"}));
        System.out.println(s.solution(2, new String[]{"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"}));
        System.out.println(s.solution(5, new String[]{"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"}));
    }
    public int solution(int cacheSize, String[] cities) {
        if(cacheSize == 0) return cities.length * 5;
        LinkedList<String> ll = new LinkedList<>();

        int len = cities.length, time = 0, idx = 0;
        String city = "";

        for(int i=0; i<len; i++) {
            city = cities[i].toLowerCase();
            if((idx = ll.indexOf(city)) > -1) {
                time += 1;
                ll.remove(idx);
            } else {
                time += 5;
                if(ll.size() == cacheSize) ll.remove(0);
            }
            ll.add(city);
        }
        return time;
    }
}