package programmers.tileOrnaments;
/*
코딩테스트 연습 > 동적계획법(Dynamic Programming) > 타일 장식물
https://programmers.co.kr/learn/courses/30/lessons/43104?language=java
 */
public class Solution {
    public long solution(int N) {
        if(N == 1) return 4;

        long pre = 1, next =1, temp = 0;
        int n = 2;
        while(n++ < N) {
            temp = pre;
            pre = next;
            next += temp;
        }
        return ( (next * 2) + pre) * 2;
    }
    // 아래로도 풀어봤는데 제출했을때 성능차이는 거의 없는듯..
    public long solution2(int N) {
        if(N == 1) return 4;

        long pre = 1, next =1;
        int n = 2;
        while(n++ < N) {
            pre ^= next;
            next ^= pre;
            pre ^= next;
            next += pre;
        }
        return (next<<1 + pre) * 2;
    }
}
