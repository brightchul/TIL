package programmers.rank;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {

  public int solution(int n, int[][] results) {
    MatchGraph2 g = new MatchGraph2(n);
    for (int[] result : results) {
      g.add(result[0], result[1]);
    }

    int answer = 0;
    for (int i = 1; i <= n; i++) {
      if (g.countWinner(i) + g.countLoser(i) == n - 1) answer++;
    }
    return answer;
  }
}

// 그래프
class MatchGraph2 {
  MyNode2[] arr;

  MatchGraph2(int n) {
    arr = new MyNode2[n + 1];
    for (int i = 1; i <= n; i++) arr[i] = new MyNode2();
  }

  public int countWinner(int player) {
    boolean[] visited = new boolean[arr.length];
    recursiveWinner(player, visited);

    int count = 0;
    for (boolean one : visited) {
      if (one) count++;
    }
    return count - 1;
  }

  private void recursiveWinner(int player, boolean[] visited) {
    if (visited[player]) return;
    visited[player] = true;

    for (int one : arr[player].winnerList) {
      if (!visited[one]) recursiveWinner(one, visited);
    }
  }

  public int countLoser(int player) {
    boolean[] visited = new boolean[arr.length];
    recursiveLoser(player, visited);

    int count = 0;
    for (boolean one : visited) {
      if (one) count++;
    }
    return count - 1;
  }

  private void recursiveLoser(int player, boolean[] visited) {
    if (visited[player]) return;
    visited[player] = true;

    for (int one : arr[player].loserList) {
      if (!visited[one]) recursiveLoser(one, visited);
    }
  }

  public void add(int winner, int loser) {
    arr[winner].loserList.add(loser);
    arr[loser].winnerList.add(winner);
  }
}

class MyNode2 {
  List<Integer> winnerList = new ArrayList<>();
  List<Integer> loserList = new ArrayList<>();
}
