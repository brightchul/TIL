package baekjoon.p6549;
/*
7 2 1 4 5 1 3 3
4 1000 1000 1000 1000
6 2 1 3 2 2 1
1 0
0

return
8
4000
6
0

 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

  public static void main(String[] args) throws IOException {
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine(), " ");
      int len = Integer.parseInt(st.nextToken());
      if (len == 0) break;

      long[] arr = initArr(st, len);
      bw.write(solution(arr) + "\n");
    }
    bw.flush();
    bw.close();
  }

  public static long[] initArr(StringTokenizer st, int len) {
    long[] arr = new long[len];
    int idx = 0;
    while (idx < len) {
      arr[idx++] = Integer.parseInt(st.nextToken());
    }
    return arr;
  }

  public static long solution(long[] arr) {
    long result = 0;
    Stack stack = new Stack(arr.length);

    for (int i = 0; i < arr.length; i++) {
      while (true) {
        if (stack.isEmpty() || arr[stack.top()] <= arr[i]) {
          stack.push(i);
          break;
        } else {
          result = Math.max(result, getArea(arr, stack, i));
        }
      }
    }

    // stack이 비워지지 않았을 경우에 대해서 처리해야 하는 로직
    int i = arr.length;
    while (!stack.isEmpty()) {
      result = Math.max(result, getArea(arr, stack, i));
    }
    return result;
  }

  public static long getArea(long[] arr, Stack stack, int i) {
    int one = stack.pop();
    int left = stack.isEmpty() ? 0 : stack.top() + 1;
    int right = i - 1;
    return arr[one] * (right - left + 1);
  }
}

class Stack {
  int[] arr;
  int idx = -1, lastIdx;

  Stack(int len) {
    lastIdx = len - 1;
    arr = new int[len];
    Arrays.fill(arr, -1);
  }

  public int length() {
    return idx + 1;
  }

  public void push(int i) {
    if (idx >= lastIdx) return;
    arr[++idx] = i;
  }

  public int pop() {
    if (idx <= -1) return 0;
    return arr[idx--];
  }

  public int top() {
    return arr[idx];
  }

  public boolean isEmpty() {
    return idx < 0;
  }

  @Override
  public String toString() {
    return "Stack{" + "arr=" + Arrays.toString(arr) + ", idx=" + idx + ", lastIdx=" + lastIdx + '}';
  }
}
