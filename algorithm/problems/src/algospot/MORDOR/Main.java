package algospot.MORDOR;

import java.io.*;
import java.util.StringTokenizer;

/*
https://www.algospot.com/judge/problem/read/MORDOR

2
3 1
1 2 3
0 2
10 4
3 9 5 6 10 8 7 1 2 4
1 6
4 7
9 9
5 8

 */
public class Main {
  public static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  public static void main(String[] args) throws IOException {
    StringTokenizer st;
    int testCaseLen = Integer.parseInt(br.readLine());
    while (testCaseLen-- > 0) {
      st = new StringTokenizer(br.readLine(), " ");
      int n = Integer.parseInt(st.nextToken());
      int q = Integer.parseInt(st.nextToken());

      int[] hArr = new int[n];
      int[] reverseHArr = new int[n];
      st = new StringTokenizer(br.readLine(), " ");
      for (int i = 0; i < n; i++) {
        hArr[i] = Integer.parseInt(st.nextToken());
        reverseHArr[i] = -hArr[i];
      }

      BT minBt = new BT(hArr);
      BT maxBt = new BT(reverseHArr);

      int left, right;
      while (q-- > 0) {
        st = new StringTokenizer(br.readLine(), " ");
        left = Integer.parseInt(st.nextToken());
        right = Integer.parseInt(st.nextToken());
        bw.write(-maxBt.query(left, right) - minBt.query(left, right)+"");
        bw.write('\n');
      }
      bw.flush();
    }
  }

  static class BT {
    private int[] rangeMin;
    private int range;

    BT(int[] arr) {
      range = arr.length - 1;
      rangeMin = new int[4 * arr.length];
      init(arr, 0, range, 1);
    }

    public int init(int[] arr, int left, int right, int node) {
      if (left == right) return rangeMin[node] = arr[left];

      int mid = (left + right) / 2;
      int leftMin = init(arr, left, mid, node * 2);
      int rightMin = init(arr, mid + 1, right, node * 2 + 1);
      return rangeMin[node] = Math.min(leftMin, rightMin);
    }

    private int query(int left, int right, int node, int nodeLeft, int nodeRight) {
      if (right < nodeLeft || nodeRight < left) return Integer.MAX_VALUE;
      if (left <= nodeLeft && nodeRight <= right) return rangeMin[node];

      int nodeMid = (nodeLeft + nodeRight) / 2;
      return Math.min(
          query(left, right, node * 2, nodeLeft, nodeMid),
          query(left, right, node * 2 + 1, nodeMid + 1, nodeRight));
    }

    public int query(int left, int right) {
      return this.query(left, right, 1, 0, range);
    }
  }
}
