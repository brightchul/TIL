package programmers.terrainMovement;
import java.util.*;

/**
 * https://sooooooyn.tistory.com/67 를 참고함
 */
public class Solution2 {
  int[] dirRow = {1, -1, 0, 0};
  int[] dirCol = {0, 0, 1, -1};

  int[] uf;
  int size;

  public int solution(int[][] land, int height) {
    int answer = 0;
    size = land.length;
    int[][] visited = new int[size][size]; // 방문했던거 캐시
    for (int[] oneRow : visited) Arrays.fill(oneRow, -1);

    // BFS 를 이용해서 사디리 없어도 이동할 수 있는 칸들을 묶어서 컴포넌트를 구한다.
    // visited 배열에 구한다.
    int comp = 0;

    // 모든 녀석들을 순회하면서 같은 영역들을 체크한다.
    // 같은 영역인 녀석들에 대해서는 comp 로 정리한다.
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (visited[row][col] >= 0) continue;

        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(row, col));
        visited[row][col] = comp;

        while (!q.isEmpty()) {
          Pair one = q.poll();
          int pRow = one.row;
          int pCol = one.col;

          // 방향별 체크
          for (int dir = 0; dir < 4; dir++) {
            int newRow = pRow + dirRow[dir];
            int newCol = pCol + dirCol[dir];

            if (isOutLength(newRow, newCol)) continue;
            if (visited[newRow][newCol] > -1) continue;
            if (Math.abs(land[pRow][pCol] - land[newRow][newCol]) > height) continue;

            visited[newRow][newCol] = comp;
            q.offer(new Pair(newRow, newCol));
          }
        }
        comp++;
      }
    }

    // visited 배열의 component 정보를 이용해서 Edge 배열 e 구하기
    List<Edge> e = new ArrayList<>();

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        for (int dir = 0; dir < 4; dir++) {
          int newRow = row + dirRow[dir];
          int newCol = col + dirCol[dir];
          if (isOutLength(newRow, newCol)) continue;

          int comp1 = visited[row][col];
          int comp2 = visited[newRow][newCol];
          if (comp1 == comp2) continue;
          
          e.add(new Edge(comp1, comp2, Math.abs(land[row][col] - land[newRow][newCol])));
        }
      }
    }
    if (e.size() == 0) return 0;

    // MST 크루스칼 알고리즘
    uf = new int[comp];
    Arrays.fill(uf, -1);

    e.sort((o1, o2) -> Integer.compare(o1.w, o2.w));

    int cnt = 0;
    for (int i = 0; ; i++) {
      Edge oneEdge = e.get(i);
      if (isNotConnect(oneEdge.u, oneEdge.v)) {
        answer += oneEdge.w;
        if (++cnt == comp - 1) break;
      }
    }
    return answer;
  }

  public int find(int a) {
    if (uf[a] < 0) return a;
    return uf[a] = find(uf[a]);
  }

  public boolean isNotConnect(int a, int b) {
    a = find(a);
    b = find(b);
    if (a == b) return false;
    uf[b] = a;
    return true;
  }

  public boolean isOutLength(int row, int col) {
    return row < 0 || row >= size || col < 0 || col >= size;
  }

  static class Pair {
    int row, col;

    public Pair(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  static class Edge {
    int u, v, w;

    public Edge(int u, int v, int w) {
      this.u = u;
      this.v = v;
      this.w = w;
    }
  }
}