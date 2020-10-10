package baekjoon.p12865;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
https://www.acmicpc.net/problem/12865

4 7
6 13
4 8
3 6
5 12

return 14

14 100000
61803 5
62863 0
41632 3
12794 2
71324 8
55358 2
34870 8
41590 7
17928 0
24218 3
18426 0
65130 2
16478 2
93173 9

return 17
 */

public class Main {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    int itemLen = Integer.parseInt(st.nextToken());
    int weightLimit = Integer.parseInt(st.nextToken());

    Item[] itemArr = new Item[itemLen];

    for (int idx = 0; idx < itemLen; idx++) {
      st = new StringTokenizer(br.readLine(), " ");
      itemArr[idx] = new Item(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

    System.out.println(solution(itemLen, weightLimit, itemArr));
  }

  public static int solution(int itemLen, int weightLimit, Item[] itemArr) {
    int[][] valueArr = new int[itemLen + 1][weightLimit + 1];

    int idx = 0;
    while (++idx <= itemLen) {
      int weight = itemArr[idx - 1].weight;
      int value = itemArr[idx - 1].value;

      for (int bagWeight = 1; bagWeight <= weightLimit; bagWeight++) {
        if (weight > bagWeight) {
          valueArr[idx][bagWeight] = valueArr[idx - 1][bagWeight];
        } else {
          valueArr[idx][bagWeight] =
              Math.max(valueArr[idx - 1][bagWeight], value + valueArr[idx - 1][bagWeight - weight]);
        }
      }
    }
    return valueArr[itemLen][weightLimit];
  }
}

class Item {
  int weight, value;

  Item(int weight, int value) {
    this.weight = weight;
    this.value = value;
  }

  @Override
  public String toString() {
    return "Item{" + "weight=" + weight + ", value=" + value + '}';
  }
}
