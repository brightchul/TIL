package programmers.crossingSteppingStones;
import java.util.*;
// 느림 컷..
class Solution2 {
    public int solution(int[] stones, int k) {
        List<Integer> list = new LinkedList<>();
        int ret = -1;
        for(int i=0; i<k; i++) {
            list.add(stones[i]);
            if(ret < stones[i]) ret = stones[i];
        }

        for(int i=k; i<stones.length; i++) {
            list.remove(0);
            list.add(stones[i]);
            ret = Math.min(ret, getMax(list));
        }
        return ret;
    }
    public int getMax(List<Integer> list) {
        int ret = -1;
        for(int one : list) {
            if(ret < one) ret = one;
        }
        return ret;
    }
}