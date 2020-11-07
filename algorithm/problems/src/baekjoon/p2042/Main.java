package baekjoon.p2042;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    int len = pInt(st);
    int operationCount = pInt(st) + pInt(st);

    long[] arr = new long[len];
    for (int i = 0; i < len; i++) arr[i] = pLong(br);

    STree sTree = new STree(arr);
    StringBuilder sb = new StringBuilder();

    while (operationCount-- > 0) {
      st = new StringTokenizer(br.readLine(), " ");
      switch (pInt(st)) {
        case 1: // change
          int idx = pInt(st) - 1;
          long value = pLong(st);
          sTree.update(idx, value);
          break;

        case 2: // 구간합 출력
          int from = pInt(st) - 1;
          int to = pInt(st) - 1;
          sb.append(sTree.query(from, to)).append("\n");
          break;

        default:
          throw new RuntimeException("error");
      }
    }
    System.out.println(sb.toString());
  }

  public static int pInt(StringTokenizer st) {
    return Integer.parseInt(st.nextToken());
  }

  public static long pLong(StringTokenizer st) {
    return Long.parseLong(st.nextToken());
  }

  public static long pLong(BufferedReader br) throws IOException {
    return Long.parseLong(br.readLine());
  }
}

class STree {
  long[] array;
  int lastIdx;

  STree(long[] arr) {
    lastIdx = arr.length - 1;
    array = new long[arr.length * 4];
    init(arr, 1, 0, lastIdx);
  }

  public long init(long[] arr, int node, int from, int to) {
    if (from == to) return array[node] = arr[from];
    int mid = (from + to) / 2;
    return array[node] = init(arr, node * 2, from, mid) + init(arr, node * 2 + 1, mid + 1, to);
  }

  public void update(int idx, long value) {
    update(idx, value, 1, 0, lastIdx);
  }

  private long update(int idx, long value, int node, int nodeLeft, int nodeRight) {
    if (idx < nodeLeft || nodeRight < idx) return array[node];
    if (nodeLeft == nodeRight) return array[node] = value;

    int mid = (nodeLeft + nodeRight) / 2;
    return array[node] =
        update(idx, value, node * 2, nodeLeft, mid)
            + update(idx, value, node * 2 + 1, mid + 1, nodeRight);
  }

  public long query(int from, int to) {
    return query(from, to, 1, 0, lastIdx);
  }

  private long query(int from, int to, int node, int nodeLeft, int nodeRight) {
    if (nodeRight < from || to < nodeLeft) return 0;
    if (from <= nodeLeft && nodeRight <= to) {
      return array[node];
    }
    int mid = (nodeLeft + nodeRight) / 2;
    return query(from, to, node * 2, nodeLeft, mid)
        + query(from, to, node * 2 + 1, mid + 1, nodeRight);
  }
}
