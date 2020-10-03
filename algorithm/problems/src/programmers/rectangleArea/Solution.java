package programmers.rectangleArea;

import java.util.Arrays;

public class Solution {

  public static int getMaxY(Pos[] arr) {
    int ret = Integer.MIN_VALUE;
    for (Pos one : arr) ret = Math.max(ret, one.bigY);
    return ret;
  }

  public long solution(int[][] rectangles) {
    Pos[] arr = getSortedPostArr(rectangles);
    STree st = new STree(Solution.getMaxY(arr));

    int area = 0;
    int preX = -1;

    for (Pos one : arr) {
      if (preX != -1) {
        area += (one.x - preX) * st.getTotalY();
      }
      st.update(one);
      preX = one.x;
    }

    return area;
  }

  public Pos[] getSortedPostArr(int[][] rectangles) {
    Pos[] posArr = new Pos[rectangles.length * 2];
    int idx = 0;

    for (int[] rect : rectangles) {
      posArr[idx++] = new Pos(rect[0], rect[1], rect[3], true);
      posArr[idx++] = new Pos(rect[2], rect[1], rect[3], false);
    }
    Arrays.sort(posArr);

    return posArr;
  }
}

class Node {
  public int count, sum;
}

class STree {
  Node[] arr;
  int range;

  STree(int len) {
    range = len;
    arr = new Node[len * 4 + 1];
    for (int i = 0; i < arr.length; i++) arr[i] = new Node();
  }

  public void update(Pos one) {
    update(one.smallY, one.bigY - 1, one.isStart, 0, range, 1);
  }

  public int getTotalY() {
    return arr[1].sum;
  }

  private void update(
      int left, int right, boolean isStart, int nodeLeft, int nodeRight, int nodeNum) {
    if (right < nodeLeft || nodeRight < left) return;
    if (left <= nodeLeft && nodeRight <= right) {
      Node oneNode = arr[nodeNum];
      if (isStart) {
        if (oneNode.count == 0) oneNode.sum = nodeRight - nodeLeft + 1;
        oneNode.count++;
      } else {
        oneNode.count--;

        if (oneNode.count == 0) {
          oneNode.sum =
              (nodeLeft == nodeRight) ? 0 : arr[nodeNum * 2].sum + arr[nodeNum * 2 + 1].sum;
        }
      }
      upward(nodeNum / 2);

    } else {
      int mid = (nodeLeft + nodeRight) / 2;
      update(left, right, isStart, nodeLeft, mid, nodeNum * 2);
      update(left, right, isStart, mid + 1, nodeRight, nodeNum * 2 + 1);
    }
  }

  private void upward(int nodeNum) {
    if (nodeNum <= 0) return;

    Node oneNode = arr[nodeNum];
    if (oneNode.count == 0) oneNode.sum = arr[nodeNum * 2].sum + arr[nodeNum * 2 + 1].sum;
    upward(nodeNum / 2);
  }
}

class Pos implements Comparable<Pos> {

  int x, smallY, bigY;
  boolean isStart;

  Pos(int x, int y1, int y2, boolean isStart) {
    this.x = x;
    this.smallY = Math.min(y1, y2);
    this.bigY = Math.max(y1, y2);
    this.isStart = isStart;
  }

  @Override
  public int compareTo(Pos o) {
    if (x == o.x) return smallY - o.smallY;
    return x - o.x;
  }
}
