package baekjoon.p10868;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*

10 4
75
30
100
38
50
51
52
20
81
5
1 10
3 5
6 9
8 10

 */

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    int oneCount = pInt(st);
    int twoCount = pInt(st);

    int[] arr = new int[oneCount];
    for (int i = 0; i < oneCount; i++) arr[i] = pInt(br);

    STree sTree = new STree(arr);
    StringBuilder sb = new StringBuilder();

    while (twoCount-- > 0) {
      st = new StringTokenizer(br.readLine(), " ");

      int from = pInt(st) - 1;
      int to = pInt(st) - 1;
      sb.append(sTree.query(pInt(st) - 1, pInt(st) - 1)).append("\n");
    }
    System.out.println(sb.toString());
  }

  public static int pInt(StringTokenizer st) {
    return Integer.parseInt(st.nextToken());
  }

  public static int pInt(BufferedReader br) throws IOException {
    return Integer.parseInt(br.readLine());
  }
}

class STree {
  int[] array;
  int lastIdx;

  STree(int[] arr) {
    array = new int[arr.length * 4];
    lastIdx = arr.length - 1;
    init(arr, 1, 0, arr.length - 1);
  }

  private int init(int[] arr, int node, int left, int right) {
    if (left == right) return array[node] = arr[left];
    int mid = (left + right) / 2;
    int leftValue = init(arr, node * 2, left, mid);
    int rightValue = init(arr, node * 2 + 1, mid + 1, right);
    return array[node] = Math.min(leftValue, rightValue);
  }

  public int query(int from, int to) {
    return query(from, to, 1, 0, lastIdx);
  }

  private int query(int from, int to, int node, int left, int right) {
    if (to < left || right < from) return Integer.MAX_VALUE;
    if (from <= left && right <= to) return array[node];
    int mid = (left + right) / 2;
    return Math.min(
        query(from, to, node * 2, left, mid), query(from, to, node * 2 + 1, mid + 1, right));
  }
}
