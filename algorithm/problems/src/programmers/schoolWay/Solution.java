package programmers.schoolWay;
/*
코딩테스트 연습 > 동적계획법(Dynamic Programming) > 등굣길
 */
public class Solution {
    final int LIMIT = 1000000007;
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(4,3, new int[][]{{2,2}}));    // 4
    }
    public int solution(int m, int n, int[][] puddles) {
        // m : x좌표이므로 col, n : y좌표이므로 row이다.
        int[][] arr = new int[n+1][m+1];
        arr[1][0] = 1;

        // 웅덩이를 arr에 적용한다.
        for(int[] puddle : puddles)
            arr[puddle[1]][puddle[0]] = -1;

        // 1,1부터 순회하면서 값을 누적한다.
        // 웅덩이(-1)일 때에는 0으로 처리하고 넘어간다.
        for(int row=1; row<=n; row++) {
            for(int col=1; col<=m; col++) {
                if(arr[row][col] == -1) arr[row][col] = 0;
                else {
                    arr[row][col] = (arr[row-1][col] + arr[row][col-1]) % LIMIT;
                }
            }
        }
        return arr[n][m] % LIMIT;
    }
}
