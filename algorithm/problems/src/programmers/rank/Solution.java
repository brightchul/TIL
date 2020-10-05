package programmers.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Solution {

  public int solution(int n, int[][] results) {
    MatchGraph g = new MatchGraph(n);
    for (int[] result : results) {
      g.add(result[0], result[1]);
    }

    int answer = 0;
    for (int i = 1; i <= n; i++) {
      if (g.countMatch(i) == n - 1) answer++;
    }
    return answer;
  }
}

// 그래프
class MatchGraph {
  MyNode[] arr;

  MatchGraph(int n) {
    arr = new MyNode[n + 1];
    for (int i = 1; i <= n; i++) arr[i] = new MyNode();
  }

  public int countMatch(int player) {
    boolean[] visited = new boolean[arr.length];
    dfs(player, visited, node -> node.winnerList);
    visited[player] = false; // 자기 자신은 위에서 한번 visited되었기 때문에 초기화
    dfs(player, visited, node -> node.loserList);

    int count = 0;
    for (boolean one : visited) {
      if (one) count++;
    }
    return count - 1;
  }

  private void dfs(int player, boolean[] visited, Function<MyNode, List<Integer>> func) {
    if (visited[player]) return;
    visited[player] = true;

    for (int one : func.apply(arr[player])) {
      if (!visited[one]) dfs(one, visited, func);
    }
  }

  public void add(int winner, int loser) {
    arr[winner].loserList.add(loser);
    arr[loser].winnerList.add(winner);
  }
}

class MyNode {
  List<Integer> winnerList = new ArrayList<>();
  List<Integer> loserList = new ArrayList<>();
}
