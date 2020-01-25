package programmers.twoNTiling;

import java.math.BigInteger;

public class Solution {
    private int MAX_LEN = 60_001;
    private BigInteger[] cache = new BigInteger[MAX_LEN];
    private long CASE_NUM =  1_000_000_007L;

    public int solution(int n) {
        int oneCount = n, twoCount = n/2;
        BigInteger result = BigInteger.ZERO;
        for(int i=0; i<= twoCount; i++) {
            result = result.add(combination(n-i, i));
        }
        result = result.remainder(BigInteger.valueOf(CASE_NUM));
        return result.intValue();
    }
    public BigInteger combination(int k, int t) {
        if(t * (k-t) == 0) return BigInteger.ONE;

        return factorial(k).divide(factorial(t)).divide(factorial(k-t));
    }
    public BigInteger factorial(int n) {
        if(n == 0 || n == 1) return BigInteger.ONE;
        if(cache[n] == null) cache[n] = factorial(n-1).multiply(BigInteger.valueOf(n));
        return cache[n];
    }
}
