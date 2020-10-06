package baekjoon.p2357;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
https://www.acmicpc.net/problem/2357
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
    int inputLen = Integer.parseInt(st.nextToken());
    int rangeLen = Integer.parseInt(st.nextToken());

    int idx = 0;
    int[] inputArr = new int[inputLen];
    while (inputLen-- > 0) {
      inputArr[idx++] = Integer.parseInt(br.readLine());
    }

    STree maxSTree = new STree(inputArr, Math::max, Integer.MIN_VALUE);
    STree minSTree = new STree(inputArr, Math::min, Integer.MAX_VALUE);

    StringBuilder sb = new StringBuilder();
    while (rangeLen-- > 0) {
      st = new StringTokenizer(br.readLine(), " ");
      int from = Integer.parseInt(st.nextToken()) - 1;
      int to = Integer.parseInt(st.nextToken()) - 1;
      sb.append(minSTree.query(from, to)).append(" ").append(maxSTree.query(from, to)).append("\n");
    }
    System.out.println(sb.toString());
  }
}

@FunctionalInterface
interface CheckFunc {
  int apply(int left, int right);
}

class STree {
  int n, defaultValue;
  int[] array;
  CheckFunc func;

  STree(int[] arr, CheckFunc func, int defaultValue) {
    n = arr.length - 1;
    this.defaultValue = defaultValue;
    array = new int[arr.length * 4];
    this.func = func;
    init(arr, 0, n, 1);
  }

  public int init(int[] arr, int from, int to, int node) {
    if (from == to) return array[node] = arr[from];

    int mid = (from + to) / 2;
    int left = init(arr, from, mid, node * 2);
    int right = init(arr, mid + 1, to, node * 2 + 1);
    return array[node] = func.apply(left, right);
  }

  public int query(int from, int to) {
    return query(from, to, 0, n, 1);
  }

  private int query(int from, int to, int nodeFrom, int nodeTo, int node) {
    if (nodeTo < from || to < nodeFrom) return defaultValue;
    if (from <= nodeFrom && nodeTo <= to) return array[node];

    int mid = (nodeFrom + nodeTo) / 2;
    int left = query(from, to, nodeFrom, mid, node * 2);
    int right = query(from, to, mid + 1, nodeTo, node * 2 + 1);
    return func.apply(left, right);
  }
}
