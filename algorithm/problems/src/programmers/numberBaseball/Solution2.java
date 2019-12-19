package programmers.numberBaseball;

import java.util.HashSet;
import java.util.Set;

/*
[[123, 1, 1], [356, 1, 0], [327, 2, 0], [489, 0, 1]]        // 2
비효율적인 방법. 패스율 50퍼.
 */
public class Solution2 {
    final Set<Integer> numSet = new HashSet<>();
    final Set<Integer> allCaseSet = new HashSet<>();
    public static void main(String[] args) {
        int[][] case1 =  new int[][] { {123, 1, 1},  {356, 1, 0},  {327, 2, 0},  {489, 0, 1}};
        new Solution().solution(case1);
    }
    public int solution(int[][] baseball) {
        numToSet(baseball);
        makeAllCaseSet();
        return checkAllCase(baseball);
    }
    public void numToSet(int[][] baseball) {
        for(int i=0; i<baseball.length; i++) {
            int one = baseball[i][0];
            for(int j=0; j<3; j++) {
                numSet.add(one%10);
                one /= 10;
            }
        }
    }
    public void makeAllCaseSet() {
        for(int one : numSet) {
            for(int two : numSet) {
                if(one == two) continue;
                for(int three : numSet) {
                    if(one == three || two == three) continue;
                    allCaseSet.add(100*one + 10 * two + three);
                }
            }
        }
    }
    public int checkAllCase(int[][] baseball) {
        int ret = 0;
        for(int one : allCaseSet)
            if(checkCase(one, baseball)) {
//                System.out.println(one);
                ret++;
            }
        return ret;
    }
    public boolean checkCase(int target, int[][] baseball) {
        int t1 = target % 10;
        int t10 = target / 10 % 10;
        int t100 = target / 100;

        int count = 0;

        for(int i=0; i<baseball.length; i++) {
            int strike = 0, ball = 0;
            int one = baseball[i][0];
            int one1 = one % 10;
            int one10 = one / 10 % 10;
            int one100 = one / 100;

            if(t100 == one100) {
                strike++;
            } else if(t100 == one10 || t100 == one1) {
                ball++;
            }
            if(t10 == one10) {
                strike++;
            } else if(t10 == one100 || t10 == one1) {
                ball++;
            }
            if(t1 == one1) {
                strike++;
            } else if(t1 == one100 || t1 == one10) {
                ball++;
            }

            if(strike == baseball[i][1] && ball == baseball[i][2]) {
                count++;
            }

        }
        return count == baseball.length;
    }
}
