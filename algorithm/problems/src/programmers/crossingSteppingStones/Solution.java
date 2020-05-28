package programmers.crossingSteppingStones;


class Solution {
    public int solution(int[] stones, int k) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int one : stones) {
            if(min > one) min = one;
            if(max < one) max = one;
        }
        while(true) {
            if(max == min) return max;
            int mid = (max + min) / 2;
            int maxCount = 0, count = 0;
            for(int one : stones) {
                if(one - mid < 1) count++;
                else if(count > 0) {
                    maxCount = Math.max(maxCount, count);
                    count = 0;
                }
            }
            maxCount = Math.max(maxCount, count);
            if (maxCount > k) {
                max = mid;
                continue;
            }
            if(maxCount < k) {
                min = mid+1;
                continue;
            }
            if(maxCount == k) {
                int[] countArr = new int[k];
                int idx = 0, passedCount = 0, passedMax = Integer.MAX_VALUE;
                for(int stone : stones) {
                    int passedStone = stone - mid;
                    if(passedStone < 1) {
                        countArr[idx++] = passedStone;
                        passedCount++;
                    } else {
                        passedCount = 0;
                        idx = 0;
                    }
                    if(passedCount == k) {
                        passedMax = Math.min(passedMax, getMax(countArr));
                        passedCount = 0;
                        idx = 0;
                    }
                }
                return mid + passedMax;
            }
        }
    }
    public int getMax(int[] arr) {
        int ret = Integer.MIN_VALUE;
        for(int one : arr)
            if(ret < one) ret = one;
        return ret;
    }
}
