package programmers.findingPrimeNumber;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    private static Set<Integer> numSet;
    public int solution(String numbers) {
        numSet = new HashSet<>();
        recursive(numbers, "");
        int maxValue = getMax();
        int[] primeArr = makePrimeArr(maxValue);
        int count = 0;

        for(int one : numSet) {
            if(primeArr[one] == 1) count++;
        }

        numSet.clear();
        return count;
    }
    private int getMax() {
        int max = 0;
        for(int one : numSet) max = Math.max(max, one);
        return max;
    }
    private int[] makePrimeArr(int maxValue) {
        int limit = (int)Math.sqrt(maxValue);
        int[] arr = new int[maxValue+1];

        arr[0] = arr[1] = 0;
        for(int i=2; i<arr.length; i++) arr[i] = 1;

        for(int i=2; i<=limit; i++) {
            for(int idx = i*i; idx<=maxValue; idx+=i) {
                arr[idx] = 0;
            }
        }
        return arr;
    }
    private void recursive(String numbers, String accNumStr) {
        if(numbers.length() == 0) return;
        int len = numbers.length();
        for(int i=0; i<len; i++) {
            char one = numbers.charAt(i);
            String nextNumbers = numbers.substring(0, i) + numbers.substring(i+1);

            String newAccNumStr1 = one + accNumStr;
            numSet.add(Integer.parseInt(newAccNumStr1));
            recursive(nextNumbers, newAccNumStr1);

            String newAccNumStr2 = accNumStr + one;
            if(newAccNumStr1.equals(newAccNumStr2)) continue;
            numSet.add(Integer.parseInt(newAccNumStr2));
            recursive(nextNumbers, newAccNumStr2);
        }
    }
}
