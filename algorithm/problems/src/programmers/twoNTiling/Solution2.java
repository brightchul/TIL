package programmers.twoNTiling;

import java.math.BigInteger;

public class Solution2 {
    private long CASE_NUM =  1_000_000_007L;

    public int solution(int n) {
        int twoCount = n/2;
        int beforeNum = n-1, nextNum = n-2;

        BigInteger temp = BigInteger.valueOf(beforeNum);
        BigInteger result = temp;

        for(int i=2; i<= twoCount; i++) {
            temp = temp.multiply(BigInteger.valueOf(nextNum-- * nextNum--)).divide(BigInteger.valueOf(beforeNum-- * i));
            result = result.add(temp);
        }

        result = result.add(BigInteger.ONE);
        return result.remainder(BigInteger.valueOf(CASE_NUM)).intValue();
    }
}
