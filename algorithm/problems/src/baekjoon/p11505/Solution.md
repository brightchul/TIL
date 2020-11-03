# 백준 온라인 저지 : 구간 곱 구하기

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/11505)

어떤 N개의 수가 주어져 있다. 그런데 중간에 수의 변경이 빈번히 일어나고 그 중간에 어떤 부분의 곱을 구하려 한다. 만약에 1, 2, 3, 4, 5 라는 수가 있고, 3번째 수를 6으로 바꾸고 2번째부터 5번째까지 곱을 구하라고 한다면 240을 출력하면 되는 것이다. 그리고 그 상태에서 다섯 번째 수를 2로 바꾸고 3번째부터 5번째까지 곱을 구하라고 한다면 48이 될 것이다.




```
[입력]
첫째 줄에 수의 개수 N(1 ≤ N ≤ 1,000,000)과 M(1 ≤ M ≤ 10,000), K(1 ≤ K ≤ 10,000) 가 주어진다. M은 수의 변경이 일어나는 횟수이고, K는 구간의 곱을 구하는 횟수이다. 그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 그리고 N+2번째 줄부터 N+M+K+1 번째 줄까지 세 개의 정수 a,b,c가 주어지는데, a가 1인 경우 b번째 수를 c로 바꾸고 a가 2인 경우에는 b부터 c까지의 곱을 구하여 출력하면 된다.

입력으로 주어지는 모든 수는 0보다 크거나 같고, 1,000,000보다 작거나 같은 정수이다.

[출력]
첫째 줄부터 K줄에 걸쳐 구한 구간의 곱을 1,000,000,007로 나눈 나머지를 출력한다.

[예시1]
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

[예시2]
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

```





## 문제풀이

세그먼트 트리를 이용하면 풀 수 있는 문제이다. 사실상 세그먼트 트리 구현만 할 줄 알면 된다. 문제는 총 3가지로 나눌 수 있다. 

1. 초기 입력 값을 세그먼트 트리에 적용하되, 리프 값을 제외하고는 전부 구간 곱으로 채운다.
2. 중간에 수정해야 할 때 그 수정되는 값이 포함되는 모든 구간의 값을 업데이트 한다.
3. 특정 구간의 값을 반환한다.



## 코드 구현 [[전체코드]](./Main.java)

1. 처음에 초기 값이 입력되었을 때 리프 값 설정 및 구간 곱을 반영한다. 이 때 1_000_000_007 값을 나눠서 이 범위가 넘어가지 않도록 한다.

```java
public long init(int[] arr, int from, int to, int node) {
    if (from == to) return array[node] = arr[to];

    int mid = (from + to) / 2;
    long left = init(arr, from, mid, node * 2);
    long right = init(arr, mid + 1, to, node * 2 + 1);

    return array[node] = ((left * right) % 1_000_000_007);
}
```



2. 중간에 특정 인덱스의 값이 변경되는 경우 인덱스에 해당하는 리프노드의 값을 변경해주고 역으로 재귀를 타고 올라가면서 포함하는 모든 구간의 구간 곱을 업데이트 한다. 

```java
private long update(int idx, int newValue, int node, int nodeLeft, int nodeRight) {
    if (idx < nodeLeft || nodeRight < idx) return array[node];
    if (nodeLeft == nodeRight) return array[node] = newValue;

    int mid = (nodeLeft + nodeRight) / 2;
    long left = update(idx, newValue, node * 2, nodeLeft, mid);
    long right = update(idx, newValue, node * 2 + 1, mid + 1, nodeRight);

    return array[node] = (left * right) % 1_000_000_007;
}
```



3. 특정 구간이 나왔을 때 해당하는 구간곱의 값을 가져온다.

```java
private long query(int from, int to, int nodeFrom, int nodeTo, int node) {
    if (nodeTo < from || to < nodeFrom) return 1;
    if (from <= nodeFrom && nodeTo <= to) return array[node];

    int mid = (nodeFrom + nodeTo) / 2;
    long left = query(from, to, nodeFrom, mid, node * 2);
    long right = query(from, to, mid + 1, nodeTo, node * 2 + 1);

    return (left * right) % 1_000_000_007;
}
```