# 백준 온라인 저지 : 최솟값

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/10868)

N(1 ≤ N ≤ 100,000)개의 정수들이 있을 때, a번째 정수부터 b번째 정수까지 중에서 제일 작은 정수를 찾는 것은 어려운 일이 아니다. 하지만 이와 같은 a, b의 쌍이 M(1 ≤ M ≤ 100,000)개 주어졌을 때는 어려운 문제가 된다. 이 문제를 해결해 보자.

여기서 a번째라는 것은 입력되는 순서로 a번째라는 이야기이다. 예를 들어 a=1, b=3이라면 입력된 순서대로 1번, 2번, 3번 정수 중에서 최솟값을 찾아야 한다. 각각의 정수들은 1이상 1,000,000,000이하의 값을 갖는다.




```
[입력]
첫째 줄에 N, M이 주어진다. 다음 N개의 줄에는 N개의 정수가 주어진다. 다음 M개의 줄에는 a, b의 쌍이 주어진다.

[출력]
M개의 줄에 입력받은 순서대로 각 a, b에 대한 답을 출력한다.

[예시]
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

return 
5
38
20
5
```





## 문제풀이

세그먼트 트리를 이용하면 쉽게 풀린다. 

- 세그먼트 트리로 입력 값을 넣으면서 리프 노드를 제외한 노드들에 한해서는 최소값을 넣으면서 진행한다.
- 구간을 받으면 그 구간에 맞춰서 재귀호출로 해당 구간안의 노드들을 찾아 나가면서 최소값을 받아 온다.



## 코드 구현 [[전체코드]](./Main.java)

```java
// 입력값이 1부터 시작하는데, 입력 배열은 0부터였기 때문에 
// 이것을 보정하기 위해 -1해준다.
int from = pInt(st) - 1;
int to = pInt(st) - 1;
```

```java
// 세그먼트 트리 구현

class STree {
    int[] array;
    int lastIdx;

    STree(int[] arr) {
        array = new int[arr.length * 4];
        lastIdx = arr.length - 1;
        init(arr, 1, 0, arr.length - 1);
    }

    // 입력값을 받을 때
    private int init(int[] arr, int node, int left, int right) {
        if (left == right) return array[node] = arr[left];
        int mid = (left + right) / 2;
        int leftValue = init(arr, node * 2, left, mid);
        int rightValue = init(arr, node * 2 + 1, mid + 1, right);
        return array[node] = Math.min(leftValue, rightValue);
    }

    // 구간에 따라 최소값을 가져올 때
    public int query(int from, int to) {
        // 입력값의 범위가 0~lastIdx까지의 값이므로 0, lastIdx를 넣는다.
        return query(from, to, 1, 0, lastIdx);
    }

    private int query(int from, int to, int node, int left, int right) {
        if (to < left || right < from) return Integer.MAX_VALUE;
        if (from <= left && right <= to) return array[node];
        int mid = (left + right) / 2;
        return Math.min(
            query(from, to, node * 2, left, mid), 
            query(from, to, node * 2 + 1, mid + 1, right)
        );
    }
}

```

