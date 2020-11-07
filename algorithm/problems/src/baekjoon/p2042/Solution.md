# 백준 온라인 저지 : 구간 합 구하기

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2042)

어떤 N개의 수가 주어져 있다. 그런데 중간에 수의 변경이 빈번히 일어나고 그 중간에 어떤 부분의 합을 구하려 한다. 만약에 1,2,3,4,5 라는 수가 있고, 3번째 수를 6으로 바꾸고 2번째부터 5번째까지 합을 구하라고 한다면 17을 출력하면 되는 것이다. 그리고 그 상태에서 다섯 번째 수를 2로 바꾸고 3번째부터 5번째까지 합을 구하라고 한다면 12가 될 것이다.

```
[입력]
첫째 줄에 수의 개수 N(1 ≤ N ≤ 1,000,000)과 M(1 ≤ M ≤ 10,000), K(1 ≤ K ≤ 10,000) 가 주어진다. M은 수의 변경이 일어나는 횟수이고, K는 구간의 합을 구하는 횟수이다. 그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 그리고 N+2번째 줄부터 N+M+K+1번째 줄까지 세 개의 정수 a, b, c가 주어지는데, a가 1인 경우 b(1 ≤ b ≤ N)번째 수를 c로 바꾸고 a가 2인 경우에는 b(1 ≤ b ≤ N)번째 수부터 c(b ≤ c ≤ N)번째 수까지의 합을 구하여 출력하면 된다.

입력으로 주어지는 모든 수는 -2^63보다 크거나 같고, 2^63-1보다 작거나 같은 정수이다.

[출력]
첫째 줄부터 K줄에 걸쳐 구한 구간의 합을 출력한다. 단, 정답은 -2^63보다 크거나 같고, 2^63-1보다 작거나 같은 정수이다.



[예시]
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
17
12
```

​      

## 문제풀이

세그먼트 트리 연습을 위해서 관련 문제를 풀고 있다. 조금씩 세그먼트 트리 구현에 대해서 익숙해지고 있다.  세그먼트 트리 초기화, 업데이트, 구간 합 값을 재귀호출 등을 이용해서 하면 된다. 

[[구간 곱 구하기]](https://github.com/brightchul/TIL/blob/master/algorithm/problems/src/baekjoon/p11505/Solution.md) 문제와 사실상 동일한 문제라고 보면 된다. 주의할 점은 2^63-1까지가 범위이기 때문에  int형을 사용하지 않고 long을 사용해야 된다는 점이다.

​      


## 코드 구현 [[전체 코드]](./Main.java)

```java

class STree {
    long[] array;
    int lastIdx;

    STree(long[] arr) {
        lastIdx = arr.length - 1;
        array = new long[arr.length * 4];
        init(arr, 1, 0, lastIdx);
    }

    // 초기화하면서 구간합을 채워나간다.
    public long init(long[] arr, int node, int from, int to) {
        if (from == to) return array[node] = arr[from];
        int mid = (from + to) / 2;
        return array[node] = init(arr, node * 2, from, mid) + init(arr, node * 2 + 1, mid + 1, to);
    }

    // idx에 value를 업데이트
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

    // from ~ to 까지의 구간합을 구한다.
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

```

