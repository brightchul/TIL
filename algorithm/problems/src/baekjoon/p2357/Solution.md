# 백준 온라인 저지 : 최솟값과 최댓값

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2357)

N(1 ≤ N ≤ 100,000)개의 정수들이 있을 때, a번째 정수부터 b번째 정수까지 중에서 제일 작은 정수, 또는 제일 큰 정수를 찾는 것은 어려운 일이 아니다. 하지만 이와 같은 a, b의 쌍이 M(1 ≤ M ≤ 100,000)개 주어졌을 때는 어려운 문제가 된다. 이 문제를 해결해 보자.

여기서 a번째라는 것은 입력되는 순서로 a번째라는 이야기이다. 예를 들어 a=1, b=3이라면 입력된 순서대로 1번, 2번, 3번 정수 중에서 최소, 최댓값을 찾아야 한다. 각각의 정수들은 1이상 1,000,000,000이하의 값을 갖는다.


```
[입력]
첫째 줄에 N, M이 주어진다. 다음 N개의 줄에는 N개의 정수가 주어진다. 다음 M개의 줄에는 a, b의 쌍이 주어진다.

[출력]
M개의 줄에 입력받은 순서대로 각 a, b에 대한 답을 최솟값, 최댓값 순서로 출력한다.

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
5 100
38 100
20 81
5 81
```

​    

## 문제풀이

세그먼트 트리를 이용해서 풀었다. 구간별 최솟값과 최댓값을 구할 때 구간 시작이 1부터 시작하므로 이 부분을 주의해야 한다. 입력 배열의 경우 0부터 입력하기 때문이다.

​    

## 코드 구현 [[전체 코드]](./Main.java)

여러 방식이 있겠지만 세그먼트 트리 2개를 이용해서 각각 최솟값과 최댓값을 구하는 방식으로 답을 구했다. 서로 반대의 값을 가져오는 것이기 때문에 비교 함수를 받도록 추상화를 시켜서 하나의 함수로 반대의 값을 가져오는 세그먼트 트리를 구현했다.

```java
STree maxSTree = new STree(inputArr, Math::max, Integer.MIN_VALUE);
STree minSTree = new STree(inputArr, Math::min, Integer.MAX_VALUE);

// 중략...

// 입력 배열을 넣을때 0~len-1까지였는데 
// 구간 입력시 1~len까지이기 때문에 이것을 보정하기 위해 -1를 해준다.
int from = Integer.parseInt(st.nextToken()) - 1;
int to = Integer.parseInt(st.nextToken()) - 1;

minSTree.query(from, to);
maxSTree.query(from, to);
```

​    

**세그먼트 트리 구현**

```java

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
        // defaultValue를 이용해서 func.apply에서 무시될수 있도록 한다.
        if (nodeTo < from || to < nodeFrom) return defaultValue;
        if (from <= nodeFrom && nodeTo <= to) return array[node];

        int mid = (nodeFrom + nodeTo) / 2;
        int left = query(from, to, nodeFrom, mid, node * 2);
        int right = query(from, to, mid + 1, nodeTo, node * 2 + 1);
        return func.apply(left, right);
    }
}

```



