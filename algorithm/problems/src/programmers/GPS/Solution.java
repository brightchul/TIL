package programmers.GPS;

import java.util.ArrayList;

class Solution {
    Graph g;
    int[][] dp;     // dp[i][j] 경로 i번째 위치가 j번 도시가 되면서 i번째 위치까지의 경로가 유효하게 고쳐야 하는 최소 횟수
    final int INF = 1_000_000_003;
    public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {

        init(n, edge_list);
        dp[0][gps_log[0]] = 0;

        for(int i=1; i<k-1; i++) {                                        // 경로별 택시 위치
            for(int j=1; j<=n; j++) {                                     // 노드 전부 검사
                int add = gps_log[i] == j ? 0 : 1;

                dp[i][j] = Math.min(dp[i][j], dp[i-1][j]+add);            // 현재 자리에 가만히 있는 경우

                for(int one : g.get(j)) {                                 // 현재 자리에서 연결된 지점으로 가는 경우
                    dp[i][j] = Math.min(dp[i-1][one] + add, dp[i][j]);
                }
            }
        }
        int answer = INF;
        for(int one : g.get(gps_log[gps_log.length-1])) {                       // 마지막 도착 지점 검사
            answer = Math.min(answer, dp[gps_log.length-2][one]);
        }
        if(answer >= INF) return -1;
        return answer;

    }
    public void init(int n, int[][] edge_list) {
        g = new Graph(n);
        dp = new int[n+1][n+1];
        for(int row=0; row<dp.length; row++)
            for(int col=0;col<dp.length; col++)
                dp[row][col] = INF;

        for(int[] edge : edge_list)
            g.add(edge[0], edge[1]);
    }
}

class Graph {
    private ArrayList<Integer>[] data;
    Graph(int len) {
        data = new ArrayList[len+1];
        for(int i=0; i<=len; i++) {
            data[i] = new ArrayList<>();
        }
    }
    public void add(int num, int addedNum) {
        data[num].add(addedNum);
        data[addedNum].add(num);
    }
    public ArrayList<Integer> get(int node) {
        return data[node];
    }
}

