package programmers.expressionN;

import java.util.HashSet;

/*
https://programmers.co.kr/learn/courses/30/lessons/42895?language=java
코딩테스트 연습 > 동적계획법(Dynamic Programming) > N으로 표현


 */
public class Solution {
    public static HashSet<Integer>[] cache = new HashSet[9];
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(5, 12));  // 4
        System.out.println(s.solution(5, 31168));  // -1
        System.out.println(s.solution(5, 3600));  // 6
        System.out.println(s.solution(4, 17));  // 4
        System.out.println(s.solution(2, 22));  // 2
    }
    public int solution(int N, int number) {
        initCache();
        int[] arrNS = makeArrNS(N);
        int idxInArr = indexOf(arrNS, number);

        if(idxInArr > -1) return idxInArr;

        cache[1].add(N);
        for(int i=2; i<=8; i++) {
            cache[i].add(arrNS[i]);
            for(int j=1; j<i; j++) {
                for(int o : cache[j]) {
                    for(int p : cache[i-j]) {
                        if(calculate(o, p, i, number)) return i;
                    }
                }
            }
        }
        return -1;
    }
    public boolean calculate(int o, int p, int idx, int number) {
        int one  = 0;
        if((one = o + p) == number) return true;
        else cache[idx].add(one);
        if((one = o - p) == number) return true;
        else cache[idx].add(one);
        if((one = o * p) == number) return true;
        else cache[idx].add(one);
        if(p != 0 && (one = o / p) == number) return true;
        else cache[idx].add(one);
        return false;
    }
    public void initCache() {
        for(int i=1; i<9;i++) cache[i] = new HashSet<Integer>();
    }
    public int indexOf(int[] arr, int N) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i] == N) return i;
        }
        return -1;
    }
    public int[] makeArrNS(int N) {
        int[] ret = new int[9];
        for(int i=1; i<8; i++) {
            ret[i] = ret[i-1] * 10 + N;
        }
        return ret;
    }
}
