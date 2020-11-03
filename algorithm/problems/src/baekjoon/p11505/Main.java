package baekjoon.p11505;
/*
5 2 2
1
2
3
4
5
1 3 6
2 2 5
1 5 2
2 3 5

return
240
48

5 2 2
1
2
3
4
5
1 3 0
2 2 5
1 3 6
2 2 5

return
0
240

세그먼트 트리 중간의 값을 변경
값을 변경시 구간 곱으로 되어 있는 중간의 모든 값들을 변경
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");

    int len = pInt(st.nextToken());
    int changeCount = pInt(st.nextToken());
    int multiplyCount = pInt(st.nextToken());

    int[] arr = new int[len];
    for (int i = 0; i < len; i++) arr[i] = pInt(br.readLine());

    STree sTree = new STree(arr);
    StringBuilder sb = new StringBuilder();
    len = changeCount + multiplyCount;

    for (int i = 0; i < len; i++) {
      st = new StringTokenizer(br.readLine(), " ");
      String flag = st.nextToken();

      if (flag.equals("1")) {
        int idx = pInt(st.nextToken()) - 1;
        int value = pInt(st.nextToken());
        sTree.update(idx, value);

      } else {
        int from = pInt(st.nextToken()) - 1;
        int to = pInt(st.nextToken()) - 1;
        sb.append(sTree.query(from, to)).append("\n");
      }
    }
    System.out.println(sb.toString());
  }

  public static int pInt(String str) {
    return Integer.parseInt(str);
  }
}

class STree {
  private final long D_NUM = 1_000_000_007;
  int n;
  long[] array;

  STree(int[] arr) {
    n = arr.length - 1;
    array = new long[arr.length * 4];
    init(arr, 0, n, 1);
  }

  public long init(int[] arr, int from, int to, int node) {
    if (from == to) return array[node] = arr[to];

    int mid = (from + to) / 2;
    long left = init(arr, from, mid, node * 2);
    long right = init(arr, mid + 1, to, node * 2 + 1);

    return array[node] = ((left * right) % D_NUM);
  }

  public long query(int from, int to) {
    return query(from, to, 0, n, 1);
  }

  private long query(int from, int to, int nodeFrom, int nodeTo, int node) {
    if (nodeTo < from || to < nodeFrom) return 1;
    if (from <= nodeFrom && nodeTo <= to) return array[node];

    int mid = (nodeFrom + nodeTo) / 2;
    long left = query(from, to, nodeFrom, mid, node * 2);
    long right = query(from, to, mid + 1, nodeTo, node * 2 + 1);

    return (left * right) % D_NUM;
  }

  public void update(int idx, int newValue) {
    update(idx, newValue, 1, 0, n);
  }

  private long update(int idx, int newValue, int node, int nodeLeft, int nodeRight) {
    if (idx < nodeLeft || nodeRight < idx) return array[node];
    if (nodeLeft == nodeRight) return array[node] = newValue;

    int mid = (nodeLeft + nodeRight) / 2;
    long left = update(idx, newValue, node * 2, nodeLeft, mid);
    long right = update(idx, newValue, node * 2 + 1, mid + 1, nodeRight);

    return array[node] = (left * right) % D_NUM;
  }
}
