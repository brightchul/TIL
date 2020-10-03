# 프로그래머스 : 직사각형의 넓이

## 문제 설명 [[링크]](https://www.welcomekakao.com/learn/courses/30/lessons/12974)

평면 위에 N개의 직사각형이 놓여있습니다. 직사각형의 각 변은 x축, y축에 평행합니다. 각각의 직사각형은 왼쪽 아래 좌표(x1, y1)과 오른쪽 위 좌표 (x2, y2)를 가지며, (x1, y1, x2, y2)로 나타내고, 서로 겹쳐있을 수 있습니다.

5개의 직사각형 (1, 1, 6, 5), (2, 0, 4, 2), (2, 4, 5, 7), (4, 3, 8, 6), (7, 5, 9, 7) 이 놓여있습니다. 이때 전체 직사각형이 덮고 있는 면적은 아래 그림의 검은 테두리 안쪽의 면적과 같습니다. 따라서 위 예시에서 5개의 직사각형이 덮고 있는 면적은 38이 됩니다.

평면 위에 놓여있는 직사각형들의 좌표가 매개변수 rectangles로 주어질 때, 직사각형들이 덮고 있는 면적의 넓이를 return하도록 solution 합수를 완성해 주세요.

```
[제한 사항]
직사각형의 개수 
N : 1 ≤ N ≤ 100,000

직사각형의 좌표 
x1, y1, x2, y2 : 0 ≤ x1 < x2 ≤ 109 , 0 ≤ y1 < y2 ≤ 109
x1, y1, x2, y2는 정수

[입출력 예]
rectangles	result
[[0, 1, 4, 4], 
 [3, 1, 5, 3]]	14
 
[[1, 1, 6, 5], 
 [2, 0, 4, 2], 
 [2, 4, 5, 7], 
 [4, 3, 8, 6], 
 [7, 5, 9, 7]]	38
 
// 자세한 내용은 링크 참조
```

​    

## 문제 풀이 

[[백준 3392 화성지도]](https://www.acmicpc.net/problem/3392) 와 같은 문제라고 보면 된다. 만약 검색해서 잘 안나오면 백준 문제로 검색해 보면 잘 나온다.

이 문제는 세그먼트 트리, 스위핑을 이용하면 된다. 자세한 내용은 [[이곳]](https://codedoc.tistory.com/421)을 참고하자.

개인적으로 세그먼트 트리에 대한 이해도가 없으면 위의 링크 설명을 봐도 무슨말인지 알기가 어렵다. 위의 설명이 어렵다면 먼저 세그먼트 트리를 공부하고 세그먼트 트리를 이용한 간단한 문제를 풀고 나서 보면 좀 더 이해가 잘된다.

​    

## 코드 구현 [[전체 코드]](./Solution.java)

코드가 좀 많다. 좀더 단순화 시키면 업데이트 하겠다.

1\. 배열들을 받아서 x축 값으로 정렬한다.

```java
Pos[] arr = getSortedPostArr(rectangles);
```

```java
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

```

​    

2\. Y값 중에 가장 큰 값을 구해서 세그먼트 트리 생성한다.

```java
STree st = new STree(Solution.getMaxY(arr));
```

```java

class STree {
    Node[] arr;
    int range;

    STree(int len) {
        range = len;
        arr = new Node[len * 4 + 1];	// 1부터 시작하게끔 +1해준다.
        for (int i = 0; i < arr.length; i++) arr[i] = new Node();
    }

    public void update(Pos one) {
        // 0~3일때 0~1, 1~2, 2~3 이지만 로직상 3~4까지 포함될수 있어서 -1해준다.
        update(one.smallY, one.bigY - 1, one.isStart, 0, range, 1);
    }

    public int getTotalY() {
        return arr[1].sum;
    }

    private void update(
        int left, int right, boolean isStart, int nodeLeft, int nodeRight, int nodeNum
    ) {
        // 전혀 겹치지 않는 영역이면 끝낸다.
        if (right < nodeLeft || nodeRight < left) return;
        
        // 구하려는 left ~ right 안에 포함되는 영역일 경우 
        if (left <= nodeLeft && nodeRight <= right) {
            Node oneNode = arr[nodeNum];
            
            // 직사각형이 시작할때
            if (isStart) {
                // 처음으로 직사각형 영역이 시작되면 sum 추가, 여러번 겹치면 sum 추가 X
                if (oneNode.count == 0) oneNode.sum = nodeRight - nodeLeft + 1;
                oneNode.count++;
            } else {
                // 직사각형이 끝날 때, count 값에 따라서 반영해준다.
                oneNode.count--;

                if (oneNode.count == 0) {
                    oneNode.sum = (nodeLeft == nodeRight) // 리프 체크
                        ? 0 : arr[nodeNum * 2].sum + arr[nodeNum * 2 + 1].sum;
                }
            }
            
            // 세그먼트 트리가 업데이트 될때마다 해당 노드의 부모 노드들을
            // 위로 올라가면서 반영해줘야 한다.
            upward(nodeNum / 2);

        } else {
            // 일부만 포함될 경우
            int mid = (nodeLeft + nodeRight) / 2;
            
            // 좌우로 나뉘어서 재귀호출
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
```

​    

3\. 입력된 값들을 순회하면서 세그먼트 트리를 업데이트 하고 업데이트 한 시점에 현재의 x값과 이전x 값의 차이를 이용해서 너비를 구한다.

```java
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
```

